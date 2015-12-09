package com.daeliin.framework.sample.configuration;

import com.daeliin.framework.commons.security.membership.MailingMemberShipNotifications;
import com.daeliin.framework.sample.api.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class SimpleMailingMembershipNotification extends MailingMemberShipNotifications<User> {
}
