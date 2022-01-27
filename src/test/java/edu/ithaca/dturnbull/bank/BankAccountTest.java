package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
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
        assertTrue( BankAccount.isEmailValid(".abcdef@mail.com"));             
        assertTrue( BankAccount.isEmailValid("abcdef.@mail.com"));             
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
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}