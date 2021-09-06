package com.sg.capstoneblog.service;

import com.sg.capstoneblog.dao.UserDao;
import com.sg.capstoneblog.dto.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * Used to load 'Capstoneblog' 'User's into users that Spring Boot Security framework
 * can understand.
 *
 * @author timi
 */
@Service
public class CapstoneblogUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);

        GrantedAuthority authority
                = new SimpleGrantedAuthority(user.getRole().getName());

        return buildUserForAuthentication(user,
                new ArrayList<>(Arrays.asList(authority)));
    }

    private UserDetails buildUserForAuthentication(User user,
            List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }
}
