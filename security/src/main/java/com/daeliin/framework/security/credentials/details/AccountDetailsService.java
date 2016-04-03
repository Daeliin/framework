package com.daeliin.framework.security.credentials.details;

import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.commons.security.credentials.account.PersistentAccountRepository;
import com.daeliin.framework.commons.security.credentials.accountpermission.AccountPermission;
import com.daeliin.framework.commons.security.credentials.accountpermission.PersistentAccountPermissionRepository;
import com.daeliin.framework.commons.security.cryptography.Sha512;
import com.daeliin.framework.commons.security.cryptography.Token;
import com.daeliin.framework.commons.security.exception.InvalidTokenException;
import com.daeliin.framework.security.membership.AccountEncryption;
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
public class AccountDetailsService implements UserDetailsService {
    
    @Autowired
    private PersistentAccountRepository accountRepository;
    
    @Autowired
    private PersistentAccountPermissionRepository permissionRepository;
    
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByUsernameIgnoreCaseAndEnabled(username, true);
        
        if (account == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<AccountPermission> permissions = permissionRepository.findByAccount(account);
        permissions.forEach(accountPermission -> authorities.add(new SimpleGrantedAuthority(accountPermission.getPermission().getLabel())));

        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);
    }
    
    public boolean exists(final Account account) {
        Account existingAccount = accountRepository.findByUsernameIgnoreCase(account.getUsername());
        return existingAccount != null;
    }
    
    public void signUp(Account account) {
        AccountEncryption accountEncryption = new AccountEncryption(account);
        
        account.setSignUpDate(new Date());
        account.setPassword(accountEncryption.password());
        account.setToken(accountEncryption.token());
        account.setEnabled(false);
    }
    
    public boolean hasValidToken(Account account, final String activationToken) {
        return account.getToken().equals(activationToken);
    }
    
    public void assignNewToken(Account account) {
        Token newToken = new Token(Arrays.asList(account.getEmail()), new Sha512(), true);
        account.setToken(newToken.asString());
    }
    
    public void activate(Account account, final String activationToken) throws InvalidTokenException {
        if (!hasValidToken(account, activationToken)) {
            throw new InvalidTokenException("Activation token is not valid for account[" + account.getId() + "]");
        }
        
        assignNewToken(account);
        account.setEnabled(true);
    }
    
    public void resetPassword(Account account, final String resetPasswordToken, final String newPassword) throws InvalidTokenException {
        if (!hasValidToken(account, resetPasswordToken)) {
            throw new InvalidTokenException("Activation token is not valid for account[" + account.getId() + "]");
        }
        
        account.setClearPassword(newPassword);
        AccountEncryption accountEncryption = new AccountEncryption(account);
        account.setPassword(accountEncryption.password());
        account.setToken(accountEncryption.token());
        account.setEnabled(true);
    }
}
