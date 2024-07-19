package com.example.user.userdomain.dto;

import com.example.user.userdomain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 회원 조회 메서드를 갖는 UserDetails interface 구현
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserDTO userDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((GrantedAuthority) () -> userDTO.getRole().toString());

        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }
}
