package com.example.dogood;

public class InsertAccountDetails {

    public InsertAccountDetails() {}

    String name, email, dob, nic, phoneNumber, location, occupation;

    public InsertAccountDetails(String name, String email, String dob, String nic, String phoneNumber, String location, String occupation) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.nic = nic;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.occupation = occupation;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
