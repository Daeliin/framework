package com.daeliin.framework.commons.security.credentials.account;

import com.daeliin.framework.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class PersistentAccountService extends ResourceService<PersistentAccount, Long, PersistentAccountRepository> {
}
