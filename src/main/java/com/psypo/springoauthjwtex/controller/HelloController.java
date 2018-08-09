package com.psypo.springoauthjwtex.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Rimantas Jacikevičius on 18.8.5.
 */
@RequestMapping("test")
@RestController
public class HelloController {

    @RequestMapping("/scope")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('read')")
    public Principal scopeTest(@AuthenticationPrincipal Principal principal) {
        return principal;
    }

    @RequestMapping("/role")
    @ResponseBody
    @PreAuthorize("#oauth2.clientHasRole('authorised')")
    public Principal authorityTest(@AuthenticationPrincipal Principal principal) {
        return principal;
    }

    @RequestMapping("/all")
    public String foo() {
        return "hello!";
    }


}
