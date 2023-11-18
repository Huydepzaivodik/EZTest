package model;

import java.sql.Timestamp;

public class Quiz {

    private int quiz_id;
    private Class class_id;
    private String quiz_name;
    private Timestamp start_time;
    private int duration;
    private boolean status;
    private Timestamp date_end;
    private boolean is_visible;
    private boolean is_displaydetail;
    private Timestamp create_date;
    private int created_by;

    public Quiz() {
    }

    public Quiz(int quiz_id, String quiz_name, Timestamp start_time, int duration, Timestamp date_end, boolean is_visible, boolean is_displaydetail) {
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.start_time = start_time;
        this.duration = duration;
        this.date_end = date_end;
        this.is_visible = is_visible;
        this.is_displaydetail = is_displaydetail;
    }

    public Quiz(int quiz_id, Class class_id, String quiz_name, Timestamp start_time, int duration, Timestamp date_end, boolean is_visible, Timestamp create_date) {
        this.quiz_id = quiz_id;
        this.class_id = class_id;
        this.quiz_name = quiz_name;
        this.start_time = start_time;
        this.duration = duration;
        this.date_end = date_end;
        this.is_visible = is_visible;
        this.create_date = create_date;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public Quiz(Class class_id) {
        this.class_id = class_id;
    }

    public Quiz(Class class_id, String quiz_name) {
        this.class_id = class_id;
        this.quiz_name = quiz_name;
    }

    public Quiz(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Class getClass_id() {
        return class_id;
    }

    public void setClass_id(Class class_id) {
        this.class_id = class_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getDate_end() {
        return date_end;
    }

    public void setDate_end(Timestamp date_end) {
        this.date_end = date_end;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    public boolean isIs_displaydetail() {
        return is_displaydetail;
    }

    public void setIs_displaydetail(boolean is_displaydetail) {
        this.is_displaydetail = is_displaydetail;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

}
