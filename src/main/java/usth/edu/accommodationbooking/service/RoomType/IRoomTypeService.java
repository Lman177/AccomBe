package usth.edu.accommodationbooking.service.RoomType;

import usth.edu.accommodationbooking.model.RoomType;

import java.util.List;

public interface IRoomTypeService {

    RoomType createRoomType(RoomType theType);

    List<RoomType> getRoomTypes();
}
