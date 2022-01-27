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