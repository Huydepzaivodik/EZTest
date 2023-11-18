package model;

public class Choice {

    private int choice_id;
    private Quiz_Question question_id;
    private String choice_text;
    private boolean is_correct;
    private int weight;
    private boolean is_visible;

    public Choice(Quiz_Question question_id, String choice_text, boolean is_correct, int weight, boolean is_visible) {
        this.question_id = question_id;
        this.choice_text = choice_text;
        this.is_correct = is_correct;
        this.weight = weight;
        this.is_visible = is_visible;
    }

    public Choice(int choice_id, Quiz_Question question_id, String choice_text, boolean is_correct, int weight, boolean is_visible) {
        this.choice_id = choice_id;
        this.question_id = question_id;
        this.choice_text = choice_text;
        this.is_correct = is_correct;
        this.weight = weight;
        this.is_visible = is_visible;
    }

    public Choice(Quiz_Question question_id, String choice_text, boolean is_correct, int weight) {
        this.question_id = question_id;
        this.choice_text = choice_text;
        this.is_correct = is_correct;
        this.weight = weight;
    }

    

    public Choice() {
    }

    public Choice(int choice_id) {
        this.choice_id = choice_id;
    }

    public Choice(String choice_text, boolean is_correct, byte[] image) {
        this.choice_text = choice_text;
        this.is_correct = is_correct;
    }

    public Choice(int choice_id, Quiz_Question question_id, String choice_text, boolean is_correct, int weight) {
        this.choice_id = choice_id;
        this.question_id = question_id;
        this.choice_text = choice_text;
        this.is_correct = is_correct;
        this.weight = weight;
    }

    public int getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(int choice_id) {
        this.choice_id = choice_id;
    }

    public Quiz_Question getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Quiz_Question question_id) {
        this.question_id = question_id;
    }

    public String getChoice_text() {
        return choice_text;
    }

    public void setChoice_text(String choice_text) {
        this.choice_text = choice_text;
    }

    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

}
