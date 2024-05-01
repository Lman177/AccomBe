package usth.edu.accommodationbooking.service.RoomType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usth.edu.accommodationbooking.exception.RoomTypeAlreadyExistException;
import usth.edu.accommodationbooking.model.RoomType;
import usth.edu.accommodationbooking.repository.RoomTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeService implements IRoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    @Override
    public RoomType createRoomType(RoomType theType) {
        String roomTypeName = theType.getName();
        RoomType roomType = new RoomType(roomTypeName);
        if (roomTypeRepository.existsByName(roomTypeName)){
            throw new RoomTypeAlreadyExistException(roomTypeName + " already exists");
        }

        return roomTypeRepository.save(roomType);
    }

    @Override
    public List<RoomType> getRoomTypes() {
        return roomTypeRepository.findAll();
    }

}
