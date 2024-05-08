package usth.edu.accommodationbooking.service.Room;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.exception.InternalSeverException;
import usth.edu.accommodationbooking.exception.ResourceNotFoundException;
import usth.edu.accommodationbooking.model.Room;
//import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.repository.RoomRepository;
import usth.edu.accommodationbooking.repository.UserRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public Room addNewRoom( MultipartFile file, String roomType, Integer roomPrice, String description) throws SQLException, IOException {
//        User owner = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
//        room.setOwner(owner);
        if(!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        room.setDescription(description);
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {

        return roomRepository.findAll();

    }

    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if(theRoom.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, Integer roomPrice, byte[] photoBytes) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry, Room not found"));
        if(roomType != null ) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
            try{
                    room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException e) {
                throw new InternalSeverException("Error updating Room");
            }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if(theRoom.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Room not found");
        }
        Blob photoBlob = theRoom.get().getPhoto();
        if(photoBlob != null){
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }



//    @Override
//    public List<String> getAllRoomTypes() {
//        RoomRepository.fin;
//    }



}
