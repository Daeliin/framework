package com.daeliin.framework.security.credentials.accountpermissoin;

import com.daeliin.framework.security.credentials.AdminResourceController;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/accountpermissions")
public class AccountPermissionController extends AdminResourceController<AccountPermission, Long, AccountPermissionService> {
}
