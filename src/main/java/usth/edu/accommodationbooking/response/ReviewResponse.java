package usth.edu.accommodationbooking.response;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long roomId;
    private Long guestId;
    private BigDecimal rating;
    private String comment;
    private LocalDate createdDate;

}

