package usth.edu.accommodationbooking.service.User;

import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.request.UserDto;
import usth.edu.accommodationbooking.response.ProfitResponse;

import java.util.List;

public interface IUserService{
    void registerUser(User user);






    void modifyUser(String userEmail, String firstName, String phoneNumber, String password);

    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);

    UserDto getOwnerOfRoomByRoomId(Long id);


    Integer countAllUser();

    List<ProfitResponse> getProfitByOwner(Long ownerId);
}
