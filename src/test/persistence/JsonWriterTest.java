package persistence;

import model.Workout;
import model.WorkoutList;
import model.exceptions.InvalidWorkoutException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code is based on JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WorkoutList wl = new WorkoutList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkoutList() {
        try {
            WorkoutList wl = new WorkoutList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkoutList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkoutList.json");
            wl = reader.read();
            assertEquals("Workout list", wl.getName());
            assertEquals(0, wl.listSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkoutList() {
        try {
            WorkoutList wl = new WorkoutList("Regular workout");
            wl.addWorkout(new Workout(1,1, true));
            wl.addWorkout(new Workout(2,6));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkoutList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkoutList.json");
            wl = reader.read();
            assertEquals("Regular workout", wl.getName());
            List<Workout> workouts = wl.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout(1, 1, true, workouts.get(0));
            checkWorkout(2, 6, false, workouts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }
}
