package bank;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class BankDataBaseTest {
    BankDataBase bankDataBase;

    @Before
    public void setUp() throws Exception {
        bankDataBase = new BankDataBase();
    }

    @Test
    public void addAndGetAccount() throws SQLException {
        BankAccount actualBankAccount = new BankAccount(
                "Mamal", "12345", "Parsa", "Mohammadian");
        bankDataBase.addAccount(actualBankAccount);
        BankAccount bankAccount = bankDataBase.getAccount("Mamal");
        assertEquals(actualBankAccount, bankAccount);
        BankAccount bankAccount1 = bankDataBase.getAccount("This account does not exist");
        assertNull(bankAccount1);
    }
}