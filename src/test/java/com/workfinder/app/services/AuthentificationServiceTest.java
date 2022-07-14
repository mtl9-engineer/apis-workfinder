package com.workfinder.app.services;

import com.workfinder.app.models.Company;
import com.workfinder.app.models.ConfirmationToken;
import com.workfinder.app.models.Employee;
import com.workfinder.app.models.User;
import com.workfinder.app.repositories.ConfirmationTokenRepository;
import com.workfinder.app.repositories.UserRepository;
import com.workfinder.app.utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthentificationServiceTest {

    @InjectMocks
    AuthentificationService authentificationService;

    @Mock
    UserRepository userRepository;

    @Mock
    EmployeeService employeeService;

    @Mock
    CompanyService companyService;

    @Mock
    ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    EmailSenderService emailSenderService;

    private Map<String,Object> userMap ;
    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId("UserId");
        user.setEmail("user@email.com");


    }

    @Test
    public void loginUserWithEmptyPassword(){
        userMap = new HashMap<>();
        userMap.put("email","user@gmail.com");
        userMap.put("password","");
        String result = authentificationService.loginUser(userMap);
        Assertions.assertEquals(Constants.EMPTY_PASSWORD, result);
    }

    @Test
    public void loginNotVerifiedUser(){
        userMap = new HashMap<>();
        userMap.put("email","user@gmail.com");
        userMap.put("password","123459");

        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        String result = authentificationService.loginUser(userMap);

        Assertions.assertEquals(Constants.USER_NOT_VERIFIED, result);

    }

    @Test
    public void registerCompanyInvalidEmail(){
        Company company = new Company();
        company.setId("companyId");
        company.setName("Apple");
        company.setEmail("careersgmail.com");
        company.setSize(10000);
        company.setPassword("company1234");

        String result =authentificationService.registerCompany(company);
        Assertions.assertEquals(Constants.INVALID_EMAIL , result);


    }

    @Test
    public void registerEmployeeAlreadyExists(){
        Employee employee = new Employee();
        employee.setId("employeeId");
        employee.setEmail("taoufik@gmail.com");
        employee.setPassword("HappyTest1");

        Mockito.when(userRepository.findByEmail("taoufik@gmail.com")).thenReturn(Optional.of(employee));
        String result = authentificationService.registerEmployee(employee);

        Assertions.assertEquals(Constants.EMAIL_ALREADY_EXIST,result);
    }

    @Test
    public void confirmAccount(){
        Employee employee = new Employee();
        employee.setId("employeeId");
        employee.setEmail("taoufik@gmail.com");
        employee.setPassword("HappyTest1");
        employee.setVerified(true);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(employee);
        confirmationToken.setConfirmationToken("token");

        Mockito.when(confirmationTokenRepository.findByConfirmationToken("token")).thenReturn(confirmationToken);
        Mockito.when(userRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        String result = authentificationService.confirmAccount("token");

        Assertions.assertEquals(Constants.ACCOUNT_ALREADY_VERIFIED , result);

    }

    @Test
    public void changePassword(){
        Mockito.when(confirmationTokenRepository.findByConfirmationToken("token")).thenReturn(null);
        String result = authentificationService.changedPassword("token");

        Assertions.assertEquals(Constants.SECURITY_CODE_USED , result);

    }

    @Test
    public void passwordForgotten(){
        userMap = new HashMap<>();
        userMap.put("email","user@gmail.com");
        userMap.put("password","");


        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.empty());
        String result = authentificationService.passwordForgotten(userMap);

        Assertions.assertEquals(Constants.EMAIL_NOT_EXIST, result);
    }


}
