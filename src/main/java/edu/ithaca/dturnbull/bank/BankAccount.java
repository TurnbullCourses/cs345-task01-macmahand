package edu.ithaca.dturnbull.bank;
import java.util.regex.Pattern;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * <li>If amount is negative, throw IllegalArgumentException.</li>
     * <li>If amount is larger than balance, throws InsufficientFundsException.</li>
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount < 0) {
            throw new IllegalArgumentException("Can not withdraw negative money");
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * <li>If amount is negative, return false.</li>
     * <li>If amount is has more than two decimal points, return false.</li>
     */
    public static boolean isAmountValid(double amount) {
        return false;
    }



    public static boolean isEmailValid(String email){
        if (hasAdjacentSymbols(email)) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private static boolean hasAdjacentSymbols(String email) {
        for (int i=0; i<email.length(); i++) {
            if (!isLetter(email.codePointAt(i))) { // if char at i is not letter
                if (i==0 || i==email.length()-1) { // bad char at first or last letter
                    return true;
                }
                if (!isLetter(email.codePointAt(i+1))) { // if next char is also not letter
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isLetter(int codePoint) {
        if (codePoint >= 65 && codePoint <= 90) { // if codePoint is capital letter
            return true;
        }
        if (codePoint >= 97 && codePoint <= 122) { // if codePoint is lowercase letter
            return true;
        }
        return false;
    }
}