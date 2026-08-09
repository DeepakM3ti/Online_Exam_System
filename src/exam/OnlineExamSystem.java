package exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineExamSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        CreateTables.createTables();

        while (true) {

            System.out.println("\n=================================");
            System.out.println("     ONLINE EXAMINATION SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Student");
            System.out.println("2. Teacher");
            System.out.println("3. Exit");

            int choice = getIntInput("Enter choice : ");

            switch (choice) {

                case 1:
                    studentMenu();
                    break;

                case 2:
                    teacherLogin();
                    break;

                case 3:
                    System.out.println("Thank you.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static int getIntInput(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static boolean isValidEmail(String email) {

        return email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    public static boolean isValidPassword(String password) {

        return password.length() >= 4;
    }

    public static String getAnswer() {

        while (true) {

            String answer = sc.nextLine().trim().toUpperCase();

            if (answer.equals("A") ||
                answer.equals("B") ||
                answer.equals("C") ||
                answer.equals("D")) {

                return answer;
            }

            System.out.print(
                    "Invalid option. Enter A, B, C or D : "
            );
        }
    }

    public static void studentMenu() {

        while (true) {

            System.out.println("\n========== STUDENT ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");

            int choice = getIntInput("Enter choice : ");

            switch (choice) {

                case 1:
                    registerStudent();
                    break;

                case 2:

                    Student student = studentLogin();

                    if (student != null) {

                        loggedStudentMenu(student);
                    }

                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void registerStudent() {

        System.out.println(
                "\n========== STUDENT REGISTRATION =========="
        );

        System.out.print("Enter name : ");
        String name = sc.nextLine().trim();

        while (name.isEmpty()) {

            System.out.print(
                    "Name cannot be empty. Enter name : "
            );

            name = sc.nextLine().trim();
        }

        String email;

        while (true) {

            System.out.print("Enter email : ");
            email = sc.nextLine().trim();

            if (isValidEmail(email)) {

                break;
            }

            System.out.println("Invalid email format.");
        }

        String password;

        while (true) {

            System.out.print("Enter password : ");
            password = sc.nextLine();

            if (isValidPassword(password)) {

                break;
            }

            System.out.println(
                    "Password must contain at least 4 characters."
            );
        }

        String sql =
                "INSERT INTO students(name,email,password) " +
                "VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            System.out.println(
                    "Student registered successfully."
            );

        } catch (Exception e) {

            if (e.getMessage() != null &&
                e.getMessage().toLowerCase().contains("duplicate")) {

                System.out.println(
                        "Email already registered."
                );

            } else {

                System.out.println(
                        "Registration failed."
                );
            }
        }
    }

    public static Student studentLogin() {

        System.out.println("\n========== STUDENT LOGIN ==========");

        System.out.print("Enter email : ");
        String email = sc.nextLine().trim();

        System.out.print("Enter password : ");
        String password = sc.nextLine();

        String sql =
                "SELECT * FROM students " +
                "WHERE email=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Student student = new Student();

                student.setStudentId(
                        rs.getInt("student_id")
                );

                student.setName(
                        rs.getString("name")
                );

                student.setEmail(
                        rs.getString("email")
                );

                student.setPassword(
                        rs.getString("password")
                );

                System.out.println(
                        "\nLogin successful."
                );

                System.out.println(
                        "Welcome " + student.getName()
                );

                return student;
            }

            System.out.println(
                    "Invalid email or password."
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public static void loggedStudentMenu(Student student) {

        while (true) {

            System.out.println("\n========== STUDENT MENU ==========");
            System.out.println("1. View Exams");
            System.out.println("2. Take Exam");
            System.out.println("3. View My Results");
            System.out.println("4. Logout");

            int choice = getIntInput("Enter choice : ");

            switch (choice) {

                case 1:
                    viewExams();
                    break;

                case 2:

                    viewExams();

                    int examId =
                            getIntInput("Enter Exam ID : ");

                    takeExam(
                            student.getStudentId(),
                            examId
                    );

                    break;

                case 3:

                    viewStudentResults(
                            student.getStudentId()
                    );

                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void teacherLogin() {

        System.out.println("\n========== TEACHER LOGIN ==========");

        System.out.print("Username : ");
        String username = sc.nextLine();

        System.out.print("Password : ");
        String password = sc.nextLine();

        Teacher teacher =
                new Teacher(username, password);

        if (teacher.login()) {

            System.out.println(
                    "Teacher login successful."
            );

            teacherMenu();

        } else {

            System.out.println(
                    "Invalid teacher credentials."
            );
        }
    }

    public static void teacherMenu() {

        while (true) {

            System.out.println("\n========== TEACHER MENU ==========");
            System.out.println("1. Create Exam");
            System.out.println("2. View Exams");
            System.out.println("3. Update Exam");
            System.out.println("4. Delete Exam");
            System.out.println("5. Add Question");
            System.out.println("6. View Questions");
            System.out.println("7. Update Question");
            System.out.println("8. Delete Question");
            System.out.println("9. View All Results");
            System.out.println("10. Result Statistics");
            System.out.println("11. Logout");

            int choice = getIntInput("Enter choice : ");

            switch (choice) {

                case 1:
                    createExam();
                    break;

                case 2:
                    viewExams();
                    break;

                case 3:
                    updateExam();
                    break;

                case 4:
                    deleteExam();
                    break;

                case 5:
                    addQuestion();
                    break;

                case 6:
                    viewQuestions();
                    break;

                case 7:
                    updateQuestion();
                    break;

                case 8:
                    deleteQuestion();
                    break;

                case 9:
                    viewAllResults();
                    break;

                case 10:
                    resultStatistics();
                    break;

                case 11:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void createExam() {

        System.out.println("\n========== CREATE EXAM ==========");

        System.out.print("Enter exam title : ");
        String title = sc.nextLine().trim();

        while (title.isEmpty()) {

            System.out.print(
                    "Exam title cannot be empty : "
            );

            title = sc.nextLine().trim();
        }

        String sql =
                "INSERT INTO exams(title,teacher_name) " +
                "VALUES(?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, "teacher");

            ps.executeUpdate();

            System.out.println(
                    "Exam created successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void viewExams() {

        System.out.println("\n========== EXAM LIST ==========");

        String sql =
                "SELECT * FROM exams";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Exam ID      : " +
                        rs.getInt("exam_id")
                );

                System.out.println(
                        "Exam Title   : " +
                        rs.getString("title")
                );

                System.out.println(
                        "Teacher      : " +
                        rs.getString("teacher_name")
                );

                System.out.println(
                        "--------------------------------"
                );
            }

            if (!found) {

                System.out.println(
                        "No exams available."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void updateExam() {

        viewExams();

        int examId =
                getIntInput("Enter Exam ID : ");

        System.out.print(
                "Enter new exam title : "
        );

        String title =
                sc.nextLine().trim();

        String sql =
                "UPDATE exams SET title=? " +
                "WHERE exam_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setInt(2, examId);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Exam updated successfully."
                );

            } else {

                System.out.println(
                        "Exam not found."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void deleteExam() {

        viewExams();

        int examId =
                getIntInput("Enter Exam ID : ");

        String sql =
                "DELETE FROM exams WHERE exam_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Exam deleted successfully."
                );

            } else {

                System.out.println(
                        "Exam not found."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void addQuestion() {

        System.out.println(
                "\n========== ADD QUESTION =========="
        );

        viewExams();

        int examId =
                getIntInput("Enter Exam ID : ");

        System.out.print(
                "Enter question : "
        );

        String question =
                sc.nextLine().trim();

        System.out.print(
                "Enter option A : "
        );

        String a =
                sc.nextLine().trim();

        System.out.print(
                "Enter option B : "
        );

        String b =
                sc.nextLine().trim();

        System.out.print(
                "Enter option C : "
        );

        String c =
                sc.nextLine().trim();

        System.out.print(
                "Enter option D : "
        );

        String d =
                sc.nextLine().trim();

        String correct;

        while (true) {

            System.out.print(
                    "Enter correct option (A/B/C/D) : "
            );

            correct =
                    sc.nextLine().trim().toUpperCase();

            if (correct.equals("A") ||
                correct.equals("B") ||
                correct.equals("C") ||
                correct.equals("D")) {

                break;
            }

            System.out.println(
                    "Enter only A, B, C or D."
            );
        }

        String sql =
                "INSERT INTO questions " +
                "(exam_id,question_text,option_a," +
                "option_b,option_c,option_d,correct_option) " +
                "VALUES(?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);
            ps.setString(2, question);
            ps.setString(3, a);
            ps.setString(4, b);
            ps.setString(5, c);
            ps.setString(6, d);
            ps.setString(7, correct);

            ps.executeUpdate();

            System.out.println(
                    "Question added successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void viewQuestions() {

        System.out.println(
                "\n========== QUESTIONS =========="
        );

        int examId =
                getIntInput("Enter Exam ID : ");

        String sql =
                "SELECT question_id, question_text, " +
                "option_a, option_b, option_c, option_d, " +
                "correct_option FROM questions " +
                "WHERE exam_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);

            ResultSet rs =
                    ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "\nQuestion ID : " +
                        rs.getInt("question_id")
                );

                System.out.println(
                        "Question : " +
                        rs.getString("question_text")
                );

                System.out.println(
                        "A. " +
                        rs.getString("option_a")
                );

                System.out.println(
                        "B. " +
                        rs.getString("option_b")
                );

                System.out.println(
                        "C. " +
                        rs.getString("option_c")
                );

                System.out.println(
                        "D. " +
                        rs.getString("option_d")
                );

                System.out.println(
                        "Correct : " +
                        rs.getString("correct_option")
                );

                System.out.println(
                        "--------------------------------"
                );
            }

            if (!found) {

                System.out.println(
                        "No questions found."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void updateQuestion() {

        System.out.println(
                "\n========== UPDATE QUESTION =========="
        );

        viewQuestions();

        int questionId =
                getIntInput("Enter Question ID : ");

        System.out.print(
                "Enter question : "
        );

        String question =
                sc.nextLine();

        System.out.print(
                "Enter option A : "
        );

        String a =
                sc.nextLine();

        System.out.print(
                "Enter option B : "
        );

        String b =
                sc.nextLine();

        System.out.print(
                "Enter option C : "
        );

        String c =
                sc.nextLine();

        System.out.print(
                "Enter option D : "
        );

        String d =
                sc.nextLine();

        String correct;

        while (true) {

            System.out.print(
                    "Enter correct option (A/B/C/D) : "
            );

            correct =
                    sc.nextLine().trim().toUpperCase();

            if (correct.equals("A") ||
                correct.equals("B") ||
                correct.equals("C") ||
                correct.equals("D")) {

                break;
            }

            System.out.println(
                    "Invalid option."
            );
        }

        String sql =
                "UPDATE questions SET " +
                "question_text=?," +
                "option_a=?," +
                "option_b=?," +
                "option_c=?," +
                "option_d=?," +
                "correct_option=? " +
                "WHERE question_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, question);
            ps.setString(2, a);
            ps.setString(3, b);
            ps.setString(4, c);
            ps.setString(5, d);
            ps.setString(6, correct);
            ps.setInt(7, questionId);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Question updated successfully."
                );

            } else {

                System.out.println(
                        "Question not found."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void deleteQuestion() {

        System.out.println(
                "\n========== DELETE QUESTION =========="
        );

        viewQuestions();

        int questionId =
                getIntInput("Enter Question ID : ");

        String sql =
                "DELETE FROM questions " +
                "WHERE question_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, questionId);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Question deleted successfully."
                );

            } else {

                System.out.println(
                        "Question not found."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void takeExam(
            int studentId,
            int examId) {

        System.out.println(
                "\n========== START EXAM =========="
        );

        String sql =
                "SELECT * FROM questions " +
                "WHERE exam_id=? " +
                "ORDER BY question_id";

        List<Question> questions =
                new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, examId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Question q =
                        new Question();

                q.setQuestionId(
                        rs.getInt("question_id")
                );

                q.setExamId(
                        rs.getInt("exam_id")
                );

                q.setQuestionText(
                        rs.getString("question_text")
                );

                q.setOptionA(
                        rs.getString("option_a")
                );

                q.setOptionB(
                        rs.getString("option_b")
                );

                q.setOptionC(
                        rs.getString("option_c")
                );

                q.setOptionD(
                        rs.getString("option_d")
                );

                q.setCorrectOption(
                        rs.getString("correct_option")
                );

                questions.add(q);
            }

        } catch (Exception e) {

            e.printStackTrace();
            return;
        }

        if (questions.isEmpty()) {

            System.out.println(
                    "No questions available."
            );

            return;
        }

        int score = 0;

        for (int i = 0;
             i < questions.size();
             i++) {

            Question q =
                    questions.get(i);

            System.out.println(
                    "\nQuestion " + (i + 1)
            );

            System.out.println(
                    q.getQuestionText()
            );

            System.out.println(
                    "A. " + q.getOptionA()
            );

            System.out.println(
                    "B. " + q.getOptionB()
            );

            System.out.println(
                    "C. " + q.getOptionC()
            );

            System.out.println(
                    "D. " + q.getOptionD()
            );

            System.out.print(
                    "Your answer : "
            );

            String answer =
                    getAnswer();

            if (answer.equals(
                    q.getCorrectOption())) {

                score++;
            }
        }

        int total =
                questions.size();

        String grade =
                calculateGrade(
                        score,
                        total
                );

        System.out.println(
                "\n========== RESULT =========="
        );

        System.out.println(
                "Score : " +
                score +
                "/" +
                total
        );

        System.out.println(
                "Grade : " +
                grade
        );

        saveResult(
                studentId,
                examId,
                score,
                total,
                grade
        );
    }

    public static String calculateGrade(
            int score,
            int total) {

        double percentage =
                ((double) score / total) * 100;

        if (percentage >= 90) {

            return "A+";

        } else if (percentage >= 80) {

            return "A";

        } else if (percentage >= 70) {

            return "B";

        } else if (percentage >= 60) {

            return "C";

        } else if (percentage >= 50) {

            return "D";

        } else {

            return "F";
        }
    }

    public static void saveResult(
            int studentId,
            int examId,
            int score,
            int total,
            String grade) {

        String sql =
                "INSERT INTO results " +
                "(student_id,exam_id,score," +
                "total_questions,grade) " +
                "VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            ps.setInt(3, score);
            ps.setInt(4, total);
            ps.setString(5, grade);

            ps.executeUpdate();

            System.out.println(
                    "Result saved successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void viewStudentResults(
            int studentId) {

        System.out.println(
                "\n========== MY RESULTS =========="
        );

        String sql =
                "SELECT s.name, e.title, r.score, " +
                "r.total_questions, r.grade " +
                "FROM results r " +
                "JOIN students s " +
                "ON r.student_id=s.student_id " +
                "JOIN exams e " +
                "ON r.exam_id=e.exam_id " +
                "WHERE r.student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Student : " +
                        rs.getString("name")
                );

                System.out.println(
                        "Exam : " +
                        rs.getString("title")
                );

                System.out.println(
                        "Score : " +
                        rs.getInt("score") +
                        "/" +
                        rs.getInt("total_questions")
                );

                System.out.println(
                        "Grade : " +
                        rs.getString("grade")
                );

                System.out.println(
                        "--------------------------------"
                );
            }

            if (!found) {

                System.out.println(
                        "No results found."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void viewAllResults() {

        System.out.println(
                "\n========== ALL RESULTS =========="
        );

        String sql =
                "SELECT r.result_id, s.name, e.title, " +
                "r.score, r.total_questions, r.grade " +
                "FROM results r " +
                "JOIN students s " +
                "ON r.student_id=s.student_id " +
                "JOIN exams e " +
                "ON r.exam_id=e.exam_id " +
                "ORDER BY r.score DESC";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "\nResult ID : " +
                        rs.getInt("result_id")
                );

                System.out.println(
                        "Student : " +
                        rs.getString("name")
                );

                System.out.println(
                        "Exam : " +
                        rs.getString("title")
                );

                System.out.println(
                        "Score : " +
                        rs.getInt("score") +
                        "/" +
                        rs.getInt("total_questions")
                );

                System.out.println(
                        "Grade : " +
                        rs.getString("grade")
                );

                System.out.println(
                        "--------------------------------"
                );
            }

            if (!found) {

                System.out.println(
                        "No results available."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void resultStatistics() {

        System.out.println(
                "\n========== RESULT STATISTICS =========="
        );

        String sql =
                "SELECT COUNT(*) AS total_results, " +
                "AVG(score) AS average_score, " +
                "MAX(score) AS highest_score, " +
                "MIN(score) AS lowest_score " +
                "FROM results";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {

                System.out.println(
                        "Total Results : " +
                        rs.getInt("total_results")
                );

                System.out.printf(
                        "Average Score : %.2f%n",
                        rs.getDouble("average_score")
                );

                System.out.println(
                        "Highest Score : " +
                        rs.getInt("highest_score")
                );

                System.out.println(
                        "Lowest Score : " +
                        rs.getInt("lowest_score")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}