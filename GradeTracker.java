import java.util.Scanner;

public class GradeTracker {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        ClassRoster classRoster = new ClassRoster();
        String fileName = "class_data.txt"; // Default file name for saving data

        // Load data from file if it exists
        while (true) {
            printMenu(); // Print the menu
            System.out.print("Enter your choice: ");
            int choice = getUserChoice(userInput, classRoster); // Get user choice

            switch (choice) {
                case 1: // For Add student
                    addStudent(userInput, classRoster);
                    break;
                case 2: // For Remove student
                    removeStudent(userInput, classRoster);
                    break;
                case 3: // For Input grades
                    classRoster.inputGrades(userInput);
                    break;
                case 4: // For Display class average
                    classRoster.displayClassAverage();
                    break;
                case 5: // For Display student grades
                    classRoster.displayStudentGrades();
                    break;
                case 6: // For Display student details
                    classRoster.displayStudentDetails(userInput);
                    break;
                case 7: // For Modify grade
                    classRoster.modifyGrade(userInput);
                    break;
                case 8: // For Save to file
                    classRoster.saveToFile(fileName);
                    break;
                case 9: // For Load from file
                    classRoster.loadFromFile(fileName);
                    break;
                case 10: // For Exit
                    System.out.println("Exiting program.");
                    System.exit(0);
                default: // Invalid choice
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    // Helper method to print the menu
    private static void printMenu() {
        System.out.println("""
                1. Add Student
                2. Remove Student
                3. Input Grades
                4. Display Class Average
                5. Display Student Grades
                6. Display Student Details
                7. Modify Grade
                8. Save to File
                9. Load from File
                10. Exit""");
    }

    // getUserChoice method to get user choice
    private static int getUserChoice(Scanner userInput, ClassRoster classRoster) {
        try {
            return userInput.nextInt(); // Get user input as an integer
        } catch (Exception e) { // Invalid input
            classRoster.logError("Invalid input. Please enter a number.", e);
            userInput.nextLine(); // Consume the invalid input
            return getUserChoice(userInput, classRoster); // Recursively call the method
        }
    }

    // To add a student to the class
    private static void addStudent(Scanner userInput, ClassRoster classRoster) {
        System.out.print("Enter student name: ");
        userInput.nextLine(); // Consume the newline character

        String studentName = userInput.nextLine();
        Student newStudent = new Student(studentName);

        classRoster.addStudent(newStudent); // Add the new student to the class
    }

    // Helper method to remove a student from the class
    private static void removeStudent(Scanner userInput, ClassRoster classRoster) {
        System.out.print("Enter student name to remove: ");
        userInput.nextLine(); // Consume the newline character
        String studentToRemove = userInput.nextLine();

        classRoster.removeStudent(studentToRemove); // Remove the student from the class
    }
}