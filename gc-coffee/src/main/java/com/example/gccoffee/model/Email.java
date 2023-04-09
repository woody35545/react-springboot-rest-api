package com.example.gccoffee.model;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;
@Getter
public class Email {
    private final String address;


    public Email(String address) {
        /* for validating email address */
        Assert.notNull(address,"address should not be null");
        Assert.isTrue(address.length()>=4 && address.length()<=50,"address length must be between 4 and 50 characters.");
        Assert.isTrue(checkAddress(address), "Invalid email address");

        this.address = address;
    }

    private static boolean checkAddress(String address){
        /* validate email address using regex */
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", address);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }
}
