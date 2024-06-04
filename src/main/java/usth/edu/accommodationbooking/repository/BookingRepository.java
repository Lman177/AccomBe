package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usth.edu.accommodationbooking.model.BookedRoom;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

    List<BookedRoom> findByRoomId(Long roomId);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByGuestEmail(String email);

    @Query("SELECT b FROM BookedRoom b WHERE b.checkOutDate < current_date ")
    List<BookedRoom> findBookingOverTime();


}
