package usth.edu.accommodationbooking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import usth.edu.accommodationbooking.exception.RoleAlreadyExistException;
import usth.edu.accommodationbooking.model.Role;
import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.repository.RoleRepository;
import usth.edu.accommodationbooking.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepository.existsByName(roleName)){
            throw new RoleAlreadyExistException(roleName + " already exists");
        }

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        return null;
    }

//    @Override
//    public User removeUserFromRole(String userId, Long roleId) {
//
//    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        return null;
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        return null;
    }
}
