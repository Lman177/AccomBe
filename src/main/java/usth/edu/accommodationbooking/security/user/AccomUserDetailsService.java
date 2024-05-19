package usth.edu.accommodationbooking.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.repository.UserRepository;
@Service
@RequiredArgsConstructor
public class AccomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return AccomUserDetails.buildUserDetails(user);
    }
}