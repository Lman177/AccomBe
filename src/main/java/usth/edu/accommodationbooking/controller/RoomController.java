package usth.edu.accommodationbooking.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.exception.PhotoRetrivalException;
import usth.edu.accommodationbooking.exception.ResourceNotFoundException;
import usth.edu.accommodationbooking.model.BookedRoom;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.response.BookingResponse;
import usth.edu.accommodationbooking.response.RoomResponse;
//import usth.edu.accommodationbooking.security.user.AccomUserDetails;
import usth.edu.accommodationbooking.security.user.AccomUserDetails;
import usth.edu.accommodationbooking.service.Booking.BookingService;
import usth.edu.accommodationbooking.service.Room.IRoomService;
import usth.edu.accommodationbooking.service.Room.RoomServiceImpl;
//import usth.edu.accommodationbooking.service.User.UserService;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final IRoomService roomService;
    private final BookingService bookingService;
    private final RoomServiceImpl RoomService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("roomTypeName") String roomTypeName,
            @RequestParam("roomAddress") String roomAddress,
            @RequestParam("roomLocation") String roomLocation,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("description") String description,
            @RequestParam("roomPrice") Integer roomPrice,
            @RequestParam("roomCapacity") Integer roomCapacity) throws SQLException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccomUserDetails userDetails = (AccomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId(); // Cast to your User class and get the ID
//        Room savedRoom = RoomService.addNewRoom(userId ,photo, roomType, roomPrice, description);
        Room savedRoom = RoomService.addNewRoom(userId, photo, roomTypeName, roomPrice, description, roomLocation, roomAddress, roomCapacity);
        RoomResponse response = new RoomResponse(
                savedRoom.getId(),
                savedRoom.getRoomTypeName(),
                savedRoom.getRoomPrice(),
                savedRoom.getDescription(),
                savedRoom.getRoomLocation().getLocationName(),
                userId,
                savedRoom.getRoomAddress(),
                savedRoom.getRoomCapacity()
                );
        return ResponseEntity.ok(response);
    }
//
//    @GetMapping("/all-rooms")
//    public ResponseEntity<List<RoomResponse>> getAllRoom() throws SQLException {
//        List<Room> rooms = roomService.getAllRooms();
//        List<RoomResponse> roomResponses = new ArrayList<>();
//        for (Room room : rooms) {
//            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
//            if (photoBytes != null && photoBytes.length > 0) {
//                String base64Photo = Base64.encodeBase64String(photoBytes);
//                RoomResponse roomResponse = getRoomResponse(room);
//                roomResponse.setPhoto(base64Photo);
//                roomResponses.add(roomResponse);
//            }
//        }
//        return ResponseEntity.ok(roomResponses);
//    }

    @GetMapping("/all-rooms")
    public ResponseEntity<Page<RoomResponse>> getAllRooms(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) throws SQLException {

        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.getAllRooms(pageable);
        List<RoomResponse> roomResponses = new ArrayList<>();

        for (Room room : roomPage.getContent()) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }

        Page<RoomResponse> responsePage = new PageImpl<>(roomResponses, pageable, roomPage.getTotalElements());
        return ResponseEntity.ok(responsePage);
    }
    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms() throws SQLException {
        List<Room> rooms = RoomService.getAvailableRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    @DeleteMapping("/delete/room/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomId") Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                   @RequestParam(required = false)  String roomTypeName,
                                                   @RequestParam(required = false) Integer roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo,
                                                   @RequestParam(required = false) String description,
                                                   @RequestParam(required = false) String roomLocation,
                                                   @RequestParam(required = false) String roomAddress,
                                                   @RequestParam(required = false) Integer roomCapacity
                                                   ) throws SQLException, IOException {
        byte[] photoBytes = photo != null && !photo.isEmpty() ?
                photo.getBytes() : roomService.getRoomPhotoByRoomId(roomId);
        Blob photoBlob = photoBytes != null && photoBytes.length >0 ? new SerialBlob(photoBytes): null;
        Room theRoom = roomService.updateRoom(roomId, roomTypeName, roomPrice, description, roomLocation, roomAddress, photoBytes, roomCapacity);
        theRoom.setPhoto(photoBlob);
        RoomResponse roomResponse = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);
    }
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
        Optional<Room> theRoom = roomService.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomResponse roomResponse = getRoomResponse(room);
            return  ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<Page<RoomResponse>> getAllAvailableRooms(
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String roomLocation,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) throws SQLException {

        Pageable pageable = PageRequest.of(page, size);
        Page<Room> availableRooms = roomService.filterRooms(checkInDate, checkOutDate, roomType, roomLocation, minPrice, maxPrice, pageable);

        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : availableRooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }

        Page<RoomResponse> roomResponsePage = new PageImpl<>(roomResponses, pageable, availableRooms.getTotalElements());

        if(roomResponsePage.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(roomResponsePage);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByUserId(@PathVariable Long userId) throws SQLException {
        List<Room> rooms = roomService.getRoomsByUserId(userId);
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    @GetMapping("/total")
    public Integer CountAllRooms(){
        return roomService.countAllRoom();
    }


    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
        // Check if bookings is null and initialize to empty list if necessary
        if (bookings == null) {
            bookings = new ArrayList<>(); // Ensures the list is not null for the streaming operation
        }
        List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(
                        booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode())).toList();
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrivalException("Error retrieving photo");
            }
        }
        return new RoomResponse(room.getId(),
                room.getRoomTypeName(), room.getRoomPrice(),
                room.isBooked(),room.getDescription(), room.getRoomLocation(), room.getRoomAddress(), photoBytes, room.getOwner().getId(), room.getRoomCapacity());
    }
    private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }


}
