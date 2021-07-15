package model;

// Represents a list of Workouts

import model.exceptions.NoUncompletedWorkoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class WorkoutList implements Writable {
    private final List<Workout> workoutList;

    private String name;

    public WorkoutList() {
        workoutList = new ArrayList<>();
        name = "Workout list";
    }

    public WorkoutList(String name) {
        workoutList = new ArrayList<>();
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: adds Workout w to WorkoutList at appropriate index according to chronological order
    //         if w is already in WorkoutList, do nothing
    public void addWorkout(Workout w) {
        if (!workoutList.contains(w)) {
            int i = 0;
            if (workoutList.size() == 0) {                    //if the list is empty, add the workout
                workoutList.add(w);
            } else {
                while (i < workoutList.size()) {
                    if (!w.afterThis(workoutList.get(i))) {   //if the workout comes before this, add the workout
                        workoutList.add(i, w);                //if not, check the next workout
                        break;
                    }
                    i++;
                }
                if (i == workoutList.size()) {                //if the workout did not come before any workout,
                    workoutList.add(i, w);                     //add it to the end of the list
                }
            }
        }
    }

    //EFFECTS: return list of workouts
    public List<Workout> getWorkouts() {
        return workoutList;
    }

    //EFFECTS: return next workout that is not completed
    //         if there is no uncompleted workout, throw NoUncompletedWorkoutException
    public Workout getNextWorkout() throws NoUncompletedWorkoutException {
        for (Workout w: workoutList) {
            if (!w.getCompleted()) {
                return w;
            }
        }
        throw new NoUncompletedWorkoutException();
    }

    //EFFECTS: returns a list of completed workouts
    public List<Workout> getCompletedWorkouts() {
        List<Workout> completedWorkouts = new ArrayList<>();
        for (Workout w: workoutList) {
            if (w.getCompleted()) {
                completedWorkouts.add(w);
            } else {
                return completedWorkouts;
            }
        }
        return completedWorkouts;
    }

    //MODIFIES: this
    //EFFECTS: completes next uncompleted workout,
    //         if there is no incomplete workout, do nothing
    public void completeWorkout() {
        for (Workout w: workoutList) {
            if (!w.getCompleted()) {
                w.completeWorkout();
                break;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: completes workout at given time and day
    public void completeWorkout(Workout wv) {
        int intContains = 0;
        boolean contains = false;
        for (Workout w: workoutList) {
            if (wv.equals(w)) {
                intContains = workoutList.indexOf(w);
                contains = true;
            }
        }
        if (contains) {
            workoutList.get(intContains).completeWorkout();
        }
    }

    //MODIFIES: this
    //EFFECTS: remove workout at time t and day d
    public void removeWorkout(int t, int d) {
        int intRemove = 0;
        boolean contains = false;
        for (Workout w: workoutList) {
            if (w.getTime() == t && w.getDay() == d) {
                intRemove = workoutList.indexOf(w);
                contains = true;
            }
        }
        if (contains) {
            workoutList.remove(intRemove);
        }
    }

    //MODIFIES: this
    //EFFECTS: removes next uncompleted workout
    //         if there is no incomplete workout, do nothing
    public void removeNextWorkout() {
        int i = 0;
        for (Workout w: workoutList) {
            if (w.getCompleted()) {
                i++;
            } else {
                workoutList.remove(i);
                break;
            }
        }
    }

    //EFFECTS: returns size of the workout list
    public int listSize() {
        return workoutList.size();
    }

    //EFFECTS: returns true if there is an incomplete workout in the list,
    //         else, return false
    public boolean incompleteExists() {
        for (Workout w: workoutList) {
            if (!w.getCompleted()) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("workout list", workoutListToJson());
        return json;
    }

    // EFFECTS: returns workouts in this workout list as a JSON array
    private JSONArray workoutListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout w : workoutList) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }
}
