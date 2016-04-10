package com.daeliin.framework.security.credentials.account;

import com.daeliin.framework.security.credentials.AdminResourceController;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import com.daeliin.framework.security.session.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/accounts")
public class AccountController extends AdminResourceController<Account, Long, AccountService> {

    private final SessionHelper sessionHelper;
    
    @Autowired
    public AccountController(final SessionHelper sessionHelper) {
        this.sessionHelper = sessionHelper;
    }
    
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Account current() {
        return sessionHelper.getCurrentAccount();
    }
}
