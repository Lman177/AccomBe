package usth.edu.accommodationbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(length = 1024)
    private String comment;

    @Column(length = 1024)
    private String response;

    @Column(nullable = false)
    private LocalDate createdDate;


}
