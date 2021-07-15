//package ui;
//
//import model.Workout;
//import model.WorkoutList;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;
//
//// Workout Planner Application
//
//public class WorkoutPlannerApp {
//    private static final String JSON_STORE = "./data/workoutList.json";
//    private WorkoutList workoutList;
//    private Scanner input;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//
//
//    // Much of the code and structure in this class is based on the teller app: https://github.students.cs.ubc.ca/CPSC210/TellerApp
//    // Data persistence based on JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//
//    //EFFECTS: runs the workout planner application
//    public WorkoutPlannerApp() throws FileNotFoundException {
//        runWorkoutPlanner();
//    }
//
//    //MODIFIES: this
//    //EFFECTS: processes user input
//    private void runWorkoutPlanner() {
//        boolean keepGoing = true;
//        String command = null;
//
//        init();
//
//        while (keepGoing) {
//            displayMenu();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//    }
//
//    //MODIFIES: this
//    //EFFECTS: initialises fields
//    private void init() {
//        workoutList = new WorkoutList();
//        input = new Scanner(System.in);
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//    }
//
//    //EFFECTS: displays menu of options
//    private void displayMenu() {
//        System.out.println("Select from:");
//        System.out.println("a -> add a workout");
//        System.out.println("c -> complete next workout");
//        System.out.println("n -> view next incomplete workout");
//        System.out.println("v -> view completed workouts");
//        System.out.println("d -> delete next incomplete workout");
//        System.out.println("s -> save workout list");
//        System.out.println("l -> load workout list");
//        System.out.println("q -> quit program");
//    }
//
//    //MODIFIES: this
//    //EFFECTS: process command input
//    private void processCommand(String c) {
//        if (c.equals("a")) {
//            doAddWorkout();
//        } else if (c.equals("c")) {
//            doCompleteWorkout();
//        } else if (c.equals("n")) {
//            doViewIncomplete();
//        } else if (c.equals("v")) {
//            doViewComplete();
//        } else if (c.equals("d")) {
//            doDelete();
//        } else if (c.equals("s")) {
//            saveWorkoutList();
//        } else if (c.equals("l")) {
//            loadWorkoutList();
//        } else {
//            System.out.println("Invalid option, please try again");
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: adds a workout to the workout list
//    private void doAddWorkout() {
//        displayAddDayMenu();
//
//        int d = input.nextInt();
//
//        while (d < 1 || d > 7) {
//            System.out.println("Invalid input, please try again");
//            displayAddDayMenu();
//            d = input.nextInt();
//        }
//
//        System.out.println("Choose an hour for your workout:");
//
//        int t = input.nextInt();
//
//        while (t < 0 || t > 23) {
//            System.out.println("Invalid time, please choose an integer from 0 to 23");
//            t = input.nextInt();
//        }
//
//        Workout w = new Workout(t,d);
//        workoutList.addWorkout(w);
//
//        System.out.println("Your workout has been added");
//    }
//
//    //EFFECTS: shows input options for workout day
//    private void displayAddDayMenu() {
//        System.out.println("Select a day for you workout:");
//        System.out.println("1 - Monday");
//        System.out.println("2 - Tuesday");
//        System.out.println("3 - Wednesday");
//        System.out.println("4 - Thursday");
//        System.out.println("5 - Friday");
//        System.out.println("6 - Saturday");
//        System.out.println("7 - Sunday");
//    }
//
//    //MODIFIES: this
//    //EFFECTS: completes next incomplete workout
//    private void doCompleteWorkout() {
//        if (workoutList.incompleteExists()) {
//            workoutList.completeWorkout();
//            System.out.println("You completed your workout");
//            printWorkout(workoutList.getCompletedWorkouts().get(workoutList.getCompletedWorkouts().size() - 1));
//            System.out.println("Good Job!");
//        } else {
//            System.out.println("No workout to complete");
//        }
//    }
//
//    //EFFECTS: show next incomplete workout
//    private void doViewIncomplete() {
//        Workout incompleteWorkout = workoutList.getNextWorkout();
//        if (incompleteWorkout.getDay() == 0 && incompleteWorkout.getTime() == 0) {
//            System.out.println("No workout planned");
//        } else {
//            printWorkout(workoutList.getNextWorkout());
//        }
//    }
//
//    //EFFECTS: prints information about workout
//    private void printWorkout(Workout w) {
//
//        String time;
//
//        if (w.getTime() == 0) {
//            time = "12:00 AM";
//        } else if (w.getTime() < 12) {
//            time = w.getTime() + ":00 AM";
//        } else if (w.getTime() == 12) {
//            time = "12:00 PM";
//        } else {
//            time = (w.getTime() - 12) + ":00 PM";
//        }
//
//
//        String status;
//
//        if (w.getCompleted()) {
//            status = "Completed";
//        } else {
//            status = "Incomplete";
//        }
//
//        System.out.println("Workout at " + time + " on " + toStringDay(w.getDay()));
//        System.out.println("Status: " + status);
//    }
//
//    //EFFECTS: turns integer representing day into string of day
//    private String toStringDay(int d) {
//        switch (d) {
//            case 1:
//                return "Monday";
//            case 2:
//                return "Tuesday";
//            case 3:
//                return "Wednesday";
//            case 4:
//                return "Thursday";
//            case 5:
//                return "Friday";
//            case 6:
//                return "Saturday";
//            case 7:
//                return "Sunday";
//            default:
//                return "INVALID DAY";
//        }
//
//
//    }
//
//    //EFFECTS: show all completed workouts
//    private void doViewComplete() {
//        List<Workout> completeWorkouts = workoutList.getCompletedWorkouts();
//        if (completeWorkouts.size() == 0) {
//            System.out.println("No workouts completed");
//        } else {
//            for (Workout w : completeWorkouts) {
//                printWorkout(w);
//            }
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: delete next incomplete workout
//    private void doDelete() {
//        if (workoutList.incompleteExists()) {
//            printWorkout(workoutList.getNextWorkout());
//            workoutList.removeNextWorkout();
//            System.out.println("Your workout has been deleted");
//        } else {
//            System.out.println("You have no workouts planned");
//        }
//    }
//
//    // EFFECTS: saves the workout list to file
//    private void saveWorkoutList() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(workoutList);
//            jsonWriter.close();
//            System.out.println("Saved " + workoutList.getName() + " to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads workout list from file
//    private void loadWorkoutList() {
//        try {
//            workoutList = jsonReader.read();
//            System.out.println("Loaded " + workoutList.getName() + " from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//}
