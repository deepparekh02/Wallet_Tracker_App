package persistence;

import exceptions.InvalidTypeException;
import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// class that facilitates the testing of JsonReader and JsonWriter
// inspired by JsonSerializationDemo
public class JsonTest {
    protected void checkTransaction(double amount, String type, String category, Transaction transaction) {
        assertEquals(amount, transaction.getAmount());
        assertEquals(category, transaction.getCategory());
        try {
            assertEquals(transaction.convertStringToType(type), transaction.getType());
        } catch (InvalidTypeException e) {
            fail("An exception should not have been thrown.");
        }
    }
}
