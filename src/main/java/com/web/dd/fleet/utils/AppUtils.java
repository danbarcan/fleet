package com.web.dd.fleet.utils;

import com.web.dd.fleet.security.UserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    private AppUtils() {
    }

    public static UserPrincipal getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserPrincipal) authentication.getPrincipal();
        }

        return null;
    }
}
