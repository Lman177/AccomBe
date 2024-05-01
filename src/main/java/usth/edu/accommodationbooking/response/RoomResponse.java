package usth.edu.accommodationbooking.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import usth.edu.accommodationbooking.model.RoomType;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private RoomType roomTypeName;
    private Integer roomPrice;
    private boolean isBooked;
    private String photo;
    private String description;
    private List<BookingResponse>bookings;

    public RoomResponse(Long id, RoomType roomTypeName, Integer roomPrice, String description) {
        this.id = id;
        this.roomTypeName = roomTypeName;
        this.roomPrice = roomPrice;
        this.description = description;
    }

    public RoomResponse(Long id, RoomType roomTypeName, Integer roomPrice, boolean isBooked,
                           byte[] photoBytes, String description, List<BookingResponse> bookings) {
        this.id = id;
        this.roomTypeName = roomTypeName;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.description = description;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.bookings = bookings;
    }
}
