package usth.edu.accommodationbooking.service.Room;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import usth.edu.accommodationbooking.exception.InternalSeverException;
import usth.edu.accommodationbooking.exception.ResourceNotFoundException;
import usth.edu.accommodationbooking.model.Location;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.model.RoomType;
import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.repository.LocationRepository;
import usth.edu.accommodationbooking.repository.RoomRepository;
import usth.edu.accommodationbooking.repository.RoomTypeRepository;
import usth.edu.accommodationbooking.repository.UserRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final LocationRepository LocationRepository;

    @Override
//    public Room addNewRoom(Long userId, MultipartFile file, String roomType, Integer roomPrice, String description) throws SQLException, IOException {
    public Room addNewRoom(Long userId, MultipartFile file, String roomTypeName, Integer roomPrice, String description, String roomLocation, String roomAddress) throws SQLException, IOException {
        User owner = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Room room = new Room();
        RoomType roomType = roomTypeRepository.findByName(roomTypeName);
        if(roomType == null){
            throw new ResourceNotFoundException("Room Type not found");
        }
        // Set RoomType for the roo
        Location roomLocate = LocationRepository.findByLocationName(roomLocation);
        if(roomLocate == null){
            throw new ResourceNotFoundException("Room Location not found");
        }
        room.setRoomAddress(roomAddress);
        room.setRoomTypeName(roomType);
        room.setRoomPrice(roomPrice);
        room.setOwner(owner);
        if(!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        room.setDescription(description);
        room.setRoomLocation(roomLocate);
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();

    }

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepository.findAvailableRoom();
    }


    @Transactional
    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if(theRoom.isPresent()){
            Room room = theRoom.get();
            // Clear user-room relationships
            User owner = room.getOwner();
            if (owner != null) {
                owner.getRooms().remove(room);
                userRepository.save(owner);
            }

            // Delete the room
            roomRepository.deleteById(roomId);
        }
    }


    @Override
    public Room updateRoom(Long roomId, String roomTypeName, Integer roomPrice,String description, String roomLocation, String roomAddress, byte[] photoBytes ) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry, Room not found"));
        RoomType roomType = roomTypeRepository.findByName(roomTypeName);
        Location roomLocate = LocationRepository.findByLocationName(roomLocation);
        if(roomTypeName != null ) room.setRoomTypeName(roomType);
        if(roomLocation != null ) room.setRoomLocation(roomLocate);
        if(description != null) room.setDescription(description);
        if(roomAddress != null) room.setRoomAddress(roomAddress);
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
    public List<Room> getAllAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType, String roomLocation) {
        return roomRepository.findAvaRoomByDate_Type_Location(checkInDate, checkOutDate, roomType, roomLocation);
    }

    @Override
    public List<Room> getRoomsByUserId(Long userId) {
        return roomRepository.findByOwnerId(userId);
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



//    @Override
//    public List<String> getAllRoomTypes() {
//        return roomRepository.findDistinctRoomTypes();
//    }



//    @Override
//    public List<String> getAllRoomTypes() {
//        RoomRepository.fin;
//    }



}
