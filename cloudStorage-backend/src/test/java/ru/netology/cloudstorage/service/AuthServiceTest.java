package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Test;
import org.openapitools.model.LoginDto;
import org.openapitools.model.LoginSuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.cloudstorage.AbstractContainerTest;
import ru.netology.cloudstorage.model.SignupRequest;
import ru.netology.cloudstorage.model.User;
import ru.netology.cloudstorage.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest extends AbstractContainerTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void authenticate() {
        String login = "login";
        String password = "password";
        String mail = "mail";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setLogin(login);
        signupRequest.setPassword(password);
        signupRequest.setEmail(mail);
        authService.registerUser(signupRequest);

        LoginDto loginDto = new LoginDto();
        loginDto.setLogin(login);
        loginDto.setPassword(password);
        LoginSuccessDto resultDto = authService.authenticate(loginDto);

        assertNotNull(resultDto.getAuthToken());
    }

    @Test
    void registerUser() {
        String login = "login";
        String password = "password";
        String mail = "mail";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setLogin(login);
        signupRequest.setPassword(password);
        signupRequest.setEmail(mail);
        authService.registerUser(signupRequest);

        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new RuntimeException("Unable to find user"));
        assertNotNull(user.getId());
        assertEquals(login, user.getUsername());
        assertNotNull(user.getPassword());
        assertEquals(mail, user.getEmail());
    }
}