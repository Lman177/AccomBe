package usth.edu.accommodationbooking;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import usth.edu.accommodationbooking.repository.RoomRepository;
import usth.edu.accommodationbooking.service.Booking.BookingService;

@SpringBootApplication
//@EnableScheduling
@RequiredArgsConstructor
public class AccommodationBookingApplication {

    private final BookingService bookingService;

    public static void main(String[] args) {
        SpringApplication.run(AccommodationBookingApplication.class, args);
    }

    @Scheduled(fixedRate = 60000)
    public void UpdateAvailableRoom() {
        bookingService.UpdateAvailableRoom();
    }
}
