package com.workfinder.app.controllers;

import com.workfinder.app.models.Company;
import com.workfinder.app.models.Employee;
import com.workfinder.app.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthentificationController {

    @Autowired
    private AuthentificationService authentificationService;


    @PostMapping("login")
    public String loginUser(@RequestBody Map<String, Object> userMap)
    {
        return authentificationService.loginUser(userMap);
    }


    @PostMapping("register-employee")
    public String registerEmployee(@RequestBody Employee employee)
    {
        return authentificationService.registerEmployee(employee);
    }

    @PostMapping("register-company")
    public String registerCompany(@RequestBody Company company)
    {
        return authentificationService.registerCompany(company);
    }


    @GetMapping("confirm-account")
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        return authentificationService.confirmAccount(confirmationToken);
    }


    @GetMapping("change-password")
    public String changePasswordUser(@RequestParam("token")String token)
    {
        return authentificationService.changedPassword(token);
    }

    @PostMapping("change-password")
    public String changePasswordUser(@RequestBody  Map<String, Object> userMap,@RequestParam("token")String confirmationToken)
    {
        return authentificationService.changedPassword(userMap,confirmationToken);
    }


    @PostMapping("password-forgotten")
    public String passwordForgottenPost(@RequestBody  Map<String, Object> userMap)
    {
        return authentificationService.passwordForgotten(userMap);
    }

}
