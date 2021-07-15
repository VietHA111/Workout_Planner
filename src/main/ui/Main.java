package ui;

import model.WorkoutList;

//Main class to run Workout Planner
public class Main {
    public static void main(String[] args) {
        WorkoutPlannerVisualInterface view = new WorkoutPlannerVisualInterface();
        WorkoutList model = new WorkoutList();
        WorkoutPlannerController controller = new WorkoutPlannerController(view, model);
//        try {
//            new WorkoutPlannerApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        }
    }
}
