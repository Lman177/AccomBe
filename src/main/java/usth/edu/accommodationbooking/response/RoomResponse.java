package usth.edu.accommodationbooking.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomType;
    private Integer roomPrice;
    private boolean isBooked;
    private String photo;
    private String description;
    private List<BookingResponse>bookings;

    public RoomResponse(Long id, String roomType, Integer roomPrice, String description) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.description = description;
    }

    public RoomResponse(Long id, String roomType, Integer roomPrice, boolean isBooked,
                           byte[] photoBytes, String description, List<BookingResponse> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.description = description;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.bookings = bookings;
    }
}
