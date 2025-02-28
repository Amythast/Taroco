package com.nebula.oauth2.resource.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 Controller
 *
 * @author feifeixia
 * 2019/5/7 11:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("#oauth2.hasScope('All')")
    @GetMapping
    public Object user(OAuth2Authentication authentication) {
        return authentication.getUserAuthentication();
    }

    @GetMapping(value = {"/test"})
    public String test() {
        return "Test";
    }
}
