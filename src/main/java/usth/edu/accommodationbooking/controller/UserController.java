package usth.edu.accommodationbooking.controller;


import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.request.UserDto;
import usth.edu.accommodationbooking.response.ProfitResponse;
import usth.edu.accommodationbooking.service.Booking.BookingService;
import usth.edu.accommodationbooking.service.Room.RoomServiceImpl;
import usth.edu.accommodationbooking.service.User.IUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final RoomServiceImpl roomService;
    private  final BookingService bookingService;
    @GetMapping("/all")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    @PermitAll
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try{
            User theUser = userService.getUser(email);
            return ResponseEntity.ok(theUser);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }
    @DeleteMapping("/delete/{userId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #email == principal.username)")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String email){
        try{
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable("userId") String email){
        try{
            User theUser = userService.getUser(email);
            return ResponseEntity.ok(theUser);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @GetMapping("/owner/{roomId}")
    public UserDto getRoomOwner(@PathVariable("roomId") Long roomId){
        return userService.getOwnerOfRoomByRoomId(roomId);
    }

    @GetMapping("/total")
    public Integer countAllUser(){
        return userService.countAllUser();
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUserProfile(@RequestParam String userEmail,
                                                    @RequestParam String firstName,
                                                    @RequestParam String phoneNumber,
                                                    @RequestParam String password) {
        try {
            userService.modifyUser(userEmail, firstName, phoneNumber, password);
            return ResponseEntity.ok("User updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }



    @GetMapping("/owner/profit/{ownerId}")
    public ResponseEntity<List<ProfitResponse>> getProfit(@PathVariable Long ownerId){
        List<ProfitResponse> profitList = userService.getProfitByOwner(ownerId);
        return new ResponseEntity<>(profitList, HttpStatus.OK);
    }
}
