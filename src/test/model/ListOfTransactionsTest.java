package model;

import exceptions.InvalidTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

// tests the ListOfTransactions model
class ListOfTransactionsTest {
    Transaction firstIncome;
    Transaction secondIncome;
    Transaction thirdIncome;
    Transaction firstExpense;
    Transaction secondExpense;
    Transaction thirdExpense;
    ListOfTransactions testList;
    ListOfTransactions emptyList;

    @BeforeEach
    public void runBefore() {
        try {
            firstIncome = new Transaction(500, "income",
                    "Job");
        secondIncome = new Transaction(1000, "income",
                "Gift");
        thirdIncome = new Transaction(250, "income",
                "Job");
        firstExpense = new Transaction(250, "expense",
                "Entertainment");
        secondExpense = new Transaction(500, "expense",
                "electricity");
        thirdExpense = new Transaction(250, "expense",
                "Entertainment");
        testList = new ListOfTransactions();
        testList.addToList(firstIncome);
        testList.addToList(firstExpense);
        testList.addToList(secondIncome);
        testList.addToList(secondExpense);
        testList.addToList(thirdIncome);
        testList.addToList(thirdExpense);
        emptyList = new ListOfTransactions();}
        catch (InvalidTypeException e) {
            System.out.println("An exception should not have been thrown in runBefore in ListOfTransactions " +
                    "Test Class");
        }
    }

    // EFFECTS: Tests whether the constructor correctly assigns a boolean value to items and whether each transaction
    //          is allocated to the correct list.
    @Test
    public void testConstructor() {
        assertTrue(secondIncome.getType());
        assertFalse(firstExpense.getType());
        assertTrue(firstIncome.equals(testList.getIncomeTransaction(0)));
        assertTrue(firstExpense.equals(testList.getExpenseTransaction(0)));
    }

    // EFFECTS: Tests whether the calculateBalance() methods works as expected
    @Test
    public void balanceTest() {
        double testBalance = testList.calculateBalance();
        assertEquals(750, testBalance);
    }

    @Test
    public void testIsListEmpty() {
        assertTrue(emptyList.isListEmpty());
        assertFalse(testList.isListEmpty());
    }

    @Test
    public void testNumberOfTransactions() {
        assertEquals(6, testList.numberOfTransactions());
        assertEquals(0, emptyList.numberOfTransactions());
    }

    @Test
    public void testNumberOfIncomeTransactions() {
        assertEquals(3, testList.numberOfIncomeTransactions());
        assertEquals(0, emptyList.numberOfIncomeTransactions());
    }

    @Test
    public void testNumberOfExpenseTransactions() {
        assertEquals(3, testList.numberOfExpenseTransactions());
        assertEquals(0, emptyList.numberOfExpenseTransactions());
    }

    @Test
    public void testMostFrequentIncomeCategory() {
        assertEquals("Job", testList.mostFrequentIncomeCategory());
    }

    @Test
    public void testMostFrequentExpenseCategory() {
        assertEquals("Entertainment", testList.mostFrequentExpenseCategory());
    }
}