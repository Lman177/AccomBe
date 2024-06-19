package usth.edu.accommodationbooking.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usth.edu.accommodationbooking.exception.InvalidBookingRequestException;
import usth.edu.accommodationbooking.exception.ResourceNotFoundException;
import usth.edu.accommodationbooking.model.BookedRoom;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.response.BookingResponse;
import usth.edu.accommodationbooking.response.ProfitResponse;
import usth.edu.accommodationbooking.response.RoomResponse;
import usth.edu.accommodationbooking.service.Booking.IBookingService;
import usth.edu.accommodationbooking.service.Room.IRoomService;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final IBookingService bookingService;
    private final IRoomService roomService;
    @GetMapping("all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookingResponse> bookingResponses = bookingService.getAllBookings();
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }



    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest){
        try{
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok(
                    "Room booked successfully! Your booking confirmation code is :" + confirmationCode);
        }catch(InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    @GetMapping("/user/{email}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserEmail(@PathVariable String email) {
        List<BookingResponse> bookingResponses = bookingService.getBookingsByUserEmail(email);
        return new ResponseEntity<>(bookingResponses,HttpStatus.OK);
    }
    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }


    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomTypeName(),
                theRoom.getRoomLocation().getLocationName(),
                theRoom.getRoomPrice(),
                theRoom.getDescription());
        return new BookingResponse(
                booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(),booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumberOfAdults(),
                booking.getNumberOfChildren(), booking.getTotalNumOfGuests(),
                booking.getBookingConfirmationCode(), booking.getRoom(), room);
    }

    @GetMapping("/get")
    public ResponseEntity<List<BookingResponse>> findBookingOfOwner(HttpServletRequest request){
        List<BookingResponse> bookings = bookingService.findBookingOfOwner(request);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/profit")
    public ResponseEntity<List<ProfitResponse>> getProfit(){
        List<ProfitResponse> profitList = bookingService.getProfit();
        return new ResponseEntity<>(profitList, HttpStatus.OK);
    }



}
