package exam;

public class Result {

    private int resultId;
    private int studentId;
    private int examId;
    private int score;
    private int totalQuestions;
    private String grade;

    public Result() {
    }

    public Result(
            int resultId,
            int studentId,
            int examId,
            int score,
            int totalQuestions,
            String grade) {

        this.resultId = resultId;
        this.studentId = studentId;
        this.examId = examId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.grade = grade;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}