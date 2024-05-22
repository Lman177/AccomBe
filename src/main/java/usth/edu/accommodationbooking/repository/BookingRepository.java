package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usth.edu.accommodationbooking.model.BookedRoom;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

    List<BookedRoom> findByRoomId(Long roomId);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByGuestEmail(String email);


}
