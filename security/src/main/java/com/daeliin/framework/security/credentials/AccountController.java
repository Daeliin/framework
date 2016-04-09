package com.daeliin.framework.security.credentials;

import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.commons.security.credentials.account.AccountService;
import com.daeliin.framework.commons.security.exception.WrongAccessException;
import com.daeliin.framework.core.resource.controller.ResourceController;
import com.daeliin.framework.core.resource.exception.PageRequestException;
import com.daeliin.framework.core.resource.exception.ResourceNotFoundException;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import com.daeliin.framework.security.session.SessionHelper;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/accounts")
public class AccountController extends ResourceController<Account, Long, AccountService> {

    private final SessionHelper sessionHelper;
    private final AccountService accountService;
    
    @Autowired
    public AccountController(final SessionHelper sessionHelper, final AccountService accountService) {
         this.sessionHelper = sessionHelper;
         this.accountService = accountService;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Account create(@Valid Account resource) {
        return super.create(resource);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Account update(@PathVariable Long id, @Valid Account resource) {
        return super.update(id, resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Page<Account> getAll(String pageNumber, String pageSize, String direction, String... properties) throws PageRequestException {
        return super.getAll(pageNumber, pageSize, direction, properties);
    }

    @Override
    public Account getOne(@PathVariable Long id) {
        if (!sessionHelper.currentAccountIs(accountService.findOne(id))) {
            throw new WrongAccessException("You're not allowed to see this account");
        }
        
        return super.getOne(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void delete(@PathVariable Long id) throws ResourceNotFoundException {
        super.delete(id);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void delete(List<Long> ids) {
        super.delete(ids);
    }
}
