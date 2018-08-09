package com.psypo.springoauthjwtex.service;

import com.psypo.springoauthjwtex.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rimantas Jacikevičius on 18.8.5.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }
        return user;
    }

}
