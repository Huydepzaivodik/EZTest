package model;

public class Quiz_Question {

    private int question_id;
    private Quiz quiz_id;
    private String question_text;
    private String explanation;
    private boolean question_type;
    private boolean is_visible;

    public Quiz_Question() {
    }

    public Quiz_Question(Quiz quiz_id, String question_text, String explanation, boolean question_type, boolean is_visible) {
        this.quiz_id = quiz_id;
        this.question_text = question_text;
        this.explanation = explanation;
        this.question_type = question_type;
        this.is_visible = is_visible;
    }

    public Quiz_Question(int question_id, Quiz quiz_id, String question_text, String explanation, boolean question_type, boolean is_visible) {
        this.question_id = question_id;
        this.quiz_id = quiz_id;
        this.question_text = question_text;
        this.explanation = explanation;
        this.question_type = question_type;
        this.is_visible = is_visible;
    }

    public Quiz_Question(int question_id, Quiz quiz_id, String question_text, String explanation, boolean question_type) {
        this.question_id = question_id;
        this.quiz_id = quiz_id;
        this.question_text = question_text;
        this.explanation = explanation;
        this.question_type = question_type;
    }

    public Quiz_Question(int question_id) {
        this.question_id = question_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public Quiz getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Quiz quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Quiz_Question(int question_id, String question_text, String explanation, boolean question_type) {
        this.question_id = question_id;
        this.question_text = question_text;
        this.explanation = explanation;
        this.is_visible = is_visible;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }


    public boolean isQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(boolean question_type) {
        this.question_type = question_type;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

}
