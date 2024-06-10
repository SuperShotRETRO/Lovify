package com.example.lovify.model;

public class User {
    // Private fields for User attributes
    private String name;
    private String email;
    private String imageURL;
    private int age;
    private String gender;
    private String preferredGender;

    // Constructor to initialize all fields
    public User(String name, String email, String imageURL, int age, String gender, String preferredGender) {
        this.name = name;
        this.email = email;
        this.imageURL = imageURL;
        this.age = age;
        this.gender = gender;
        this.preferredGender = preferredGender;
    }


    // Getter and setter methods for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for 'name'
    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = email;
    }

    // Getter and setter methods for 'imageURL'
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // Getter and setter methods for 'age'
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and setter methods for 'gender'
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter and setter methods for 'preferredGender'
    public String getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(String preferredGender) {
        this.preferredGender = preferredGender;
    }

    // Override the toString() method for better readability
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", preferredGender='" + preferredGender + '\'' +
                '}';
    }


}
