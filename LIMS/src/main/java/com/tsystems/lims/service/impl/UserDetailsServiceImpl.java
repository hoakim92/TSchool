package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.interfaces.IUserDao;
import com.tsystems.lims.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@ComponentScan(basePackages = "com.tsystems.lims.dao")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserDao userDao;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getByName(email);
        if (user == null)
            throw new UsernameNotFoundException("User not found exception");
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

}
