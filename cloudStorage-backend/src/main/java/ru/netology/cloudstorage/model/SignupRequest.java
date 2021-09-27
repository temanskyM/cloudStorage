package ru.netology.cloudstorage.model;

import lombok.Data;

@Data
public class SignupRequest {
    private String login;
    private String password;
    private String email;
}
