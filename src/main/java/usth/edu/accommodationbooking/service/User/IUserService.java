package usth.edu.accommodationbooking.service.User;

import usth.edu.accommodationbooking.model.User;

import java.util.List;

public interface IUserService{
    void registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
