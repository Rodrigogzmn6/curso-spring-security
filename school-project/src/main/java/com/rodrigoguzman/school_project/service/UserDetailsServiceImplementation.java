package com.rodrigoguzman.school_project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.dto.AuthLoginRequestDTO;
import com.rodrigoguzman.school_project.dto.AuthResponseDTO;
import com.rodrigoguzman.school_project.model.SecuredUser;
import com.rodrigoguzman.school_project.repository.ISecuredUserRepository;
import com.rodrigoguzman.school_project.utils.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService {
    final ISecuredUserRepository repository;
    final JwtUtils jwtUtils;
    final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We have a SchoolUser and we need to return a UserDetails object
        // The UserDetails object is used by the Spring Security framework to
        // authenticate and authorize users
        // Get user from database
        SecuredUser schoolUser = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Create permissions list
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Get roles and change them to SimpleGrantedAuthority
        schoolUser.getRolesList()
                .stream()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        // Get permissions and change them to SimpleGrantedAuthority
        schoolUser.getRolesList()
                .stream()
                .flatMap(r -> r.getPermissionsList()
                        .stream())
                .forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getPermission())));

        // Return user on Spring Security User format
        return new User(
                schoolUser.getUsername(),
                schoolUser.getPassword(),
                schoolUser.isEnabled(),
                schoolUser.isAccountNonExpired(),
                schoolUser.isCredentialsNonExpired(),
                schoolUser.isAccountNonLocked(),
                authorities);
    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO userRequest) {
        String username = userRequest.username();
        String password = userRequest.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponseDTO response = new AuthResponseDTO(username, "Login successful", accessToken, true);

        return response;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                userDetails.getAuthorities());
    }
}
