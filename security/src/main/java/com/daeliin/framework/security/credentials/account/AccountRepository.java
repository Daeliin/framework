package com.daeliin.framework.security.credentials.account;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ResourceRepository<Account, Long> {
    
    public Account findByUsernameIgnoreCaseAndEnabled(final String username, final boolean enabled);
    
    public Account findByEmailIgnoreCase(final String email);
    
    public Account findByUsernameIgnoreCase(final String username);
}
