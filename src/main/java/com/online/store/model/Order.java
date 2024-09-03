package com.online.store.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Order {

    @NotEmpty(message = "ItemId is required")
    private String itemId;

    @NotEmpty(message = "Full Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Full Name should only contain letters and spaces")
    private String fullName;

    @NotEmpty(message = "Address is required")
    private String address;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")
    private String email;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone Number should be in the format xxx-xxx-xxxx")
    @NotEmpty(message = "Phone Number is required")
    private String phoneNumber;

    @Pattern(regexp = "\\d{19}", message = "Credit Card Number should be 19 digits long")
    @NotEmpty(message = "Credit Card Number is required")
    private String creditCardNumber;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }


}

