package com.daeliin.framework.security.credentials;

import com.daeliin.framework.commons.security.credentials.accountpermission.AccountPermission;
import com.daeliin.framework.commons.security.credentials.accountpermission.AccountPermissionService;
import com.daeliin.framework.core.resource.controller.ResourceController;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/accountpermissions")
public class AccountPermissionController extends ResourceController<AccountPermission, Long, AccountPermissionService> {
}
