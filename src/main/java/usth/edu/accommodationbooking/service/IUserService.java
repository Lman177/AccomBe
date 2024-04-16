package usth.edu.accommodationbooking.service;

import usth.edu.accommodationbooking.model.User;

import java.util.List;

public interface IUserService{
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
