package com.example.web_task_manager.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @author: GMO_ThanhND
 * @version: 1.0
 * @since 1/24/2022 10:49 PM
 */

@Service
@RequiredArgsConstructor
public class CookieUtil {
    @Autowired
    private HttpServletResponse resp;
    @Autowired
    private HttpServletRequest req;

    public Cookie add(String name, String value, int hours) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(hours);
        cookie.setPath("/");
        resp.addCookie(cookie);
        return cookie;
    }

    public String get(String name){
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equalsIgnoreCase(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}