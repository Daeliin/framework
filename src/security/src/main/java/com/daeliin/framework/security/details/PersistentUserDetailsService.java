package com.daeliin.framework.security.details;

import com.daeliin.framework.commons.security.cryptography.Sha512;
import com.daeliin.framework.commons.security.cryptography.Token;
import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.commons.security.details.UserDetailsRepository;
import com.daeliin.framework.commons.security.details.UserPermissionDetails;
import com.daeliin.framework.commons.security.details.UserPermissionDetailsRepository;
import com.daeliin.framework.commons.security.exception.InvalidTokenException;
import com.daeliin.framework.security.membership.UserDetailsEncryption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersistentUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @Autowired
    private UserPermissionDetailsRepository permissionRepository;
    
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        UserDetails user = userDetailsRepository.findByUsernameIgnoreCaseAndEnabled(username, true);
        
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<UserPermissionDetails> permissions = permissionRepository.findByUser(user);
        permissions.forEach(userPermission -> authorities.add(new SimpleGrantedAuthority(userPermission.getPermission().getLabel())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    
    public boolean exists(final UserDetails userDetails) {
        UserDetails existingUserDetails = userDetailsRepository.findByUsernameIgnoreCase(userDetails.getUsername());
        return existingUserDetails != null;
    }
    
    public void signUp(UserDetails userDetails) {
        UserDetailsEncryption userDetailsEncryption = new UserDetailsEncryption(userDetails);
        
        userDetails.setSignedUpDate(new Date());
        userDetails.setPassword(userDetailsEncryption.password());
        userDetails.setToken(userDetailsEncryption.token());
        userDetails.setEnabled(false);
    }
    
    public boolean hasValidToken(UserDetails userDetails, final String activationToken) {
        return userDetails.getToken().equals(activationToken);
    }
    
    public void assignNewToken(UserDetails userDetails) {
        Token newToken = new Token(Arrays.asList(userDetails.getEmail()), new Sha512(), true);
        userDetails.setToken(newToken.asString());
    }
    
    public void activate(UserDetails userDetails, final String activationToken) throws InvalidTokenException {
        if (!hasValidToken(userDetails, activationToken)) {
            throw new InvalidTokenException("Activation token is not valid for user[" + userDetails.getId() + "]");
        }
        
        assignNewToken(userDetails);
        userDetails.setEnabled(true);
    }
    
    public void resetPassword(UserDetails userDetails, final String resetPasswordToken, final String newPassword) throws InvalidTokenException {
        if (!hasValidToken(userDetails, resetPasswordToken)) {
            throw new InvalidTokenException("Activation token is not valid for user[" + userDetails.getId() + "]");
        }
        
        userDetails.setClearPassword(newPassword);
        UserDetailsEncryption userDetailsEncryption = new UserDetailsEncryption(userDetails);
        userDetails.setPassword(userDetailsEncryption.password());
        userDetails.setToken(userDetailsEncryption.token());
        userDetails.setEnabled(true);
    }
}
