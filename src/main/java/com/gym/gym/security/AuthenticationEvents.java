package com.gym.gym.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvents {

    //@EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent failures) {
        log.error("HERE IS THE ERROR");
    }
}
