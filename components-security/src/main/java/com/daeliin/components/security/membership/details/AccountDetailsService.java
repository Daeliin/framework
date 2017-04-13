package com.daeliin.components.security.membership.details;

import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountRepository;
import com.daeliin.components.security.credentials.accountpermission.AccountPermission;
import com.daeliin.components.security.credentials.accountpermission.AccountPermissionRepository;
import com.daeliin.components.security.cryptography.Sha512;
import com.daeliin.components.security.cryptography.Token;
import com.daeliin.components.security.exception.InvalidTokenException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountDetailsService implements UserDetailsService {
    
    @Inject
    private AccountRepository accountRepository;
    
    @Inject
    private AccountPermissionRepository permissionRepository;
    
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
    
    public boolean exists(Account account) {
        if (account == null) {
            return false;
        }
            
        return accountRepository.findByUsernameIgnoreCase(account.getUsername()) != null;
    }
    
    public void signUp(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account must not be null");
        }
        
        AccountEncryption accountEncryption = new AccountEncryption(account);
        
        account.setSignUpDate(LocalDateTime.now());
        account.setPassword(accountEncryption.password());
        account.setToken(accountEncryption.token());
        account.setEnabled(false);
    }
    
    public boolean tokensAreNotTheSame(Account account, final String token) {
        if (account == null) {
            throw new IllegalArgumentException("account must not be null");
        }
        
        return !account.getToken().equals(token);
    }
    
    public void assignNewToken(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account must not be null");
        }
        
        Token newToken = new Token(Arrays.asList(account.getEmail()), new Sha512(), true);
        account.setToken(newToken.asString());
    }
    
    public void activate(Account account, final String activationToken) throws InvalidTokenException {
        if (tokensAreNotTheSame(account, activationToken)) {
            throw new InvalidTokenException("Activation token is not valid for account[" + account.getId() + "]");
        }
        
        assignNewToken(account);
        account.setEnabled(true);
    }
    
    public void resetPassword(Account account, final String resetPasswordToken, final String newPassword) throws InvalidTokenException {
        if (tokensAreNotTheSame(account, resetPasswordToken)) {
            throw new InvalidTokenException("Activation token is not valid for account[" + account.getId() + "]");
        }
        
        account.setClearPassword(newPassword);
        AccountEncryption accountEncryption = new AccountEncryption(account);
        account.setPassword(accountEncryption.password());
        account.setToken(accountEncryption.token());
        account.setEnabled(true);
    }
}
