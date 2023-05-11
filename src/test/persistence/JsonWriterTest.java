package persistence;

import exceptions.InvalidTypeException;
import model.ListOfTransactions;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests JsonWriter
// inspired by JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfTransactions lot = new ListOfTransactions();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:filename.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyList() {
        try {
            ListOfTransactions lot = new ListOfTransactions();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyList.json");
            writer.open();
            writer.write(lot);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyList.json");
            lot = reader.read();
            assertEquals(0, lot.numberOfTransactions());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidTypeException ite) {
            fail("An invalid type exception should not have been thrown.");
        }
    }

    @Test
    void testWriterGeneralList() {
        try {
            ListOfTransactions lot = new ListOfTransactions();
            Transaction t1 = new Transaction(300, "Income", "Investment");
            Transaction t2 = new Transaction(400, "expense", "entertainment");
            lot.addToList(t1);
            lot.addToList(t2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralList.json");
            writer.open();
            writer.write(lot);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralList.json");
            lot = reader.read();
            assertEquals(2, lot.numberOfTransactions());
            checkTransaction(300, "Income", "Investment", t1);
            checkTransaction(400, "expense", "entertainment", t2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidTypeException ite) {
            fail("An invalid type exception should not have been thrown.");
        }
    }
}
