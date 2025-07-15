package com.nhnhan.find_your_keeb.dto;

public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String displayName;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;

    public UserProfileResponse() {}

    public UserProfileResponse(Long id, String username, String email, String displayName, String gender, String dateOfBirth, String address, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
} 