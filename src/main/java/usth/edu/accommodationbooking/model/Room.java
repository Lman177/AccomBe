package usth.edu.accommodationbooking.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "room_address")
    private String roomAddress;

    @Column(name = "room_price")
    private Integer roomPrice;

    @Column(name = "room_status")
    private boolean isBooked = false;

    @Lob
    @Column(name = "room_photo")
    @JsonIgnore
    private Blob photo;

    @Column(name = "room_description")
    private String description;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room")
    private List<BookedRoom> bookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type")
    private RoomType roomTypeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_location")
    private Location roomLocation;

    public Room() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoom booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setPrice(roomPrice*(booking.getCheckOutDate().getDayOfMonth()-booking.getCheckInDate().getDayOfMonth()));
        booking.setBookingConfirmationCode(bookingCode);
    }
}
