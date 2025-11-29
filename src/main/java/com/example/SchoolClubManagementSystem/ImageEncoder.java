package com.example.SchoolClubManagementSystem;

import org.springframework.stereotype.Component;

import java.io.Console;
import java.util.Base64;

@Component
public class ImageEncoder {
    public String encodeToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}