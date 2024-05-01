//package usth.edu.accommodationbooking.service.Role;
//
//import org.springframework.stereotype.Service;
//import usth.edu.accommodationbooking.model.Role;
//import usth.edu.accommodationbooking.model.User;
//
//import java.util.List;
//
//public interface IRoleService {
//    List<Role> getRoles();
//    Role createRole(Role theRole);
//
//    void deleteRole(Long id);
//    Role findByName(String name);
//
//    User removeUserFromRole(Long userId, Long roleId);
//    User assignRoleToUser(Long userId, Long roleId);
//    Role removeAllUsersFromRole(Long roleId);
//}