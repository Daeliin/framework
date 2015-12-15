package com.daeliin.framework.security.mock.userpermission.controller;

import com.daeliin.framework.core.resource.controller.ResourceController;
import static com.daeliin.framework.security.mock.Application.API_ROOT_PATH;
import com.daeliin.framework.security.mock.userpermission.model.UserPermission;
import com.daeliin.framework.security.mock.userpermission.service.UserPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/userpermissions")
public class UserPermissionController extends ResourceController<UserPermission, Long, UserPermissionService> {
}
