package model;

// Workout class represents a workout

import model.exceptions.InvalidWorkoutException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

public class Workout implements Writable {

    private final int time; // Workout time, hour of the day between 0 and 23
    private final int day;  // Workout day. 1 is Monday, 2 is Tuesday and so on
    private boolean completed; //true if workout is completed, false otherwise

    //EFFECTS: creates Workout object with time t, day d and completed = false
    public Workout(int t, int d) throws InvalidWorkoutException {
        if (t < 0 || t > 23 || d < 1 || d > 7) {
            throw new InvalidWorkoutException();
        }
        time = t;
        day = d;
        completed = false;
    }

    //EFFECTS: creates Workout object with time t, day d and completed = status
    public Workout(int t, int d, boolean status) throws InvalidWorkoutException {
        if (t < 0 || t > 23 || d < 1 || d > 7) {
            throw new InvalidWorkoutException();
        }
        time = t;
        day = d;
        completed = status;
    }

    //MODIFIES: this
    //EFFECTS: Complete workout
    public void completeWorkout() {
        completed = true;
    }

    //EFFECTS: Returns true if Workout w comes after this
    public boolean afterThis(Workout w) {
        if (w.getDay() == day) {
            return w.getTime() < time;
        } else {
            return w.getDay() < day;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Workout workout = (Workout) o;
        return time == workout.time
                && day == workout.day
                && completed == workout.completed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day, completed);
    }

    public boolean getCompleted() {
        return completed;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("day", day);
        json.put("status", completed);
        return json;
    }
}
