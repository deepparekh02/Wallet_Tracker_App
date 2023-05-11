package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import exceptions.InvalidTypeException;
import model.ListOfTransactions;
import model.Transaction;
import org.json.*;

// Represents a reader that reads ListOfTransactions from JSON data stored in file
// inspired by JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfTransactions from file and returns it;
    // throws IOException if an error occurs reading data from file
    // throws InvalidTypeException if an invalid type was stored on the file
    public ListOfTransactions read() throws IOException, InvalidTypeException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfTransactions(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ListOFTransactions from JSON object and returns it
    private ListOfTransactions parseListOfTransactions(JSONObject jsonObject) throws InvalidTypeException {
        ListOfTransactions current = new ListOfTransactions();
        addTransactions(current,jsonObject);
        return current;
    }

    // MODIFIES: ListOfTransactions
    // EFFECTS: parses transactions from JSON object and adds them to ListOfTransactions
    private void addTransactions(ListOfTransactions current, JSONObject jsonObject) throws InvalidTypeException {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(current, nextTransaction);
        }
    }

    // MODIFIES: ListOfTransactions
    // EFFECTS: parses transactions from JSON object and adds them to ListOfTransactions
    //          throws InvalidTypeException if an invalid type was stored on the file
    private void addTransaction(ListOfTransactions lot, JSONObject jsonObject) throws InvalidTypeException {
        int amount = jsonObject.getInt("amount");
        String type = jsonObject.getString("type");
        String category = jsonObject.getString("category");
        lot.addToList(new Transaction(amount, type, category));
    }
}
