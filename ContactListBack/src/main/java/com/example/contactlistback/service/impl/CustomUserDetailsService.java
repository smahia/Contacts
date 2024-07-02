package com.example.contactlistback.service.impl;

import com.example.contactlistback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Component used for loading user-specific data. It is responsible for retrieving user information
 * https://www.geeksforgeeks.org/spring-security-userdetailsservice-and-userdetails-with-example/
 */
@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    /**
     * @param username the username identifying the user whose data is required.
     * @return UserDetails The UserDetails object represents the authenticated user in the Spring Security
     * framework and contains details such as the user’s username, password, authorities (roles),
     * and additional attributes.
     * @throws UsernameNotFoundException If the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username + "not found")
        );
    }
}
