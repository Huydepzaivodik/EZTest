package model;

import java.sql.Timestamp;

public class Report {
    private int result_id;
    private int account_id;
    private String account_name;
    private String class_name;
    private float score;
    private Timestamp start_time;
    private Timestamp create_date;
    
    public Report(int result_id, int account_id, String account_name, String class_name , float score, Timestamp start_time, Timestamp create_date) {
        this.result_id = result_id;
        this.account_id = account_id;
        this.account_name = account_name;
        this.class_name = class_name;
        this.score = score;
        this.start_time = start_time;
        this.create_date = create_date;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }
    
    public String getStart_date_string() {
        return start_time.toString();
    }

    public Timestamp getCreate_date() {
        return create_date;
    }
    
    public String getCreate_date_string() {
        return create_date.toString();
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }
    
}
