package model;

import model.exceptions.InvalidWorkoutException;
import model.exceptions.NoUncompletedWorkoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutListTest {

    private WorkoutList workoutList;
    private Workout w1;
    private Workout w2;

    @BeforeEach
    public void setup() {
        workoutList = new WorkoutList();
        try {
            w1 = new Workout(1,1);
            w2 = new Workout(2,6);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestNameConstructor() {
        workoutList = new WorkoutList("My Regular Workout");
        assertEquals("My Regular Workout", workoutList.getName());
    }

    @Test
    public void testGetNextWorkout() {
        try {
            workoutList.getNextWorkout();
            fail();
        } catch (NoUncompletedWorkoutException e) {
            e.printStackTrace();
        }

        try {
            workoutList.addWorkout(w1);
            assertEquals(w1, workoutList.getNextWorkout());
            workoutList.addWorkout(w2);
            assertEquals(w1, workoutList.getNextWorkout());
            workoutList.completeWorkout();
            assertEquals(w2, workoutList.getNextWorkout());
        } catch (NoUncompletedWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testAddWorkout() {
        try {
            Workout w3 = new Workout(3,5);

            workoutList.addWorkout(w1);
            workoutList.addWorkout(w1);
            assertEquals(w1, workoutList.getNextWorkout());
            assertEquals(1, workoutList.listSize());

            workoutList.addWorkout(w2);
            assertEquals(w1, workoutList.getNextWorkout());
            assertEquals(2, workoutList.listSize());

            workoutList.completeWorkout();
            assertEquals(w2, workoutList.getNextWorkout());

            workoutList.addWorkout(w3);
            assertEquals(w3, workoutList.getNextWorkout());
            assertEquals(3, workoutList.listSize());
        } catch (InvalidWorkoutException | NoUncompletedWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetCompletedWorkouts() {
        try {
            Workout w3 = new Workout(5,7);
            ArrayList<Workout> completedWorkouts = new ArrayList<>();

            workoutList.addWorkout(w1);
            workoutList.addWorkout(w2);

            workoutList.completeWorkout();
            completedWorkouts.add(w1);
            assertEquals(completedWorkouts, workoutList.getCompletedWorkouts());
            workoutList.completeWorkout();
            completedWorkouts.add(w2);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testCompleteNextWorkout() {
        ArrayList<Workout> completedWorkouts = new ArrayList<>();

        workoutList.completeWorkout();
        assertEquals(0, workoutList.getCompletedWorkouts().size());

        workoutList.addWorkout(w1);
        workoutList.addWorkout(w2);

        workoutList.completeWorkout();
        completedWorkouts.add(w1);
        assertEquals(completedWorkouts, workoutList.getCompletedWorkouts());

        workoutList.completeWorkout();
        completedWorkouts.add(w2);
        assertEquals(completedWorkouts, workoutList.getCompletedWorkouts());
    }

    @Test
    public void testRemoveNextWorkout() {
        try {
            Workout w3 = new Workout(1,7);
            Workout w4 = new Workout(3,7);
            workoutList.addWorkout(w1);
            workoutList.addWorkout(w2);
            workoutList.addWorkout(w3);
            workoutList.addWorkout(w4);

            workoutList.removeNextWorkout();
            assertEquals(0, workoutList.getCompletedWorkouts().size());
            assertEquals(w2, workoutList.getNextWorkout());
            assertEquals(3, workoutList.listSize());
            workoutList.completeWorkout();
            workoutList.removeNextWorkout();
            assertEquals(1, workoutList.getCompletedWorkouts().size());
            assertEquals(2, workoutList.listSize());
            assertEquals(w4, workoutList.getNextWorkout());
            workoutList.completeWorkout();
            workoutList.removeNextWorkout();
            assertEquals(2, workoutList.listSize());
        } catch (InvalidWorkoutException | NoUncompletedWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testExistsIncomplete() {
        assertFalse(workoutList.incompleteExists());
        workoutList.addWorkout(w1);
        assertTrue(workoutList.incompleteExists());
        workoutList.completeWorkout();
        assertFalse(workoutList.incompleteExists());
    }

    @Test
    public void testGetWorkouts() {
        workoutList = new WorkoutList();
        assertEquals(0,workoutList.getWorkouts().size());
        workoutList.addWorkout(w1);
        workoutList.addWorkout(w2);
        assertEquals(w1,workoutList.getWorkouts().get(0));
        assertEquals(w2,workoutList.getWorkouts().get(1));
    }

    @Test
    public void testRemoveWorkout() {
        try {
            workoutList = new WorkoutList();
            workoutList.addWorkout(w1);
            workoutList.addWorkout(w2);
            workoutList.removeWorkout(2,2);
            assertEquals(2, workoutList.listSize());
            workoutList.removeWorkout(1, 1);
            assertEquals(1, workoutList.listSize());
            assertEquals(w2, workoutList.getNextWorkout());
        } catch (NoUncompletedWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testCompleteWorkout() {
        try {
            Workout w3 = new Workout(6,3);
            workoutList.addWorkout(w1);
            workoutList.addWorkout(w2);
            workoutList.completeWorkout(new Workout(2, 6));
            assertTrue(workoutList.getWorkouts().get(1).getCompleted());
            assertFalse(workoutList.getWorkouts().get(0).getCompleted());
            workoutList.completeWorkout(w3);
            assertTrue(workoutList.getWorkouts().get(1).getCompleted());
            assertFalse(workoutList.getWorkouts().get(0).getCompleted());
            workoutList.completeWorkout(new Workout(1, 1));
            assertTrue(workoutList.getWorkouts().get(0).getCompleted());
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }
}
