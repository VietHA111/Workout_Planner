package persistence;

import model.Workout;
import model.WorkoutList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
// Code is based on JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class  JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkoutList wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkoutList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkoutList.json");
        try {
            WorkoutList wl = reader.read();
            assertEquals("Workout list", wl.getName());
            assertEquals(0, wl.listSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkoutList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkoutList.json");
        try {
            WorkoutList wl = reader.read();
            assertEquals("Regular workout", wl.getName());
            List<Workout> workouts = wl.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout(1, 1, true, workouts.get(0));
            checkWorkout(2, 6, false, workouts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderExceptionWorkoutList() {
        JsonReader reader = new JsonReader("./data/testExceptionWorkoutList.json");
        try {
            WorkoutList wl = reader.read();
            assertEquals("Regular workout", wl.getName());
            List<Workout> workouts = wl.getWorkouts();
            assertEquals(0, workouts.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
