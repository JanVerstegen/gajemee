package com.qien.gajemee.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.qien.gajemee.exceptions.IncorrectPasswordFormatException;

@Component
public class PasswordValidator {

	public static boolean isValid(String rawPassword) {

	    boolean validPassword = true;
	    
	    Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
	    Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
	    Pattern lowerCasePatten = Pattern.compile("[a-z ]");
	    Pattern digitCasePatten = Pattern.compile("[0-9 ]");

	    if (rawPassword.length() <= 8) {
	    		validPassword = false;
	    }
	    if (!specailCharPatten.matcher(rawPassword).find()) {
    			validPassword = false;
	    }
	    if (!UpperCasePatten.matcher(rawPassword).find()) {
    			validPassword = false;
	    }
	    if (!lowerCasePatten.matcher(rawPassword).find()) {
    			validPassword = false;
	    }
	    if (!digitCasePatten.matcher(rawPassword).find()) {
    			validPassword = false;
	    }

	    return validPassword;

	}
}
