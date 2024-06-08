package usth.edu.accommodationbooking.service.User;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import usth.edu.accommodationbooking.exception.UserAlreadyExistsException;
import usth.edu.accommodationbooking.model.Role;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.repository.RoleRepository;
import usth.edu.accommodationbooking.repository.RoomRepository;
import usth.edu.accommodationbooking.repository.UserRepository;
import usth.edu.accommodationbooking.request.UserDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoomRepository roomRepository;

    @Override
    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())){
            throw new UserAlreadyExistsException(user.getPhoneNumber() + " already exists");
        }
        user.setPhoneNumber(user.getPhoneNumber());
        user.setCreatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        System.out.println(user.getPassword());
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if (theUser != null) {
            // Step 1: Clear user-role relationships
            theUser.getRoles().clear();
            userRepository.save(theUser);
            // Step 2: Handle user-room relationships
            List<Room> rooms = roomRepository.findByOwnerId(theUser.getId());
            for (Room room : rooms) {
                // Alternatively, delete the rooms if that makes more sense for your application
                roomRepository.delete(room);
            }
            // Step 3: Delete the user
            userRepository.delete(theUser);
        }
    }
    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDto getOwnerOfRoomByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new UsernameNotFoundException("Room not found with id: " + roomId));
        User owner = userRepository.findById(room.getOwner().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + room.getOwner().getId()));
        UserDto ownerDto = new UserDto();
        ownerDto.setId(owner.getId());
        ownerDto.setFirstName(owner.getFirstName());
        ownerDto.setPhoneNumber(owner.getPhoneNumber());
        ownerDto.setCreatedAt(owner.getCreatedAt());
        ownerDto.setEmail(owner.getEmail());
        return ownerDto;
    }


}