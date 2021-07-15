package ui;

import javax.swing.*;
import java.awt.*;


// Code based on BoxLayoutDemo: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/BoxLayoutDemoProject/src/layout/BoxLayoutDemo.java
//              and ButtonDemo: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ButtonDemoProject/src/components/ButtonDemo.java
// Workout Planner visual interface
public class WorkoutPlannerVisualInterface {
    private static final String JSON_STORE = "./data/workoutList.json";


    private JButton addWorkoutButton;
    private JButton completeWorkoutButton;
    private JButton removeWorkoutButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTable timeTable;


    public WorkoutPlannerVisualInterface() {
        setup();
    }

    public JTable getTimeTable() {
        return timeTable;
    }

    public JButton getAddWorkoutButton() {
        return addWorkoutButton;
    }

    public JButton getCompleteWorkoutButton() {
        return completeWorkoutButton;
    }

    public JButton getRemoveWorkoutButton() {
        return removeWorkoutButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }


    //EFFECTS: setup GUI
    private void setup() {
        JFrame f = new JFrame("Workout Planner");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToFrame(f);
        f.pack();
        f.setVisible(true);
    }


    //EFFECTS: add components to frame
    private void addComponentsToFrame(JFrame f) {
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
        addTimetable(f);
        addWorkoutButton = addAButton("Add workout",f);
        completeWorkoutButton = addAButton("Complete workout", f);
        removeWorkoutButton = addAButton("Remove workout", f);
        saveButton = addAButton("Save", f);
        loadButton = addAButton("Load", f);
    }

    //EFFECTS: adds timetable to frame
    private void addTimetable(JFrame f) {
        timeTable = new JTable();
        timeTable.setDefaultEditor(Object.class, null); //https://stackoverflow.com/questions/9919230/disable-user-edit-in-jtable
        timeTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        f.add(timeTable);
    }

    //EFFECTS: add a button to frame
    private JButton addAButton(String text, JFrame f) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        f.add(button);
        return button;
    }



}
