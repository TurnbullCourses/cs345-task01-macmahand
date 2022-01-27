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
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTestDoug(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        
    }

    @Test
    void isEmailValidTestBadPrefix(){
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com"));       // too many periods
        assertFalse( BankAccount.isEmailValid(".abc@mail.com"));           // bad character
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com	"));       // bad character
    }

    @Test
    void isEmailValidTestGoodPrefix(){
        assertTrue( BankAccount.isEmailValid("abc-d@mail.com"));           
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));           
        assertTrue( BankAccount.isEmailValid("abc@mail.com"));           
        assertTrue( BankAccount.isEmailValid("abc_def@mail.com"));           
    }

    @Test
    void isEmailValidTestBadDomain(){
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c"));           // domain too short
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com")); // invalid characters
        assertFalse( BankAccount.isEmailValid("abc.def@mail"));             // missing domain portion
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com"));        // too many domain periods
    }

    @Test
    void isEmailValidTestGoodDomain(){
        assertTrue( BankAccount.isEmailValid("abc.def@mail.cc"));           
        assertTrue( BankAccount.isEmailValid("abc.def@mail-archive.com"));           
        assertTrue( BankAccount.isEmailValid("abc.def@mail.org"));           
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));         
        
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