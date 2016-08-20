package com.daeliin.components.security.credentials.account;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ResourceRepository<Account, Long> {
    
    public Account findByEmailIgnoreCase(String email);
    
    public Account findByUsernameIgnoreCase(String username);
    
    public List<Account> findByEnabled(boolean enabled);
    
    public Account findByUsernameIgnoreCaseAndEnabled(String username, boolean enabled);
}
