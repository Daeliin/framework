package com.daeliin.framework.sample.user;

import com.daeliin.framework.core.controller.ResourceController;
import com.daeliin.framework.security.details.UserPermission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userpermission")
public class UserPermissionController extends ResourceController<UserPermission, Long, UserPermissionService>{
}
