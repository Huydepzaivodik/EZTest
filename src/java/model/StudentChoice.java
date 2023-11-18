package model;

public class StudentChoice {

    private int student_student_choice;
    private Account student_id;
    private Choice student_choice;
    private Quiz_Question question_id;
    private Quiz quiz_id;

    public StudentChoice() {
    }

    public StudentChoice(int student_student_choice, Account student_id, Choice student_choice, Quiz_Question question_id, Quiz quiz_id) {
        this.student_student_choice = student_student_choice;
        this.student_id = student_id;
        this.student_choice = student_choice;
        this.question_id = question_id;
        this.quiz_id = quiz_id;
    }

    public int getStudent_student_choice() {
        return student_student_choice;
    }

    public void setStudent_student_choice(int student_student_choice) {
        this.student_student_choice = student_student_choice;
    }

    public Account getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Account student_id) {
        this.student_id = student_id;
    }

    public Choice getStudent_choice() {
        return student_choice;
    }

    public void setStudent_choice(Choice student_choice) {
        this.student_choice = student_choice;
    }

    public Quiz_Question getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Quiz_Question question_id) {
        this.question_id = question_id;
    }

    public Quiz getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Quiz quiz_id) {
        this.quiz_id = quiz_id;
    }

}
