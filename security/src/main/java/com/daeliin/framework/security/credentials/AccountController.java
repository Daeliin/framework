package com.daeliin.framework.security.credentials;

import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.commons.security.credentials.account.AccountService;
import com.daeliin.framework.core.resource.controller.ResourceController;
import com.daeliin.framework.core.resource.exception.ResourceNotFoundException;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/accounts")
public class AccountController extends ResourceController<Account, Long, AccountService> {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void delete(@PathVariable Long id) throws ResourceNotFoundException {
        super.delete(id);
    }
}
