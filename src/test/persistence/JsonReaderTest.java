package persistence;

import exceptions.InvalidTypeException;
import model.ListOfTransactions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests JsonReader
// inspired by JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader(".data/noSuchFile.json");
        try {
            ListOfTransactions lot = reader.read();
            fail("No such file exists");
        } catch (IOException e) {
            // pass
        } catch (InvalidTypeException e) {
            fail("Should not have thrown InvalidTypeException");
        }
    }

    @Test
    void testReaderGeneralListOfTransactions() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralList.json");
        try {
            ListOfTransactions lot = reader.read();
            assertEquals(2, lot.numberOfTransactions());
            assertEquals(200, lot.getTransaction(0).getAmount());
            assertEquals(true, lot.getTransaction(0).getType());
            assertEquals("Job", lot.getTransaction(0).getCategory());
            assertEquals(100, lot.getTransaction(1).getAmount());
            assertEquals(false, lot.getTransaction(1).getType());
            assertEquals("Food", lot.getTransaction(1).getCategory());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidTypeException e) {
            fail("Should not have thrown InvalidTypeException");
        }
    }

    @Test
    void testReaderInvalidTypeException() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidTypeException.json");
        try {
            reader.read();
            fail("Should have thrown an InvalidTypeException");
        } catch (InvalidTypeException e) {
            // pass
        } catch (IOException e) {
            fail("Should not have thrown an IOException.");
        }
    }
}
