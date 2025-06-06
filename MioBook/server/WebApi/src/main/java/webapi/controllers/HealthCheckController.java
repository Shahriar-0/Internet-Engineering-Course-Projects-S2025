package webapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapi.accesscontrol.Access;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {
    
    @GetMapping
    @Access(isWhiteList = true)
    public String healthCheck() {
        return "OK";
    }
}
