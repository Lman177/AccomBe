package usth.edu.accommodationbooking.service.Room;

import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.model.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {


    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

    List<String> getAllRoomTypes();

    Room addNewRoom(Long userId, MultipartFile file, String roomType, Integer roomPrice, String description) throws SQLException, IOException;

    List<Room> getAllRooms();


    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId, String roomType, Integer roomPrice, byte[] photoBytes);

    Optional<Room> getRoomById(Long roomId);
}
