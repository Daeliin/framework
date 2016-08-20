package com.daeliin.components.security.credentials.account;

import com.daeliin.components.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends ResourceService<Account, Long, AccountRepository> {
}
