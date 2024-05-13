package usth.edu.accommodationbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usth.edu.accommodationbooking.exception.RoomTypeAlreadyExistException;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.model.RoomType;
import usth.edu.accommodationbooking.service.RoomType.RoomTypeService;

import java.util.List;

@RestController
@RequestMapping("/room-types")
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @PostMapping("/create")
    public ResponseEntity<String> createRole(@RequestBody RoomType roomType){
        try{
            roomTypeService.createRoomType(roomType);
            return ResponseEntity.ok("New Room Type created successfully");
        }catch(RoomTypeAlreadyExistException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());
        }
    }
    @GetMapping("/all-room-types")
    public ResponseEntity<List<RoomType>> getAllRoomTypes(){
        return new ResponseEntity<>(roomTypeService.getRoomTypes(), HttpStatus.OK);
    }



}
