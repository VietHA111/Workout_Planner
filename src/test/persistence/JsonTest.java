package persistence;

import model.Workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
// Code is based on JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkWorkout(int time, int day, boolean status, Workout workout) {
        assertEquals(time, workout.getTime());
        assertEquals(day, workout.getDay());
        assertEquals(status, workout.getCompleted());
    }
}
