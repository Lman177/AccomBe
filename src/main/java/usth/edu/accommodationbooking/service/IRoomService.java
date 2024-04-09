package usth.edu.accommodationbooking.service;

import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.model.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {

    Room addNewRoom(MultipartFile photo, String roomType, Integer roomPrice) throws SQLException, IOException;

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();


    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId, String roomType, Integer roomPrice, byte[] photoBytes);

    Optional<Room> getRoomById(Long roomId);
}
