package exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateTables {

    public static void createTables() {

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

            String students =
                    "CREATE TABLE IF NOT EXISTS students (" +
                    "student_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100) NOT NULL," +
                    "email VARCHAR(100) UNIQUE NOT NULL," +
                    "password VARCHAR(100) NOT NULL" +
                    ")";

            String exams =
                    "CREATE TABLE IF NOT EXISTS exams (" +
                    "exam_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "title VARCHAR(150) NOT NULL," +
                    "teacher_name VARCHAR(100) NOT NULL" +
                    ")";

            String questions =
                    "CREATE TABLE IF NOT EXISTS questions (" +
                    "question_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "exam_id INT NOT NULL," +
                    "question_text VARCHAR(500) NOT NULL," +
                    "option_a VARCHAR(200) NOT NULL," +
                    "option_b VARCHAR(200) NOT NULL," +
                    "option_c VARCHAR(200) NOT NULL," +
                    "option_d VARCHAR(200) NOT NULL," +
                    "correct_option CHAR(1) NOT NULL," +
                    "FOREIGN KEY (exam_id) REFERENCES exams(exam_id) " +
                    "ON DELETE CASCADE" +
                    ")";

            String results =
                    "CREATE TABLE IF NOT EXISTS results (" +
                    "result_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "student_id INT NOT NULL," +
                    "exam_id INT NOT NULL," +
                    "score INT NOT NULL," +
                    "total_questions INT NOT NULL," +
                    "grade VARCHAR(5) NOT NULL," +
                    "FOREIGN KEY (student_id) REFERENCES students(student_id) " +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (exam_id) REFERENCES exams(exam_id) " +
                    "ON DELETE CASCADE" +
                    ")";

            st.executeUpdate(students);
            st.executeUpdate(exams);
            st.executeUpdate(questions);
            st.executeUpdate(results);

            insertExamSets(con);

            System.out.println("Tables created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertExamSets(Connection con) {

        String[][] examTitles = {
            {"Java Basics - Set 1", "teacher"},
            {"OOP Concepts - Set 2", "teacher"},
            {"JDBC and SQL - Set 3", "teacher"},
            {"Advanced Java - Set 4", "teacher"},
            {"Java Mixed - Set 5", "teacher"}
        };

        String[][][] questionSets = {

            {
                {
                    "Which keyword is used to create a class?",
                    "class", "new", "object", "create", "A"
                },
                {
                    "Which method is the starting point of a Java program?",
                    "start()", "main()", "run()", "begin()", "B"
                },
                {
                    "Which data type stores true or false?",
                    "int", "boolean", "char", "float", "B"
                },
                {
                    "Which symbol is used to end a Java statement?",
                    ".", ":", ";", ",", "C"
                },
                {
                    "Which keyword creates an object?",
                    "new", "create", "object", "make", "A"
                },
                {
                    "Which type stores a single character?",
                    "String", "char", "Character", "text", "B"
                },
                {
                    "Which type stores decimal numbers?",
                    "int", "boolean", "double", "char", "C"
                },
                {
                    "Which operator is used for addition?",
                    "+", "-", "*", "/", "A"
                },
                {
                    "Which operator checks equality?",
                    "=", "==", "!=", "=>", "B"
                },
                {
                    "Which keyword is used to return a value?",
                    "send", "return", "value", "get", "B"
                },
                {
                    "Which loop executes at least once?",
                    "for", "while", "do-while", "foreach", "C"
                },
                {
                    "Which keyword is used for a constant?",
                    "constant", "final", "static", "fixed", "B"
                },
                {
                    "Which class is used to read keyboard input?",
                    "Scanner", "Reader", "Input", "Keyboard", "A"
                },
                {
                    "Which package contains Scanner?",
                    "java.io", "java.util", "java.sql", "java.lang", "B"
                },
                {
                    "Which method prints output?",
                    "System.out.println()", "System.print()", "Console.write()", "print.out()", "A"
                },
                {
                    "Which keyword is used for inheritance?",
                    "inherit", "extends", "implements", "super", "B"
                },
                {
                    "Which keyword refers to the current object?",
                    "self", "current", "this", "object", "C"
                },
                {
                    "Which keyword refers to the parent class?",
                    "parent", "base", "super", "this", "C"
                },
                {
                    "Which collection stores elements in order?",
                    "Set", "List", "Map", "Tree", "B"
                },
                {
                    "Which collection does not allow duplicates?",
                    "List", "ArrayList", "Set", "Vector", "C"
                }
            },

            {
                {
                    "What is encapsulation?",
                    "Data hiding", "Inheritance", "Compilation", "Looping", "A"
                },
                {
                    "What is inheritance?",
                    "Creating objects", "Acquiring properties of another class", "Deleting objects", "Hiding data", "B"
                },
                {
                    "Which keyword is used to inherit a class?",
                    "implements", "extends", "inherits", "using", "B"
                },
                {
                    "Which concept allows multiple forms?",
                    "Encapsulation", "Inheritance", "Polymorphism", "Abstraction", "C"
                },
                {
                    "Method overloading means?",
                    "Same method with different parameters", "Same class name", "Different classes", "Deleting methods", "A"
                },
                {
                    "Method overriding occurs in?",
                    "Inheritance", "Variables", "Loops", "Packages", "A"
                },
                {
                    "Which keyword prevents inheritance?",
                    "static", "final", "private", "const", "B"
                },
                {
                    "Which access modifier provides the widest access?",
                    "private", "protected", "public", "default", "C"
                },
                {
                    "Which modifier allows access only inside the class?",
                    "public", "private", "protected", "default", "B"
                },
                {
                    "What is an interface?",
                    "Blueprint containing abstract behavior", "Database", "Object", "Variable", "A"
                },
                {
                    "Which keyword implements an interface?",
                    "extends", "implements", "interface", "using", "B"
                },
                {
                    "Can Java support multiple inheritance through classes?",
                    "Yes", "No", "Only interfaces", "Only abstract classes", "B"
                },
                {
                    "Which keyword is used to create an abstract class?",
                    "abstract", "virtual", "interface", "base", "A"
                },
                {
                    "Can an abstract class have a constructor?",
                    "Yes", "No", "Only static", "Only private", "A"
                },
                {
                    "Which method cannot be overridden?",
                    "public", "static", "final", "protected", "C"
                },
                {
                    "What is a constructor?",
                    "Special method used to initialize objects", "Normal method", "Variable", "Package", "A"
                },
                {
                    "Constructor name must match?",
                    "Package", "Class", "Object", "Method", "B"
                },
                {
                    "Which keyword calls parent constructor?",
                    "this", "super", "parent", "base", "B"
                },
                {
                    "Which concept hides implementation details?",
                    "Abstraction", "Inheritance", "Polymorphism", "Compilation", "A"
                },
                {
                    "Which is an example of polymorphism?",
                    "Method overriding", "Variable declaration", "Loop", "Import", "A"
                }
            },

            {
                {
                    "What does JDBC stand for?",
                    "Java Database Connectivity", "Java Data Connection", "Java Database Control", "Java Data Control", "A"
                },
                {
                    "Which package contains JDBC classes?",
                    "java.io", "java.sql", "java.util", "java.net", "B"
                },
                {
                    "Which object connects Java to a database?",
                    "Connection", "Statement", "ResultSet", "Driver", "A"
                },
                {
                    "Which method executes SELECT queries?",
                    "executeUpdate()", "executeQuery()", "executeSelect()", "runQuery()", "B"
                },
                {
                    "Which method executes INSERT, UPDATE and DELETE?",
                    "executeQuery()", "executeUpdate()", "executeSelect()", "executeInsert()", "B"
                },
                {
                    "Which interface stores query results?",
                    "Connection", "Statement", "ResultSet", "PreparedStatement", "C"
                },
                {
                    "Which object is safer for parameters?",
                    "Statement", "PreparedStatement", "ResultSet", "Driver", "B"
                },
                {
                    "Which SQL command retrieves data?",
                    "INSERT", "UPDATE", "SELECT", "DELETE", "C"
                },
                {
                    "Which SQL command adds data?",
                    "INSERT", "SELECT", "UPDATE", "CREATE", "A"
                },
                {
                    "Which SQL command modifies data?",
                    "UPDATE", "SELECT", "INSERT", "DROP", "A"
                },
                {
                    "Which SQL command removes data?",
                    "DELETE", "REMOVE", "CLEAR", "DROP", "A"
                },
                {
                    "Which SQL command creates a table?",
                    "CREATE", "MAKE", "NEW", "BUILD", "A"
                },
                {
                    "Which SQL function counts rows?",
                    "SUM()", "COUNT()", "TOTAL()", "ROWS()", "B"
                },
                {
                    "Which SQL function calculates average?",
                    "AVG()", "AVERAGE()", "MEAN()", "TOTAL()", "A"
                },
                {
                    "Which SQL function returns highest value?",
                    "HIGH()", "MAX()", "TOP()", "UP()", "B"
                },
                {
                    "Which SQL function returns lowest value?",
                    "LOW()", "MIN()", "BOTTOM()", "LOWEST()", "B"
                },
                {
                    "Which SQL keyword combines tables?",
                    "JOIN", "COMBINE", "MERGE", "CONNECT", "A"
                },
                {
                    "Which JOIN returns matching rows?",
                    "INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL JOIN", "A"
                },
                {
                    "Which key uniquely identifies a row?",
                    "Foreign Key", "Primary Key", "Unique Key", "Reference", "B"
                },
                {
                    "Which key connects two tables?",
                    "Primary Key", "Foreign Key", "Main Key", "Join Key", "B"
                }
            },

            {
                {
                    "Which keyword handles exceptions?",
                    "try", "check", "error", "handle", "A"
                },
                {
                    "Which block handles an exception?",
                    "try", "catch", "finally", "throw", "B"
                },
                {
                    "Which block always executes?",
                    "try", "catch", "finally", "error", "C"
                },
                {
                    "Which keyword manually throws an exception?",
                    "throws", "throw", "exception", "error", "B"
                },
                {
                    "Which keyword declares exceptions?",
                    "throw", "throws", "declare", "exception", "B"
                },
                {
                    "Which class is the parent of all exceptions?",
                    "Exception", "Throwable", "Error", "Object", "B"
                },
                {
                    "Which keyword creates a thread?",
                    "thread", "new", "start", "run", "B"
                },
                {
                    "Which method starts a thread?",
                    "run()", "start()", "begin()", "execute()", "B"
                },
                {
                    "Which interface can be used to create a thread?",
                    "Runnable", "Threadable", "Executable", "Process", "A"
                },
                {
                    "Which class is used for file reading?",
                    "FileReader", "FileWriter", "FileInput", "ReaderFile", "A"
                },
                {
                    "Which class is used for file writing?",
                    "FileReader", "FileWriter", "FileOutput", "WriterFile", "B"
                },
                {
                    "Which keyword imports a package?",
                    "include", "import", "package", "using", "B"
                },
                {
                    "Which keyword defines a package?",
                    "package", "import", "namespace", "folder", "A"
                },
                {
                    "Which class is the root of Java class hierarchy?",
                    "Main", "Object", "Class", "Root", "B"
                },
                {
                    "Which interface is used for sorting?",
                    "Comparator", "Sorter", "Sortable", "Ordering", "A"
                },
                {
                    "Which interface provides natural ordering?",
                    "Comparator", "Comparable", "Sortable", "Order", "B"
                },
                {
                    "Which class stores key-value pairs?",
                    "List", "Set", "Map", "Queue", "C"
                },
                {
                    "Which class is synchronized dynamic array?",
                    "ArrayList", "Vector", "HashMap", "HashSet", "B"
                },
                {
                    "Which class provides a resizable array?",
                    "ArrayList", "HashSet", "TreeMap", "VectorOnly", "A"
                },
                {
                    "Which collection follows FIFO?",
                    "Stack", "Queue", "Set", "Map", "B"
                }
            },

            {
                {
                    "Which language is Java based on?",
                    "C/C++", "Python", "Ruby", "HTML", "A"
                },
                {
                    "Which component compiles Java source code?",
                    "JVM", "JDK compiler", "JRE", "Database", "B"
                },
                {
                    "What does JVM stand for?",
                    "Java Virtual Machine", "Java Variable Machine", "Java Visual Machine", "Java Version Manager", "A"
                },
                {
                    "What does JRE stand for?",
                    "Java Runtime Environment", "Java Run Engine", "Java Runtime Editor", "Java Resource Environment", "A"
                },
                {
                    "What does JDK stand for?",
                    "Java Development Kit", "Java Design Kit", "Java Database Kit", "Java Development Key", "A"
                },
                {
                    "Which file contains Java bytecode?",
                    ".java", ".class", ".exe", ".txt", "B"
                },
                {
                    "Which keyword is used to define a static member?",
                    "static", "constant", "global", "shared", "A"
                },
                {
                    "Can a static method access non-static variables directly?",
                    "Yes", "No", "Always", "Only public", "B"
                },
                {
                    "Which class is immutable?",
                    "String", "StringBuilder", "ArrayList", "Scanner", "A"
                },
                {
                    "Which class is mutable?",
                    "String", "StringBuilder", "Integer", "Character", "B"
                },
                {
                    "Which method returns String length?",
                    "size()", "length()", "count()", "getLength()", "B"
                },
                {
                    "Which method compares String contents?",
                    "==", "equals()", "compare()", "same()", "B"
                },
                {
                    "Which keyword is used to synchronize code?",
                    "sync", "synchronized", "lock", "thread", "B"
                },
                {
                    "Which exception occurs for division by zero?",
                    "NullPointerException", "ArithmeticException", "IOException", "SQLException", "B"
                },
                {
                    "Which exception occurs for invalid array index?",
                    "ArrayIndexOutOfBoundsException", "IndexException", "ArrayException", "RangeException", "A"
                },
                {
                    "Which exception occurs when accessing a null object?",
                    "NullPointerException", "ObjectException", "NullException", "PointerException", "A"
                },
                {
                    "Which SQL command removes a table?",
                    "DELETE", "DROP", "REMOVE", "CLEAR", "B"
                },
                {
                    "Which SQL clause filters rows?",
                    "WHERE", "FILTER", "HAVING", "CHECK", "A"
                },
                {
                    "Which clause groups rows?",
                    "GROUP BY", "ORDER BY", "WHERE", "JOIN", "A"
                },
                {
                    "Which clause sorts query results?",
                    "SORT BY", "ORDER BY", "GROUP BY", "ARRANGE BY", "B"
                }
            }
        };

        for (int i = 0; i < examTitles.length; i++) {

            try {

                int examId = getExamId(
                        con,
                        examTitles[i][0]
                );

                if (examId == 0) {

                    String insertExam =
                            "INSERT INTO exams " +
                            "(title, teacher_name) " +
                            "VALUES (?, ?)";

                    try (PreparedStatement ps =
                                 con.prepareStatement(
                                         insertExam,
                                         Statement.RETURN_GENERATED_KEYS)) {

                        ps.setString(1, examTitles[i][0]);
                        ps.setString(2, examTitles[i][1]);

                        ps.executeUpdate();

                        ResultSet keys =
                                ps.getGeneratedKeys();

                        if (keys.next()) {

                            examId =
                                    keys.getInt(1);
                        }
                    }

                    System.out.println(
                            examTitles[i][0] +
                            " created."
                    );
                }

                insertQuestions(
                        con,
                        examId,
                        questionSets[i]
                );

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    public static int getExamId(
            Connection con,
            String title) throws Exception {

        String sql =
                "SELECT exam_id FROM exams WHERE title=?";

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1, title);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("exam_id");
            }
        }

        return 0;
    }

    public static void insertQuestions(
            Connection con,
            int examId,
            String[][] questions) throws Exception {

        String countSql =
                "SELECT COUNT(*) FROM questions " +
                "WHERE exam_id=?";

        try (PreparedStatement countPs =
                     con.prepareStatement(countSql)) {

            countPs.setInt(1, examId);

            ResultSet rs =
                    countPs.executeQuery();

            if (rs.next() &&
                rs.getInt(1) >= 20) {

                return;
            }
        }

        String sql =
                "INSERT INTO questions " +
                "(exam_id, question_text, option_a, " +
                "option_b, option_c, option_d, " +
                "correct_option) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            for (String[] q : questions) {

                ps.setInt(1, examId);
                ps.setString(2, q[0]);
                ps.setString(3, q[1]);
                ps.setString(4, q[2]);
                ps.setString(5, q[3]);
                ps.setString(6, q[4]);
                ps.setString(7, q[5]);

                ps.executeUpdate();
            }
        }

        System.out.println(
                "20 questions added to Exam ID " +
                examId
        );
    }
}