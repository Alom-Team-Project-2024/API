package com.example.user.userdomain.service;

import com.example.user.userdomain.dto.CustomUserDetails;
import com.example.user.userdomain.dto.UserDTO;
import com.example.user.userdomain.entity.User;
import com.example.user.userdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 클라이언트에서 HTTP Header에 들어있는 JWT를 분석하여 사용자 정보를 조회하는 Service
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDTO userDTO = new UserDTO(user.getUsername(), user.getNickname(), user.getRole().toString());
        return new CustomUserDetails(userDTO);
    }
}
