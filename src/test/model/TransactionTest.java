package model;

import exceptions.InvalidTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests the Transaction Model
public class TransactionTest {
    Transaction incomeA;
    Transaction expenseA;
    Transaction incomeB;
    Transaction expenseB;

    @BeforeEach
    public void runBefore() {
        try {
            incomeA = new Transaction(400, "income", "Job");
            expenseA = new Transaction(200, "expense", "Food");
            incomeB = new Transaction(1000, "Income", "Investment");
            expenseB = new Transaction(100, "Expense", "Food");
        } catch (InvalidTypeException e) {
            System.out.println("An exception should not have been thrown in runBefore in ListOfTransactions " +
                    "Test Class");
        }
    }

    @Test
    public void testConstructor() {
        assertTrue(incomeA.getType());
        assertFalse(expenseA.getType());
    }

    @Test
    public void testConvertTypeToString() {
        assertEquals("income", incomeA.convertTypeToString());
        assertEquals("expense", expenseA.convertTypeToString());
    }

    @Test
    public void testConvertStringToTypeWhenValid() {
        try {
            assertTrue(incomeA.convertStringToType("income"));
            assertTrue(incomeB.convertStringToType("Income"));
            assertFalse(expenseA.convertStringToType("expense"));
            assertFalse(expenseB.convertStringToType("Expense"));
        } catch (InvalidTypeException e) {
            fail("An InvalidTypeException should not have been thrown.");
        }
    }

    @Test
    public void testConvertStringToTypeWhenInvalid() {
        try {
            incomeA.convertStringToType("Invalid Type");
            fail("An InvalidTypeException should have been thrown.");
        } catch (InvalidTypeException e) {
            // pass
        }
    }
}

