package com.workfinder.app.services;

import com.workfinder.app.models.Company;
import com.workfinder.app.models.ConfirmationToken;
import com.workfinder.app.models.Employee;
import com.workfinder.app.models.User;
import com.workfinder.app.repositories.ConfirmationTokenRepository;
import com.workfinder.app.repositories.UserRepository;
import com.workfinder.app.utils.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthentificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailSenderService emailSenderService;

    //password pattern
    String password_regex = Constants.PASSWORD_REGEX;
    Pattern password_pattern = Pattern.compile(password_regex);

    //generate "password forgotten" verification code
    private String generateStrings()
    {
        return RandomStringUtils.randomAlphanumeric(5);
    }


    private boolean timeOutForLinkValidity(Date date1, ConfirmationToken confirmationToken)
    {
        Date date2 = confirmationToken.getCreateDate();
        long difference_In_Time= date1.getTime() - date2.getTime();
        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60));
        return (difference_In_Hours>48);

    }

    private ConfirmationToken createConfirmationToken(ConfirmationToken confirmationToken, User user) {

        if (confirmationToken == null)// create a new one
        {
            confirmationToken = new ConfirmationToken(user);
        }
        else {
            if (timeOutForLinkValidity(new Date(), confirmationToken))
            {
                confirmationToken.setConfirmationToken();
                confirmationToken.setCreateDate(new Date());
            }
            else
                confirmationToken = null;

        }
        return confirmationToken;

    }

    private String sendEmail(User user, ConfirmationToken confirmationToken, String subject, String text)
    {
        try
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setFrom("mohameedtaoufik@gmail.com");
            mailMessage.setText(text+confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail(mailMessage);
            return "Successful : An email has been sent to "+user.getEmail();
        }
        catch (Exception e)
        {
            return e.toString();
        }


    }

    public String loginUser(Map<String ,Object> userMap)
    {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        if(email.isEmpty())
            return Constants.EMPTY_EMAIL;
        if(password.isEmpty())
            return Constants.EMPTY_PASSWORD;

        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent())
            return Constants.EMAIL_NOT_EXIST;
        if(!user.get().isVerified())
            return Constants.USER_NOT_VERIFIED;
        if(!BCrypt.checkpw(password,user.get().getPassword()))
            return Constants.INVALID_PASSWORD;
        generateJWTToken(user.get());
        return "Bearer "+user.get().getToken();
    }

    public String registerCompany(Company company)
    {
        Pattern email_pattern = Pattern.compile(Constants.EMAIL_REGEX);

        String message;
        String email = company.getEmail();
        String password = company.getPassword();
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(10));

        ConfirmationToken confirmationToken = new ConfirmationToken();

        if(!email_pattern.matcher(email).matches())
            return Constants.INVALID_EMAIL;
        if(password.isEmpty())
            return Constants.EMPTY_PASSWORD;
        if(!password_pattern.matcher(password).matches())
            return Constants.PASSWORD_PATTERN_ERROR;
        if(userRepository.findByEmail(email.toLowerCase()).isPresent() )
            return Constants.EMAIL_ALREADY_EXIST;
        try
        {
            company.setPassword(hashedPassword);
            companyService.addCompany(company);
            confirmationToken = new ConfirmationToken(company);
            message =  sendEmail(company,confirmationToken,Constants.COMPLETE_REGISTRATION,"To confirm your account, please click here : "
                    +"https://127.0.0.1/api/v1/confirm-account?token=") ;

            confirmationTokenRepository.save(confirmationToken);
            if (!message.contains("Successful"))
                confirmationTokenRepository.delete(confirmationToken);
            return message;
        }
        catch(Exception e) {
            return e.toString();
        }
    }

    public String registerEmployee(Employee employee)
    {
        Pattern email_pattern = Pattern.compile(Constants.EMAIL_REGEX);

        String message;
        String email = employee.getEmail();
        String password = employee.getPassword();
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(10));

        ConfirmationToken confirmationToken = new ConfirmationToken();

        if(!email_pattern.matcher(email).matches())
            return Constants.INVALID_EMAIL;
        if(password.isEmpty())
            return Constants.EMPTY_PASSWORD;
        if(!password_pattern.matcher(password).matches())
            return Constants.PASSWORD_PATTERN_ERROR;
        if(userRepository.findByEmail(email.toLowerCase()).isPresent() )
            return Constants.EMAIL_ALREADY_EXIST;

        try
        {
                employee.setPassword(hashedPassword);
                employeeService.addEmployee(employee);
                confirmationToken = new ConfirmationToken(employee);
                message =  sendEmail(employee,confirmationToken,Constants.COMPLETE_REGISTRATION,"To confirm your account, please click here : "
                        +"https://127.0.0.1/api/v1/confirm-account?token=") ;

            confirmationTokenRepository.save(confirmationToken);
            if (!message.contains("Successful"))
                confirmationTokenRepository.delete(confirmationToken);
            return message;
        }
        catch(Exception e) {
            return e.toString();
        }
    }

    public String confirmAccount(String confirmationToken)
    {
        try {

            ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

            if (token != null) {
                if(userRepository.findById(token.getUser().getId()).get().isVerified())
                    return Constants.ACCOUNT_ALREADY_VERIFIED;
                Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
                user.get().setVerified(true);
                userRepository.save(user.get());
                confirmationTokenRepository.delete(token);
                return Constants.ACCOUNT_VERIFIED_SUCCESSFULLY;
            } else {
                return Constants.VERFICATION_LINK_USED;
            }

        }
        catch(Exception e){
            return "Something is going wrong, exception message : " + e.getMessage();
        }
    }

    //GET method
    public String changedPassword(String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        if (confirmationToken == null)
            return Constants.SECURITY_CODE_USED;

        if(timeOutForLinkValidity(new Date(),confirmationToken))
            return Constants.EXPIRED_CODE;
        else
            return "Success";
    }

    //POST method
    public String changedPassword(Map<String ,Object> userMap,String confirmationToken)
    {

        String password = (String) userMap.get("password");
        String password_conformation = (String) userMap.get("password_confirmation");
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt(10));

        if(password==null || password.isEmpty())
            return Constants.EMPTY_PASSWORD;
        if(!password_pattern.matcher(password).matches())
            return Constants.PASSWORD_PATTERN_ERROR;
        if(!password.equals(password_conformation))
            return Constants.CONFIRMATION_PASSWORD_ERROR;

        try {
            ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
            if (token != null) {
                if(timeOutForLinkValidity(new Date(),token))
                    return Constants.VERFICATION_LINK_USED;
                Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
                user.get().setPassword(hashedPassword);
                userRepository.save(user.get());
                confirmationTokenRepository.delete(token);
                return "Successful : Password changed successfully";
            } else {
                return Constants.VERFICATION_LINK_USED;
            }
        }
        catch(Exception e){
            return "Something is going wrong, exception message";
        }
    }

    public String passwordForgotten(Map<String ,Object> userMap)
    {
        String email = (String) userMap.get("email");
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent())
            return Constants.EMAIL_NOT_EXIST;
        try
        {
            ConfirmationToken confirmationToken = createConfirmationToken(confirmationTokenRepository.findUserByUserId(user.get().getId()),user.get());

            if(confirmationToken==null)
                return "The security code is already sent,you need to wait 48 hours";

            confirmationToken.setConfirmationToken(generateStrings());
            confirmationTokenRepository.save(confirmationToken);

            String message =  sendEmail(user.get(),confirmationToken,"Password Forgotten","To change your password, please navigate to your application and enter this code : ");
            if (!message.contains("Successful"))
                confirmationTokenRepository.delete(confirmationToken);
            return message;
        }
        catch(Exception e)
        {
            return "Error";
        }
    }



    private Map<String ,String> generateJWTToken(User user)
    {
        long timestamp = System.currentTimeMillis();// we will use this for deleting the token in (now+TOKEN_VALIDITY)
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("id_user",user.getId())
                .claim("email",user.getEmail())

                .compact();
        user.setToken(token);
        userRepository.save(user);
        Map<String ,String> map = new HashMap<>();
        map.put("token",token);
        return map;

    }
}


