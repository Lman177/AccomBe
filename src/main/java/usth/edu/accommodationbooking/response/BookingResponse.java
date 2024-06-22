package usth.edu.accommodationbooking.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import usth.edu.accommodationbooking.model.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data

@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;
    private String guestEmail;
    private String guestPhone;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuests;
    private String bookingConfirmationCode;
    private Long ownerId;
    private Room room;
    private boolean isReview;

    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, String guestName,
                           String guestEmail, String guestPhone, int numOfAdults, int numOfChildren,
                           int totalNumOfGuests, String bookingConfirmationCode, Long ownerId, Room room) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.numOfAdults = numOfAdults;
        this.numOfChildren = numOfChildren;
        this.totalNumOfGuests = totalNumOfGuests;
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.ownerId = ownerId;
        this.room = room;
    }
        


    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate,
                           String guestName, String guestEmail, String guestPhone,
                           int totalNumOfGuests, String bookingConfirmationCode, Long ownerId, Room room, boolean isReview) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.totalNumOfGuests = totalNumOfGuests;
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.ownerId = ownerId;
        this.room = room;
        this.isReview = isReview;
    }

    public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, String guestEmail, int numberOfAdults, int numberOfChildren, int totalNumOfGuests, String bookingConfirmationCode, Room room, RoomResponse room1) {
    this.id = bookingId;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.guestName = guestFullName;
    this.guestEmail = guestEmail;
    this.numOfAdults = numberOfAdults;
    this.numOfChildren = numberOfChildren;
    this.totalNumOfGuests = totalNumOfGuests;
    this.bookingConfirmationCode = bookingConfirmationCode;
    this.room = room;
    this.ownerId = room1.getOwnerId();

    }


//    public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, String guestEmail, String guestPhone, int totalNumOfGuests, String bookingConfirmationCode,Long ownerId, Room room ) {
//    this.id = bookingId;
//    this.checkInDate = checkInDate;
//    this.checkOutDate = checkOutDate;
//    this.guestName = guestFullName;
//    this.guestEmail = guestEmail;
//    this.guestPhone = guestPhone;
//    this.totalNumOfGuests = totalNumOfGuests;
//    this.bookingConfirmationCode = bookingConfirmationCode;
//    this.ownerId = id;
//    this.room = room;
//
//
//
//
//
//    }
}
