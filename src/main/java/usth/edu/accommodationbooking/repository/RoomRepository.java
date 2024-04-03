package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usth.edu.accommodationbooking.model.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT  distinct r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();


}
