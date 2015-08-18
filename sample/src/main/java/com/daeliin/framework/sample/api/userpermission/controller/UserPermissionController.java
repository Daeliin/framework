package com.daeliin.framework.sample.api.userpermission.controller;

import com.daeliin.framework.sample.api.userpermission.service.UserPermissionService;
import com.daeliin.framework.core.controller.ResourceController;
import static com.daeliin.framework.sample.Application.API_ROOT_PATH;
import com.daeliin.framework.sample.api.userpermission.model.UserPermission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "userpermissions")
public class UserPermissionController extends ResourceController<UserPermission, Long, UserPermissionService>{
}
