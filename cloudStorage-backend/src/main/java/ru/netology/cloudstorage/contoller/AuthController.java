package ru.netology.cloudstorage.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.LoginApi;
import org.openapitools.model.LoginDto;
import org.openapitools.model.LoginSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.cloudstorage.model.SignupRequest;
import ru.netology.cloudstorage.service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements LoginApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<LoginSuccessDto> login(@Valid LoginDto loginDto) {
        log.debug("Login request");
        LoginSuccessDto response = authService.authenticate(loginDto);
        log.debug("Login success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.debug("Register request");
        authService.registerUser(signUpRequest);
        log.debug("Register success");
        return ResponseEntity.ok("User registered successfully!");
    }
}
