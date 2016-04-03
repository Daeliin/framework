package com.daeliin.framework.commons.security.credentials.account;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistentAccountRepository extends ResourceRepository<PersistentAccount, Long> {
    
    public Account findByUsernameIgnoreCaseAndEnabled(final String username, final boolean enabled);
    
    public Account findByEmailIgnoreCase(final String email);
    
    public Account findByUsernameIgnoreCase(final String username);
}
