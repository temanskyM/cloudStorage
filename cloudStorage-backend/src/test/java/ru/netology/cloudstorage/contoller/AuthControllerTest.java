package ru.netology.cloudstorage.contoller;

import org.junit.jupiter.api.Test;
import org.openapitools.model.LoginDto;
import org.openapitools.model.LoginSuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.netology.cloudstorage.AbstractContainerTest;
import ru.netology.cloudstorage.model.SignupRequest;
import ru.netology.cloudstorage.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest extends AbstractContainerTest {


    @Test
    void login() {
        String login = "login";
        String password = "password";
        String mail = "mail";

        signup(login, password, mail);

        LoginDto input = new LoginDto();
        input.setLogin(login);
        input.setPassword(password);

        String url = "http://localhost:" + devApp.getMappedPort(8080) + "/api/login";

        ResponseEntity<LoginSuccessDto> response = restTemplate.postForEntity(url, input, LoginSuccessDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoginSuccessDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getAuthToken());
    }

    private void signup(String login, String password, String mail) {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setLogin(login);
        signupRequest.setPassword(password);
        signupRequest.setEmail(mail);

        String url = "http://localhost:" + devApp.getMappedPort(8080) + "/api/signup";

        ResponseEntity<String> response = restTemplate.postForEntity(url, signupRequest, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}