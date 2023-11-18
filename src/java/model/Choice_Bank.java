/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Choice_Bank {

    private int choice_bank_id;
    private Question_Bank question_bank_id;
    private String choice_bank_text;
    private boolean is_correct;
    private int weight;
    private boolean is_visible;

    public Choice_Bank() {
    }

    public Choice_Bank(int choice_bank_id, Question_Bank question_bank_id, String choice_bank_text, boolean is_correct, int weight) {
        this.choice_bank_id = choice_bank_id;
        this.question_bank_id = question_bank_id;
        this.choice_bank_text = choice_bank_text;
        this.is_correct = is_correct;
        this.weight = weight;
    }

    public Choice_Bank(int choice_bank_id, Question_Bank question_bank_id, String choice_bank_text, boolean is_correct, int weight, boolean is_visible) {
        this.choice_bank_id = choice_bank_id;
        this.question_bank_id = question_bank_id;
        this.choice_bank_text = choice_bank_text;
        this.is_correct = is_correct;
        this.weight = weight;
        this.is_visible = is_visible;
    }

    public Choice_Bank(Question_Bank question_bank_id, String choice_bank_text, boolean is_correct, int weight) {
        this.question_bank_id = question_bank_id;
        this.choice_bank_text = choice_bank_text;
        this.is_correct = is_correct;
        this.weight = weight;
    }

    public int getChoice_bank_id() {
        return choice_bank_id;
    }

    public void setChoice_bank_id(int choice_bank_id) {
        this.choice_bank_id = choice_bank_id;
    }

    public Question_Bank getQuestion_bank_id() {
        return question_bank_id;
    }

    public void setQuestion_bank_id(Question_Bank question_bank_id) {
        this.question_bank_id = question_bank_id;
    }

    public String getChoice_bank_text() {
        return choice_bank_text;
    }

    public void setChoice_bank_text(String choice_bank_text) {
        this.choice_bank_text = choice_bank_text;
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
