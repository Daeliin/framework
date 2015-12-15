package com.daeliin.framework.security.mock.permission.controller;

import com.daeliin.framework.core.resource.controller.ResourceController;
import static com.daeliin.framework.security.mock.Application.API_ROOT_PATH;
import com.daeliin.framework.security.mock.permission.model.Permission;
import com.daeliin.framework.security.mock.permission.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/permissions")
public class PermissionController extends ResourceController<Permission, Long, PermissionService> {
}
