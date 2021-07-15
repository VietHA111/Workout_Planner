package ui;

import model.Workout;
import model.WorkoutList;
import model.exceptions.InvalidWorkoutException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WorkoutPlannerController implements ActionListener, MouseListener {
    private static final String JSON_STORE = "./data/workoutList.json";
    String[] COLUMN_NAMES = {"Time",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"};

    public static int action; // 0 is add workout, 1 is complete workout, 2 is remove workout
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private TimeTableModel timeTableModel;
    private WorkoutPlannerVisualInterface view;
    private WorkoutList model;

    public WorkoutPlannerController(WorkoutPlannerVisualInterface view, WorkoutList model) {
        this.view = view;
        this.model = model;
        timeTableModel = new TimeTableModel(COLUMN_NAMES, 25);
        view.getTimeTable().setModel(timeTableModel);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setupButtons();
        setupTimetable();
        view.getTimeTable().addMouseListener(this);
    }

    //EFFECTS: setup timetable
    private void setupTimetable() {
        timeTableModel.setValueAt("Time", 0, 0);
        timeTableModel.setValueAt("Monday", 0, 1);
        timeTableModel.setValueAt("Tuesday", 0, 2);
        timeTableModel.setValueAt("Wednesday", 0, 3);
        timeTableModel.setValueAt("Thursday", 0, 4);
        timeTableModel.setValueAt("Friday", 0, 5);
        timeTableModel.setValueAt("Saturday", 0, 6);
        timeTableModel.setValueAt("Sunday", 0, 7);
        setTimes();
    }

    //EFFECTS: set time column
    private void setTimes() {
        for (int i = 1; i < 25; i++) {
            timeTableModel.setValueAt((i - 1) + ":00",i,0);
        }
    }

    //EFFECTS: add action commands to buttons
    private void setupButtons() {
        JButton add = view.getAddWorkoutButton();
        JButton complete = view.getCompleteWorkoutButton();
        JButton remove = view.getRemoveWorkoutButton();
        JButton save = view.getSaveButton();
        JButton load = view.getLoadButton();

        add.setActionCommand("add");
        add.addActionListener(this);
        complete.setActionCommand("complete");
        complete.addActionListener(this);
        remove.setActionCommand("remove");
        remove.addActionListener(this);
        save.setActionCommand("save");
        save.addActionListener(this);
        load.setActionCommand("load");
        load.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: save workouts
    private void saveWorkout() {
        try {
            jsonWriter.open();
            jsonWriter.write(model);
            jsonWriter.close();
            System.out.println("Saved " + model.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: load workouts
    private void loadWorkout() {
        reset();

        try {
            model = jsonReader.read();
            System.out.println("Loaded " + model.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

        loadTimeTable();
    }

    //MODIFIES: this
    //EFFECTS: allows user to remove workouts
    private void removeWorkout() {
        action = 1;
    }

    //MODIFIES: this
    private void removeWorkout(int row, int col) {
        StatusColumnCellRenderer sccr = new StatusColumnCellRenderer();
        timeTableModel.setValueAt(null, row, col);
        model.removeWorkout(row - 1, col);
        view.getTimeTable().getColumnModel().getColumn(col).setCellRenderer(sccr);
    }

    //MODIFIES: this
    //EFFECTS: allows user to complete workouts
    private void completeWorkout() {
        action = 2;
    }

    //MODIFIES: this
    //EFFECTS: completes the workout
    //         if InvalidWorkoutException, do not complete any workout
    private void completeWorkout(int row, int col) {
        if (timeTableModel.getValueAt(row, col) == "Incomplete") {
            StatusColumnCellRenderer sccr = new StatusColumnCellRenderer();
            timeTableModel.setValueAt("Completed", row, col);
            try {
                model.completeWorkout(new Workout(row - 1, col));
            } catch (InvalidWorkoutException e) {
                e.printStackTrace();
            }
            view.getTimeTable().getColumnModel().getColumn(col).setCellRenderer(sccr);
        }
    }

    //MODIFIES: this
    //EFFECTS: allows user to add workouts
    private void addWorkout() {
        action = 0;
    }

    //MODIFIES: this
    //EFFECTS: add complete workout to workout list and change color of appropriate cell on Timetable to red
    //         if InvalidWorkoutException, do not add a workout
    private void addCompleteWorkout(int row, int col) {
        StatusColumnCellRenderer sccr = new StatusColumnCellRenderer();
        timeTableModel.setValueAt("Completed", row, col);
        try {
            model.addWorkout(new Workout(row - 1, col, true));
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }
        view.getTimeTable().getColumnModel().getColumn(col).setCellRenderer(sccr);
    }

    //MODIFIES: this
    //EFFECTS: add workout to workout list and change color of appropriate cell on Timetable to red
    //         if InvalidWorkoutException, do not add a workout
    private void addIncompleteWorkout(int row, int col) {
        StatusColumnCellRenderer sccr = new StatusColumnCellRenderer();
        timeTableModel.setValueAt("Incomplete", row, col);
        try {
            model.addWorkout(new Workout(row - 1, col, false));
        } catch (InvalidWorkoutException e) {
            e.printStackTrace();
        }
        view.getTimeTable().getColumnModel().getColumn(col).setCellRenderer(sccr);
    }

    //MODIFIES: this
    //EFFECTS: load workouts
    private void loadTimeTable() {

        for (Workout w: model.getWorkouts()) {
            if (w.getCompleted()) {
                addCompleteWorkout(w.getTime() + 1, w.getDay());
            } else {
                addIncompleteWorkout(w.getTime() + 1, w.getDay());
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: removes all workouts from timeTable
    private void reset() {
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 25; j++) {
                removeWorkout(j, i);
            }
        }
    }

    //code from: http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    //EFFECTS: plays specified audio file
    private void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Failed to play sound");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playSound("data//zapsplat_multimedia_button_click_fast_plastic_49161.wav");
        switch (e.getActionCommand()) {
            case "add":
                addWorkout();
                break;
            case "complete":
                completeWorkout();
                break;
            case "remove":
                removeWorkout();
                break;
            case "save":
                saveWorkout();
                break;
            case "load":
                loadWorkout();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int column = table.columnAtPoint(e.getPoint());
        int row = table.rowAtPoint(e.getPoint());
        if (!(row == 0) && !(column == 0)) {
            if (action == 0) {
                addIncompleteWorkout(row, column);
            } else if (action == 1) {
                removeWorkout(row, column);
            } else if (action == 2) {
                completeWorkout(row, column);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
