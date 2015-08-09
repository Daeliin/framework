package com.daeliin.framework.sample.api.permission.controller;

import com.daeliin.framework.core.controller.ResourceController;
import com.daeliin.framework.sample.api.permission.model.Permission;
import com.daeliin.framework.sample.api.permission.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permission")
public class PermissionController extends ResourceController<Permission, Long, PermissionService>{
}
