package com.workfinder.app.utils;

public class Constants {

    public static final String API_SECRET_KEY = "adoptauthapi";
    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
    public static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    public static final String SENDING_EMAIL_ERROR = "Error in sending email, try later";
    public static final String INVALID_EMAIL = "Your email is invalid";
    public static final String INVALID_PASSWORD = "Your password is invalid";
    public static final String EMAIL_ALREADY_EXIST = "This email already exists";
    public static final String EMPTY_EMAIL = "Please enter your email";
    public static final String EMAIL_NOT_EXIST = "Email does not exist, register first";
    public static final String USER_NOT_VERIFIED = "User Not Verified Yet";
    public static final String EMPTY_PASSWORD = "Please enter your password";
    public static final String PASSWORD_PATTERN_ERROR = "Password must be between 8 and 20 character and contains: \" +\n" +
            "                    \"upper cases, lower cases and digits";
    public static final String COMPLETE_REGISTRATION = "Complete Registration!";
    public static final String ACCOUNT_ALREADY_VERIFIED = "Successful : Your account is already verified";
    public static final String ACCOUNT_VERIFIED_SUCCESSFULLY = "Successful : accountVerified";
    public static final String VERFICATION_LINK_USED = "The link is used or broken, try to resend a verification request!";
    public static final String SECURITY_CODE_USED = "The security code is used or broken, try to resend a verification request!";
    public static final String EXPIRED_CODE = "Expired security code, try to resend a password forgotten request!";
    public static final String CONFIRMATION_PASSWORD_ERROR = "Your confirmation password does not match your password";;

}
