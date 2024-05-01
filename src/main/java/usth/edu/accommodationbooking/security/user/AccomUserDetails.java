//package usth.edu.accommodationbooking.security.user;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import usth.edu.accommodationbooking.model.User;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class AccomUserDetails implements UserDetails {
//    private Long id;
//    private String email;
//    private String password;
//    private Collection<GrantedAuthority> authorities;
//
//    public static AccomUserDetails buildUserDetails(User user){
//        List<GrantedAuthority> authorities = user.getRoles()
//                .stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//        return new AccomUserDetails(
//                user.getId(),
//                user.getEmail(),
//                user.getPassword(),
//                authorities);
//
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public Long getUserId() {
//        return id;
//    }
//}
