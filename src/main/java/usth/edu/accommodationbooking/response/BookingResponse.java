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
@AllArgsConstructor
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


    public BookingResponse(Long bookingId, LocalDate checkInDate,
                           LocalDate checkOutDate, String guestFullName,
                           String guestEmail, int numberOfAdults, int numberOfChildren, int totalNumOfGuests,
                           String bookingConfirmationCode, RoomResponse room) {

        this.id = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestName = guestFullName;
        this.guestEmail = guestEmail;
        this.numOfAdults = numberOfAdults;
        this.numOfChildren = numberOfChildren;
        this.totalNumOfGuests = totalNumOfGuests;
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.ownerId = room.getOwnerId();
    }

    public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmationCode) {
        this.id = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
