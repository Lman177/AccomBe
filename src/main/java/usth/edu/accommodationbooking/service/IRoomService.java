package usth.edu.accommodationbooking.service;

import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.model.Room;

import java.io.IOException;
import java.sql.SQLException;

public interface IRoomService {

    Room addNewRoom(MultipartFile photo, String roomType, Integer roomPrice) throws SQLException, IOException;
}
