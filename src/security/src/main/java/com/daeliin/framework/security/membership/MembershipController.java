package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.core.controller.ResourceController;
import com.daeliin.framework.core.service.FullCrudService;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MembershipController<E extends UserDetails, ID extends Serializable, S extends FullCrudService<E, ID>>
    extends ResourceController<E, ID, S> {
    
    @Autowired
    S userDetailsService;
}
