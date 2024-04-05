package usth.edu.accommodationbooking.service;

import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.model.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IRoomService {

    public Room addNewRoom(MultipartFile photo, String roomType, Integer roomPrice) throws SQLException, IOException;

    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();


    void deleteRoom(Long roomId);
}
