package model;

public class Class {

    private int class_id;
    private Course course_id;
    private String class_name;
    private Account teacher_id;
    private boolean status;
    private boolean is_visible;
    private Account alter_teacher_id;

    public Class() {
    }

    public Class(int class_id) {
        this.class_id = class_id;
    }

    public Class(Course course_id, String class_name, Account teacher_id, boolean status) {
        this.course_id = course_id;
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.status = status;
    }

    public Class(int class_id, Course course_id, String class_name, Account teacher_id, boolean status) {
        this.class_id = class_id;
        this.course_id = course_id;
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.status = status;
    }

    public Class(int class_id, Course course_id, String class_name, Account teacher_id, boolean status, boolean is_visible) {
        this.class_id = class_id;
        this.course_id = course_id;
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.status = status;
        this.is_visible = is_visible;
    }

    public Class(int class_id, Course course_id, String class_name, Account teacher_id, boolean status, boolean is_visible, Account alter_teacher_id) {
        this.class_id = class_id;
        this.course_id = course_id;
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.status = status;
        this.is_visible = is_visible;
        this.alter_teacher_id = alter_teacher_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public Course getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Course course_id) {
        this.course_id = course_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Account getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Account teacher_id) {
        this.teacher_id = teacher_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    public Account getAlter_teacher_id() {
        return alter_teacher_id;
    }

    public void setAlter_teacher_id(Account alter_teacher_id) {
        this.alter_teacher_id = alter_teacher_id;
    }

}
