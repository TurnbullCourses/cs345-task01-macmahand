package edu.ithaca.dturnbull.bank;
import java.util.regex.Pattern;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email or startingBalance is invalid
     */
    public BankAccount(String email, double startingBalance){
        boolean emailValid = false;
        boolean balanceValid = false;

        if (isEmailValid(email)){
            this.email = email;
            emailValid = true;
        }
        if (isAmountValid(startingBalance)) {
            this.balance = startingBalance;
            balanceValid = true;
        }
        if (!emailValid || !balanceValid) {
            throw new IllegalArgumentException(
                "Email address: " + email + " and/or balance: " + startingBalance + " is invalid, cannot create account"
                );
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
     * <li>If amount is not valid, throw IllegalArgumentException.</li>
     * <li>If amount is larger than balance, throws InsufficientFundsException.</li>
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Amount is not valid.");
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @post increases the balance by amount if amount is valid
     * <li>If amount is not valid, throw IllegalArgumentException.</li>
     */
    public void deposit (double amount) throws IllegalArgumentException{
        if (isAmountValid(amount)) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException("Deposit amount is invalid.");
        }
    }

    /**
     * @post decreases amount from balance and deposits to another account's balance
     * <li>If amount is not valid, throw IllegalArgumentException.</li>
     * <li>If transferee is not valid, throw IllegalArgumentException.</li>
     */
    public void transfer (double amount, BankAccount transfereeAccount) throws IllegalArgumentException, InsufficientFundsException{
        if (transfereeAccount==null) {
            throw new IllegalArgumentException("Transferee account is invalid.");
        } else {
            this.withdraw(amount);
            transfereeAccount.deposit(amount);
        }
    }

    /**
     * <li>If amount is negative, return false.</li>
     * <li>If amount is has more than two decimal points, return false.</li>
     * <li> most of my code review reevals were making this method's tests more robust since it's used throughout the public methods
     */
    public static boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        } 
        String amountStr = Double.toString(Math.abs(amount)); // converts amount to string
        int integerPlaces = amountStr.indexOf('.'); // gets index of string where decimal is

        // if length is greater than decimal index by 2, too many decimals are present
        // also checks for modulo 1 for integers with lots of leading zeroes (i.e. 10.0000)
        if (((amountStr.length() - integerPlaces - 1) > 2) && (amount%1!=0))  {
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email){
        
        if (hasAdjacentSymbols(email)) { // checks for invalid symbol usage that passes through the regex
            // see private methods for implementation
            return false;
        }

        // partner implemented but highly functional
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     * @param myStr
     * @post HELPER FUNCTION : scrubs myStr to find adjacent non-letters
     * @return false if no adjacent symbols detected
     * <li>true is they ARE detected
     */
    private static boolean hasAdjacentSymbols(String myStr) {
        for (int i=0; i<myStr.length(); i++) {
            if (!isLetter(myStr.codePointAt(i))) { // if char at i is not letter
                if (i==0 || i==myStr.length()-1) { // bad char at first or last letter
                    return true;
                }
                if (!isLetter(myStr.codePointAt(i+1))) { // if next char is also a bad char
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param int codePoint
     * @post HELPER FUNCTION : checks ASCII value of provided integer
     * @return true if param is a letter 
     * <li>false if param is NOT a letter
     */
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