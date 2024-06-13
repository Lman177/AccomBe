package usth.edu.accommodationbooking.service.Booking;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usth.edu.accommodationbooking.exception.InvalidBookingRequestException;
import usth.edu.accommodationbooking.model.BookedRoom;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.repository.BookingRepository;
import usth.edu.accommodationbooking.repository.RoomRepository;
import usth.edu.accommodationbooking.repository.UserRepository;
import usth.edu.accommodationbooking.response.BookingResponse;
import usth.edu.accommodationbooking.security.jwt.JwtUtils;
import usth.edu.accommodationbooking.service.Room.IRoomService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final IRoomService roomService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }


    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        // Validate dates first
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        // Retrieve the room information
        Room room = roomService.getRoomById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + roomId + " not found"));
        // Check if room is available
        if (!roomIsAvailable(bookingRequest, room.getBookings())) {
            throw new InvalidBookingRequestException("Sorry, this room is not available for selected dates");
        }
        // Check room capacity
        if (room.getRoomCapacity() < bookingRequest.getTotalNumOfGuests()) {
            throw new InvalidBookingRequestException("This room capacity is not enough for the number of guests you have entered");
        }
        // Add booking to room and save
        room.addBooking(bookingRequest);
        bookingRepository.save(bookingRequest);

        return bookingRequest.getBookingConfirmationCode();
    }




    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public List<BookingResponse> getBookingsByUserEmail(String email) {
        List<BookedRoom> bookedRooms = bookingRepository.findByGuestEmail(email);
        return bookedRooms.stream().map(bookedRoom -> new BookingResponse(
                bookedRoom.getBookingId(),
                bookedRoom.getCheckInDate(),
                bookedRoom.getCheckOutDate(),
                bookedRoom.getGuestFullName(),
                bookedRoom.getGuestEmail(),
                bookedRoom.getGuestPhone(),
                bookedRoom.getNumberOfAdults(),
                bookedRoom.getNumberOfChildren(),
                bookedRoom.getTotalNumOfGuests(),
                bookedRoom.getBookingConfirmationCode(),
                bookedRoom.getRoom().getOwner().getId(),
                bookedRoom.getRoom()
        )).collect(Collectors.toList());
    }


    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        LocalDate checkIn = bookingRequest.getCheckInDate();
        LocalDate checkOut = bookingRequest.getCheckOutDate();

        return existingBookings.stream().noneMatch(existingBooking -> {
            LocalDate existingCheckIn = existingBooking.getCheckInDate();
            LocalDate existingCheckOut = existingBooking.getCheckOutDate();
            return checkIn.isBefore(existingCheckOut) && checkOut.isAfter(existingCheckIn);
        });
    }

    @Transactional
    @Override
    public void UpdateAvailableRoom() {
        if(bookingRepository.findBookingOverTime() != null){
            bookingRepository.findBookingOverTime().forEach(booking -> {
                Room room = booking.getRoom();
                room.setBooked(false);
                roomRepository.save(room);
            });
        }
    }
    @Override
    public  List<BookingResponse> findBookingOfOwner(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer " prefix
        Long userid =jwtUtils.getUserIdFromToken(token);
        List<BookedRoom> bookedRooms = bookingRepository.findBookingOfOwner(userid);
        return bookedRooms.stream().map(bookedRoom -> new BookingResponse(
                bookedRoom.getBookingId(),
                bookedRoom.getCheckInDate(),
                bookedRoom.getCheckOutDate(),
                bookedRoom.getGuestFullName(),
                bookedRoom.getGuestEmail(),
                bookedRoom.getGuestPhone(),
                bookedRoom.getNumberOfAdults(),
                bookedRoom.getNumberOfChildren(),
                bookedRoom.getTotalNumOfGuests(),
                bookedRoom.getBookingConfirmationCode(),
                bookedRoom.getRoom().getOwner().getId(),
                bookedRoom.getRoom()
        )).collect(Collectors.toList());
    }


}
