import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class ClassRoster {
    private ArrayList<Student> students; // List of students in the class

    public ClassRoster() {
        this.students = new ArrayList<>(); // Initialize the list of students
    }

    // Helper method to add a student
    public void addStudent(Student student) {
        students.add(student); // Add the student to the list
        System.out.println("Student " + student.getName() + " added.");
    }

    // Helper method to remove a student by name
    public void removeStudent(String studentName) {
        Iterator<Student> iterator = students.iterator(); // Create an iterator

        // Remove the student from the list
        while (iterator.hasNext()) {
            Student student = iterator.next(); // Get the next student

            // Check if the student name matches
            if (student.getName().equals(studentName)) {
                iterator.remove(); // Remove the student

                // Print a success message
                System.out.println("Student " + studentName + " removed.");
                return; // Exit the method
            }
        }
        System.out.println("Student " + studentName + " not found.");
    }

    // Helper method to input grades for a student by name
    public void inputGrades(Scanner scanner) {
        System.out.print("Enter student name: ");
        scanner.nextLine(); // Consume the newline character
        String studentName = scanner.nextLine();

        // Find the student by name
        for (Student student : students) {

            // Check if the student name matches
            if (student.getName().equals(studentName)) {
                try {
                    System.out.print("Enter grade for " + studentName + ": ");
                    int grade = scanner.nextInt();

                    student.addGrade(grade); // Add the grade to the student

                    System.out.println("Grade added for " + studentName);
                    return;
                } catch (java.util.InputMismatchException e) { // Invalid input
                    scanner.nextLine(); // Consume the invalid input
                    logError("Invalid input. Please enter a valid integer for the grade.", e);
                    return;
                }
            }
        }

        System.out.println("Student " + studentName + " not found."); // Student isn't found
    }

    // Helper method to display the class average for all students
    public void displayClassAverage() {

        // Check if there are any students in the class
        if (students.isEmpty()) {
            System.out.println("No students in the class.");
            return;
        }

        double classSum = 0; // Sum of all student grades

        // Add up all the student grades
        for (Student student : students) {

            // Add the student's average grade
            classSum += student.getAverageGrade();
        }

        double classAverage = classSum / students.size();
        System.out.println("Class Average: " + classAverage);
    }

    // Helper method to display the student grades
    public void displayStudentGrades() {

        // Check if there are any students in the class
        if (students.isEmpty()) {
            System.out.println("No students in the class.");
            return;
        }

        System.out.println("Student Grades:");

        for (Student student : students) {
            System.out.println(student.getName() + ": " + student.getAverageGrade());
        }
    }

    // Helper method to display the student details by name
    public void displayStudentDetails(Scanner scanner) {
        System.out.print("Enter student name: ");
        scanner.nextLine(); // Consume the newline character
        String studentName = scanner.nextLine();

        for (Student student : students) {

            // Check if the student name matches
            if (student.getName().equals(studentName)) {
                System.out.println("Student Details for " + studentName + ":");
                System.out.println("Average Grade: " + student.getAverageGrade());
                System.out.println("Individual Grades: " + student.getGrades());
                return;
            }
        }

        System.out.println("Student " + studentName + " not found.");
    }

    // Helper method to modify a grade for a student
    public void modifyGrade(Scanner scanner) {
        System.out.print("Enter student name: ");
        scanner.nextLine(); // Consume the newline character
        String studentName = scanner.nextLine();

        for (Student student : students) {

            // Check if the student name matches
            if (student.getName().equals(studentName)) {
                System.out.print("Enter the index of the grade to modify (starting from 1): ");
                int index = scanner.nextInt();

                // Check if the index is valid and within the range of the student's grades
                if (index >= 1 && index <= student.getGrades().size()) {
                    System.out.print("Enter the new grade: ");
                    int newGrade = scanner.nextInt(); // Get the new grade

                    // Modify the grade at the specified index to the new grade
                    student.getGrades().set(index - 1, newGrade);

                    System.out.println("Grade modified for " + studentName);
                    return;
                } else {
                    System.out.println("Invalid index.");
                }
            }
        }

        System.out.println("Student " + studentName + " not found.");
    }

    // Helper method to save data to a file
    public void saveToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students); // Write the list of students to the file
            System.out.println("Data saved to file: " + fileName);
        } catch (IOException e) { // Error saving data
            logError("Error saving data to file: " + fileName, e);
        }
    }

    @SuppressWarnings("unchecked") // Helper method to load data from a file
    public void loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            students = (ArrayList<Student>) ois.readObject(); // Read the list of students from the file
            System.out.println("Data loaded from file: " + fileName);
        } catch (IOException | ClassNotFoundException e) { // Error loading data
            logError("Error loading data from file: " + fileName, e);
        }
    }

    // Helper method for logging errors to a file
    void logError(String message, Exception e) {

        // Create a logger and add a file handler to it to log errors
        Logger logger = Logger.getLogger(ClassRoster.class.getName());

        try {

            // Create a file handler to log errors to a file named "error.log" in the current directory
            FileHandler fileHandler = new FileHandler("error.log", true);
            logger.addHandler(fileHandler); // Add the file handler to the logger
            SimpleFormatter formatter = new SimpleFormatter(); // Set the formatter
            fileHandler.setFormatter(formatter); // Set the formatter for the file handler

            logger.severe(message); // Log the error message to the file handler

            if (e != null) { // If an exception was thrown

                // Log the exception message to the file handler and the console for debugging purposes
                String exceptionMessage = (e.getMessage() != null && !e.getMessage().isEmpty())
                        ? e.getMessage() // Get the exception message if available
                        : "No additional exception information available.";
                // Otherwise, use a default message

                logger.severe(exceptionMessage); // Log the exception message to the file handler
                logger.log(Level.SEVERE, "Exception occurred", e); // Log the exception to the console
            } else { // If no exception was thrown
                logger.severe("Exception object is null.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            // Error logging errors to a file named "error.log" in the current directory
        }
    }
}