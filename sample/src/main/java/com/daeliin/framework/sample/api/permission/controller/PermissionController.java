package com.daeliin.framework.sample.api.permission.controller;

import com.daeliin.framework.core.controller.ResourceController;
import static com.daeliin.framework.sample.Application.API_ROOT_PATH;
import com.daeliin.framework.sample.api.permission.model.Permission;
import com.daeliin.framework.sample.api.permission.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_ROOT_PATH + "/api/permissions")
public class PermissionController extends ResourceController<Permission, Long, PermissionService>{
}
