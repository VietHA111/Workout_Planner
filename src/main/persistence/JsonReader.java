package persistence;

import model.Workout;
import model.WorkoutList;
import model.exceptions.InvalidWorkoutException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// Code is based on JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkoutList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout list from JSON object and returns it
    private WorkoutList parseWorkoutList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WorkoutList wl = new WorkoutList(name);
        addWorkouts(wl, jsonObject);
        return wl;
    }

    // MODIFIES: wl
    // EFFECTS: parses workouts from JSON object and adds them to workout list
    private void addWorkouts(WorkoutList wl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("workout list");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            addWorkout(wl, nextWorkout);
        }
    }

    // MODIFIES: wl
    // EFFECTS: parses workouts from JSON object and adds it to workout list
    //          if InvalidWorkoutException, do not add a workout
    private void addWorkout(WorkoutList wl, JSONObject jsonObject) {
        try {
            int day = jsonObject.getInt("day");
            int time = jsonObject.getInt("time");
            boolean status = jsonObject.getBoolean("status");
            Workout workout = new Workout(time, day, status);
            wl.addWorkout(workout);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }
    }
}
