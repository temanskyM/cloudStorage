package ru.netology.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.LoginDto;
import org.openapitools.model.LoginSuccessDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.cloudstorage.Util.JwtUtils;
import ru.netology.cloudstorage.model.SignupRequest;
import ru.netology.cloudstorage.model.User;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.service.security.model.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public LoginSuccessDto authenticate(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());

        LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
        loginSuccessDto.setAuthToken(jwt);
        return loginSuccessDto;
    }

    public void registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Email is already in use");
        }

        User user = new User(signUpRequest.getLogin(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
    }
}
