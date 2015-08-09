package com.daeliin.framework.sample.api.userpermission.controller;

import com.daeliin.framework.sample.api.userpermission.service.UserPermissionService;
import com.daeliin.framework.core.controller.ResourceController;
import com.daeliin.framework.sample.api.userpermission.model.UserPermission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userpermission")
public class UserPermissionController extends ResourceController<UserPermission, Long, UserPermissionService>{
}
