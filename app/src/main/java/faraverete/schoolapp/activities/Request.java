package faraverete.schoolapp.activities;

/**
 * Created by faraverete on 10.12.2016.
 */

public class Request {

    private String name;
    private String email;
    private String address;
    private String grades;

    public Request(String name, String email, String address, String grades) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.grades = grades;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String toString() {
        return "Name: " + this.name + "\nEmail: " + this.email + "\nAddress: " + this.address + "\nGrades: " + this.grades;
    }
}
