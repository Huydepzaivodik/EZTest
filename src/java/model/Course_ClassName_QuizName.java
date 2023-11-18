package model;

public class Course_ClassName_QuizName {

    private String quiz_name;
    private String course_name;
    private String class_name;

    public Course_ClassName_QuizName() {
    }

    public Course_ClassName_QuizName(String quiz_name, String course_name, String class_name) {
        this.quiz_name = quiz_name;
        this.course_name = course_name;
        this.class_name = class_name;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

}
