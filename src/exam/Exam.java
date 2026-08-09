package exam;

public class Exam {

    private int examId;
    private String title;
    private String teacherName;

    public Exam() {
    }

    public Exam(int examId, String title, String teacherName) {
        this.examId = examId;
        this.title = title;
        this.teacherName = teacherName;
    }

    public Exam(String title, String teacherName) {
        this.title = title;
        this.teacherName = teacherName;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}