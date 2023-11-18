package model;

import java.sql.Date;

public class Account {

    private int id;
    private String email, password, name;
    private Date dob;
    private boolean gender;
    private String phone, address;
    private int role_id;
    private String img;
    private boolean is_valid;
    private boolean is_login;

    public Account() {
    }

    public Account(int id) {
        this.id = id;
    }

    public Account(int id, String email, String name, boolean gender, String phone, String address, int role_id) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.role_id = role_id;
    }

    public boolean isIs_login() {
        return is_login;
    }

    public void setIs_login(boolean is_login) {
        this.is_login = is_login;
    }

    public Account(int id, String email, String password, String name, Date dob, boolean gender, String phone, String address, int role_id, String img, boolean is_valid) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.role_id = role_id;
        this.img = img;
        this.is_valid = is_valid;
    }

    public Account(String email, String password, String name, Date dob, boolean gender, String phone, String address, int role_id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.role_id = role_id;
    }

    public Account(int id, String email, String password, String name, Date dob, boolean gender, String phone, String address, int role_id, String img) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.role_id = role_id;
        this.img = img;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
