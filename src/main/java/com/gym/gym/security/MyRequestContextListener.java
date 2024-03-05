package com.gym.gym.security;

import jakarta.servlet.annotation.WebListener;
import org.springframework.web.context.request.RequestContextListener;

@WebListener
public class MyRequestContextListener extends RequestContextListener {
}
