package com.daeliin.framework.security.details;

import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.commons.security.details.UserDetailsRepository;
import com.daeliin.framework.commons.security.details.UserPermissionDetails;
import com.daeliin.framework.commons.security.details.UserPermissionDetailsRepository;
import com.daeliin.framework.security.membership.UserDetailsEncryption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersistentUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserDetailsRepository userRepository;
    
    @Autowired
    private UserPermissionDetailsRepository permissionRepository;
    
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        UserDetails user = userRepository.findByUsernameIgnoreCaseAndEnabled(username, true);
        
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<UserPermissionDetails> permissions = permissionRepository.findByUser(user);
        permissions.forEach(userPermission -> authorities.add(new SimpleGrantedAuthority(userPermission.getPermission().getLabel())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    
    public void generate(UserDetails userDetails) {
        UserDetailsEncryption userDetailsEncryption = new UserDetailsEncryption(userDetails);
        
        userDetails.setSignedUpDate(new Date());
        userDetails.setPassword(userDetailsEncryption.password());
        userDetails.setToken(userDetailsEncryption.token());
        userDetails.setEnabled(false);
    }
}
