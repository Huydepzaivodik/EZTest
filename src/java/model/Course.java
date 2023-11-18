package model;

public class Course {

    private int course_id;
    private String course_name;
    private Semester semester_id;
    private Major major_id;
    private boolean is_visible;

    public Course() {
    }

    public Course(int course_id) {
        this.course_id = course_id;
    }

    public Course(String course_name, Semester semester_id, Major major_id, boolean is_visible) {
        this.course_name = course_name;
        this.semester_id = semester_id;
        this.major_id = major_id;
        this.is_visible = is_visible;
    }

    public Course(int course_id, String course_name, Semester semester_id, Major major_id) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.semester_id = semester_id;
        this.major_id = major_id;
    }

    public Course(int course_id, String course_name, Semester semester_id, Major major_id, boolean is_visible) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.semester_id = semester_id;
        this.major_id = major_id;
        this.is_visible = is_visible;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Semester getSemester_id() {
        return semester_id;
    }

    public void setSemester_id(Semester semester_id) {
        this.semester_id = semester_id;
    }

    public Major getMajor_id() {
        return major_id;
    }

    public void setMajor_id(Major major_id) {
        this.major_id = major_id;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

}
