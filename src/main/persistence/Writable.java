package persistence;

import org.json.JSONObject;

//Interface for classes that can be saved in Json file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
