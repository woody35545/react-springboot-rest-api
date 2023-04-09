package com.example.gccoffee.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @DisplayName("Test Invalid Email")
    @Test
    public void testInvalidEmail(){
        assertThrows(IllegalArgumentException.class, ()-> {
            var email = new Email("11");
        });
    }


}