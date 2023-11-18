package model;

public class Quiz_Report {
    private int account_id;
    private String account_name;
    private int quiz_id;
    private String quiz_name;
    private int question_id;
    private int student_choice;
    private Boolean is_correct;
    
    public Quiz_Report(int account_id, String account_name, int quiz_id, String quiz_name, int question_id, int student_choice, Boolean is_correct) {
        this.account_id = account_id;
        this.account_name = account_name;
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.question_id = question_id;
        this.student_choice = student_choice;
        this.is_correct = is_correct;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getStudent_choice() {
        return student_choice;
    }

    public void setStudent_choice(int student_choice) {
        this.student_choice = student_choice;
    }

    public Boolean getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(Boolean is_correct) {
        this.is_correct = is_correct;
    }  
    
}
