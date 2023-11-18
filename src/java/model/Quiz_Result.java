package model;

import java.sql.Timestamp;

public class Quiz_Result {

    private int result_id;
    private Quiz quiz_id;
    private Account student_id;
    private float score;
    private Timestamp start_time_student;
    private Timestamp end_time_student;
    private boolean is_valid;

    public Quiz_Result(int result_id, Quiz quiz_id, Account student_id, float score, Timestamp start_time_student, Timestamp end_time_student) {
        this.result_id = result_id;
        this.quiz_id = quiz_id;
        this.student_id = student_id;
        this.score = score;
        this.start_time_student = start_time_student;
        this.end_time_student = end_time_student;
    }

    public Quiz_Result(int result_id, Quiz quiz_id, Account student_id, float score, Timestamp start_time_student, Timestamp end_time_student, boolean is_valid) {
        this.result_id = result_id;
        this.quiz_id = quiz_id;
        this.student_id = student_id;
        this.score = score;
        this.start_time_student = start_time_student;
        this.end_time_student = end_time_student;
        this.is_valid = is_valid;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public Quiz_Result() {
    }

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public Quiz getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Quiz quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Account getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Account student_id) {
        this.student_id = student_id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Timestamp getStart_time_student() {
        return start_time_student;
    }

    public void setStart_time_student(Timestamp start_time_student) {
        this.start_time_student = start_time_student;
    }

    public Timestamp getEnd_time_student() {
        return end_time_student;
    }

    public void setEnd_time_student(Timestamp end_time_student) {
        this.end_time_student = end_time_student;
    }

}
