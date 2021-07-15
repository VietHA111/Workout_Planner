package model;

import model.exceptions.InvalidWorkoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    private Workout workout;
    private int time;
    private int day;

    @BeforeEach
    public void setup() {
        time = 8;
        day = 2;
        try {
            workout = new Workout(time,day);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void constructorTest1() {
        try {
            Workout w1 = new Workout(0, 7);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }

        try {
            Workout w2 = new Workout(3, 3);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }

        try {
            Workout w3 = new Workout(-1, 4);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }

        try {
            Workout w4 = new Workout(2, 8);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }

        try {
            Workout w5 = new Workout(24, 3);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }

        try {
            Workout w5 = new Workout(2, -1);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor2() {
        try {
            Workout w1 = new Workout(0, 7, true);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }

        try {
            Workout w2 = new Workout(3, 3, false);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }

        try {
            Workout w3 = new Workout(-1, 4, true);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }

        try {
            Workout w4 = new Workout(2, 8, false);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }

        try {
            Workout w5 = new Workout(24, 3, true);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }

        try {
            Workout w5 = new Workout(2, -1, false);
            fail();
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCompleteWorkout() {
        workout.completeWorkout();
        assertTrue(workout.getCompleted());
    }

    @Test
    public void testAfterThis() {
        try {
            Workout workout1 = new Workout(time+1,day);
            Workout workout2 = new Workout(time-1,day);
            Workout workout3 = new Workout(time+1,day-1);
            Workout workout4 = new Workout(time-1,day+1);

            assertFalse(workout.afterThis(workout1));
            assertTrue(workout.afterThis(workout2));
            assertTrue(workout.afterThis(workout3));
            assertFalse(workout.afterThis(workout4));
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testEquals() {
        try {
            Workout w1 = new Workout(1, 1, true);
            Workout w2 = new Workout(1,1,true);
            Workout w3 = new Workout(1,2,true);
            Workout w4 = new Workout(3,1,true);
            Workout w5 = new Workout(1,1,false);
            Workout w6 = new Workout(5,3,false);
            Workout w7 = new Workout(5,3, false);
            Set<Workout> workoutSet1 = new HashSet<>();
            Set<Workout> workoutSet2 = new HashSet<>();
            assertNotEquals(w1, 1);
            workoutSet1.add(w1);
            workoutSet2.add(w2);
            assertEquals(w2, w1);
            assertEquals(workoutSet1, workoutSet2);
            workoutSet1.add(w3);
            assertNotEquals(workoutSet1, workoutSet2);
            workoutSet2.add(w4);
            assertNotEquals(workoutSet1, workoutSet2);
            assertNotEquals(w3, w1);
            assertNotEquals(w4, w1);
            assertNotEquals(w5, w1);
            assertNotEquals(w3, w7);
            assertNotEquals(w4, w7);
            assertNotEquals(w3, w4);
            assertEquals(w7,w6);
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
            fail();
        }
    }
}