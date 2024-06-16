package usth.edu.accommodationbooking.service.Room;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.model.Room;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRoomService {


    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

//    List<String> getAllRoomTypes();

    Room addNewRoom(Long userId, MultipartFile file, String roomTypeName,
                    Integer roomPrice, String description, String roomLocation,
                    String roomAddress, Integer roomCapacity) throws SQLException, IOException;
//    Room addNewRoom(Long userId, MultipartFile file, String roomType, Integer roomPrice, String description) throws SQLException, IOException;

    List<Room> getAllRooms();


    Page getAllRooms(Pageable pageable) throws SQLException;

    List<Room> getAvailableRooms();

    void deleteRoom(Long roomId);


    Room updateRoom(Long roomId, String roomTypeName, Integer roomPrice, String description, String roomLocation, String roomAddress,byte[] photoBytes, Integer roomCapacity ) throws SQLException, IOException;

    Optional<Room> getRoomById(Long roomId);


    List<Room> getAllAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType, String roomLocation);

    List<Room> filterRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType, String roomLocation, BigDecimal minPrice, BigDecimal maxPrice);

    List<Room> getRoomsByUserId(Long userId);

    Integer countAllRoom();
}
