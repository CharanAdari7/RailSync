package domain;

import java.util.Objects;

public class Passenger {

    private String passengerId;
    private String passengerName;
    private int age;
    private String gender;
    private String phone;
    private String email;

    public Passenger() {
    }

    public Passenger(String passengerId, String passengerName,
                     int age, String gender, String phone, String email) {

        setPassengerId(passengerId);
        setPassengerName(passengerName);
        setAge(age);
        setGender(gender);
        setPhone(phone);
        setEmail(email);
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        if (passengerId == null || passengerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger ID cannot be empty.");
        }
        this.passengerId = passengerId.trim();
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty.");
        }
        this.passengerName = passengerName.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? "" : gender.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    @Override
    public String toString() {
        return passengerId + " - " + passengerName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Passenger)) return false;

        Passenger other = (Passenger) obj;
        return Objects.equals(passengerId, other.passengerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerId);
    }
}