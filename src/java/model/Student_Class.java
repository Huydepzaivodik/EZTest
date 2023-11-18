package model;

public class Student_Class {

    private int studentclass_id;
    private Class class_id;
    private Account student_id;
    private boolean is_visible;

    public Student_Class() {
    }

    public int getStudentclass_id() {
        return studentclass_id;
    }

    public void setStudentclass_id(int studentclass_id) {
        this.studentclass_id = studentclass_id;
    }

    public Class getClass_id() {
        return class_id;
    }

    public void setClass_id(Class class_id) {
        this.class_id = class_id;
    }

    public Account getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Account student_id) {
        this.student_id = student_id;
    }

    public Student_Class(int studentclass_id, Class class_id, Account student_id) {
        this.studentclass_id = studentclass_id;
        this.class_id = class_id;
        this.student_id = student_id;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

}
