package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
    // doesn't need test cases since solely reflects effects of other methods 
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTestDoug() throws InsufficientFundsException{
    // case : amount greater than balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(300.567));
    }

    @Test
    void withdrawTestNegative() throws InsufficientFundsException, IllegalArgumentException{
    // case : negative withdraw amount
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));
        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTestZero() throws InsufficientFundsException{
    // case : withdraw nothing
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(0);
        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTestToZero() throws InsufficientFundsException{
    // case : fully deplete account
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(200);
        assertEquals(0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTestToCents() throws InsufficientFundsException{
    // case : leave cents in the account
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(0.01);
        assertEquals(199.99, bankAccount.getBalance(), 0.001);
        bankAccount.withdraw(199.98);
        assertEquals(0.01, bankAccount.getBalance(), 0.001);
    }

    @Test
    void depositTest() throws IllegalArgumentException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(0.01); // EC : valid
        assertEquals(200.01, bankAccount.getBalance(), 0.001);
        bankAccount.deposit(799.98); // EC : edge case
        assertEquals(999.99, bankAccount.getBalance(), 0.001);
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-0.01)); // EC : negative deposit
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(5.012)); // EC : too many decimals deposit
    }

    @Test
    void transferTest() throws IllegalArgumentException, InsufficientFundsException{
        BankAccount account1 = new BankAccount("a@b.com", 200);
        BankAccount account2 = new BankAccount("a@b.com", 0);

        account1.transfer(200, account2);  // EC : valid transfer
        assertEquals(0, account1.getBalance(), 0.001);
        assertEquals(200, account2.getBalance(), 0.001);

        assertThrows(InsufficientFundsException.class, () -> account2.transfer(300, account1)); // EC : transfer more than balance
        assertEquals(0, account1.getBalance(), 0.001);
        assertEquals(200, account2.getBalance(), 0.001);

        assertThrows(InsufficientFundsException.class, () -> account2.transfer(-5, account1)); // EC : invalid amount
        assertEquals(0, account1.getBalance(), 0.001);
        assertEquals(200, account2.getBalance(), 0.001);

        account2.transfer(5.25, account1);  // EC : valid transfer with decimals
        assertEquals(5.25, account1.getBalance(), 0.001);
        assertEquals(194.75, account2.getBalance(), 0.001);
    }

    @Test
    void isAmountValidTest() {
        assertTrue(BankAccount.isAmountValid(200));     // EC : middle
        assertTrue(BankAccount.isAmountValid(10.99));   // EC : middle
        assertTrue(BankAccount.isAmountValid(10.5));   // EC : middle
        assertFalse(BankAccount.isAmountValid(-5));     // EC : negative
        assertFalse(BankAccount.isAmountValid(10.999)); // EC : too many decimals
        assertTrue(BankAccount.isAmountValid(0));       // EC : zero
        assertTrue(BankAccount.isAmountValid(10.00000));  // EC : zero
    }

    @Test
    void isEmailValidTestDoug(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string
    }

    @Test
    // tests @ presence in string
    void isEmailValidTestAts(){
        assertTrue( BankAccount.isEmailValid("abcdef@mail.com"));       // 1 @ symbol
        assertFalse( BankAccount.isEmailValid("abc@@mail.com"));        // 2 @ symbol
        assertFalse( BankAccount.isEmailValid("abcmail.com"));          // no @ symbol
    }

    @Test
    // emailwide invalid characters
    void isEmailValidTestBadChars(){
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com"));       // too many periods
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com	"));       // no # allowed
        assertFalse( BankAccount.isEmailValid("abc?def@mail.com	"));       // no ? allowed
        assertFalse( BankAccount.isEmailValid("abc+def@mail.com	"));       // no symbols allowed
    }

    @Test
    void isEmailValidTestPrefix(){
    // tests prefix
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));           
        assertFalse( BankAccount.isEmailValid(".abcdef@mail.com"));             
        assertFalse( BankAccount.isEmailValid("abcdef.@mail.com"));             
        assertFalse( BankAccount.isEmailValid("@mail.com"));         // no prefix     
    }

    @Test
    void isEmailValidTestDomain(){
    // tests domain
        assertFalse( BankAccount.isEmailValid("abcdef@mail.c"));           // domain is too short
        assertTrue( BankAccount.isEmailValid("abc.def@mail.cc"));           // domain is > 1      
        assertTrue( BankAccount.isEmailValid("abc.def@mail-archive.com"));  
        assertFalse( BankAccount.isEmailValid("abc.def@-mailarchive.com"));  
        assertFalse( BankAccount.isEmailValid("abc.def@mailarchive-.com"));  
        assertTrue( BankAccount.isEmailValid("abc.def@mail.org"));          // .org is an allowed domain 
        assertFalse( BankAccount.isEmailValid("abc.def@.org"));             // no domain 
        assertFalse( BankAccount.isEmailValid("abc.def@mail"));             // no domain extension

    }

    @Test
    void constructorTest() throws IllegalArgumentException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -200));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 100.567));

    }

}