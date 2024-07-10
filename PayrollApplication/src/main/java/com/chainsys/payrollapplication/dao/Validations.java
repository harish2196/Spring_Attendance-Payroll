package com.chainsys.payrollapplication.dao;


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validations {

	public static double isDouble(double input) throws IllegalArgumentException {
		Scanner sc=new Scanner(System.in);
		while (true) {
			try {
				input = sc.nextDouble();
				if (input<=500 || input>=1000000) {
					System.out.println("Price has been greater than >500");
					System.out.println("Please Re-enter Valid Choice:");
				}
				else
				{
					return input;
				}
			} catch (InputMismatchException e) {
				System.out.println(e);
				System.out.println("Enter Re-enter Admin ID:");
				sc.next(); 
			}
		}	
	}

	public static int isNumeric(int input) throws IllegalArgumentException {
		Scanner sc=new Scanner(System.in);
		while (true) {
			try {
				input = sc.nextInt();
				if (input != 1 && input != 2) {
					System.out.println("Invalid Data");
					System.out.println("Please Re-enter Valid Choice:");
				}
				else
				{
					return input;
				}
			} catch (InputMismatchException e) {
				System.out.println(e);
				System.out.println("Enter Re-enter Admin ID:");
				sc.next(); 
			}
		}	
	}

	public boolean isNumerics(int input) {
		try {
			if(input<0) {
				System.out.println("Invalid Data");
				return false;
			}else {
				return true;
			}
		}catch(Exception e) {
			throw new IllegalArgumentException("Invalid Data. Please enter a valid numeric value.", e);
		}
	}


	public boolean isDle(double regNo1) {
		try {
			if(regNo1 < 0)
			{
				System.out.println("Invalid Data");
				return false;
			}else {
				return true;
			}
		}catch(Exception e) {
			throw new IllegalArgumentException("Invalid Data. Please enter a valid numeric value.", e);
		}
	}

	public static boolean validateString(String input) throws IllegalArgumentException {
		try {
			String regex = "^[a-zA-Z]+$";
			if(input.matches(regex)) {
				return true;
			}
		} catch(Exception e) {
			throw new IllegalArgumentException("Invalid Data. Please enter only letters (both uppercase and lowercase).", e);
		}
		return false;
	}


	public boolean longNumerics(long regNo1) {
		try {
			if(regNo1 < 0)
			{
				System.out.println("Invalid Data");
				return false;
			}else {
				return true;
			}
		}catch(Exception e){
			throw new IllegalArgumentException("Invalid Data. Please enter a valid numeric value.", e);
		}
	}

	public boolean stringChecker(String s) 
	{
		try
		{
			Integer.parseInt(s);
			System.out.println("Dont use numbers");
			return false;
		}
		catch(Exception e){
			return true;
		}
	}

	public boolean isPassword(String password) {
		try {
			String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+.])(?=.*\\d).{8,}$";
			if (password.matches(regex)) {
				return true;
			} else {
				throw new IllegalArgumentException("Invalid Data. Please enter a valid Password.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}


	public boolean isSpecialChar(String input)
	{
		String specialCharRegex = "^[^a-zA-Z0-9]+[!@#$%^&*()]+$";
		if(Pattern.matches(specialCharRegex, input))
		{
			System.out.println("Invalid!.Dont use Special Characters");
			return false;
		}
		return true;
	}

	public boolean isEmailChecker(String input) {
	    String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    if (input.matches(emailPattern)) {
	        return true; 
	    } else {
	        System.out.println("Invalid!");
	        return false; 
	    }
	}
	
	public boolean isPhoneNumber(String contact) {
	    String numberStr = String.valueOf(contact);	    
	    String phoneNumberPattern = "[0-9]{10}";	    
	    if (numberStr.matches(phoneNumberPattern)) {
	        return true; 
	    } else {
	        System.out.println("Invalid!");
	        return false; 
	    }
	}

	 public boolean isValidDate(String input) {
	        String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
	        if (!Pattern.matches(dateRegex, input)) {
	            System.out.println("Invalid date format! Please enter date in yyyy-MM-dd format.");
	            return false;
	        }
			return true;
	 }

}
