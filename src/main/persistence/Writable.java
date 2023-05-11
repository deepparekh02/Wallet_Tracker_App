package persistence;

import org.json.JSONObject;

// interface that makes subclasses implement the toJson() method
// inspired by JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
