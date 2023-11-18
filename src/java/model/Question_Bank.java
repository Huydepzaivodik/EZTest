/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Question_Bank {
    private int question_bank_id;
    private Course course_id;
    private String question_text;
    private String explanation;
    private boolean question_type;
    private boolean is_visible;

    public Question_Bank() {
    }

    public Question_Bank(int question_bank_id, Course course_id, String question_text, String explanation, boolean question_type) {
        this.question_bank_id = question_bank_id;
        this.course_id = course_id;
        this.question_text = question_text;
        this.explanation = explanation;
        this.question_type = question_type;
    }

    public Question_Bank(Course course_id, String question_text, String explanation, boolean question_type, boolean is_visible) {
        this.course_id = course_id;
        this.question_text = question_text;
        this.explanation = explanation;
        this.question_type = question_type;
        this.is_visible = is_visible;
    }

    public Question_Bank(int question_bank_id, Course course_id, String question_text, String explanation, boolean question_type, boolean is_visible) {
        this.question_bank_id = question_bank_id;
        this.course_id = course_id;
        this.question_text = question_text;
        this.explanation = explanation;
        this.question_type = question_type;
        this.is_visible = is_visible;
    }
    
    public Question_Bank(int question_bank_id) {
        this.question_bank_id = question_bank_id;
    }

    public int getQuestion_bank_id() {
        return question_bank_id;
    }

    public void setQuestion_bank_id(int question_bank_id) {
        this.question_bank_id = question_bank_id;
    }

    public Course getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Course course_id) {
        this.course_id = course_id;
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
