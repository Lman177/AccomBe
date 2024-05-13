package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usth.edu.accommodationbooking.model.Room;

import java.time.LocalDate;
import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT * FROM accom.room r " +
            "WHERE r.room_type LIKE :roomType " +
            "AND r.room_location LIKE :roomLocation " +
            "AND r.id NOT IN (" +
            "SELECT br.room_id FROM accom.booked_room br " +
            "WHERE (br.check_in_date <= :checkOutDate AND br.check_out_date >= :checkInDate))",
            nativeQuery = true)
    List<Room> findAvaRoomByDate_Type_Location(LocalDate checkInDate, LocalDate checkOutDate, String roomType, String roomLocation);
}
