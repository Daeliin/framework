package com.daeliin.framework.security.credentials.permission;

import com.daeliin.framework.security.credentials.AdminResourceController;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/permissions")
public class PermissionController extends AdminResourceController<Permission, Long, PermissionService> {
}
