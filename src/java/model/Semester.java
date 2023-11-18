package model;

public class Semester {

    private int semester_id;
    private String semester_name;

    public Semester() {
    }

    public Semester(int semester_id) {
        this.semester_id = semester_id;
    }

    public int getSemester_id() {
        return semester_id;
    }

    public void setSemester_id(int semester_id) {
        this.semester_id = semester_id;
    }

    public String getSemester_name() {
        return semester_name;
    }

    public void setSemester_name(String semester_name) {
        this.semester_name = semester_name;
    }

}
