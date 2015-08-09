package com.daeliin.framework.security.details;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    
    @Autowired
    private UserRepository UserRepository;
    
    @Autowired
    private UserPermissionRepository permissionRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepository.findByUsernameIgnoreCaseAndEnabled(username, true);
        
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<UserPermission> permissions = permissionRepository.findByUser(user);
        permissions.forEach(userPermission -> authorities.add(new SimpleGrantedAuthority(userPermission.getPermission().getLabel())));
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
