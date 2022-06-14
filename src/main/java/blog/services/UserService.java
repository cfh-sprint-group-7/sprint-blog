package blog.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import blog.dto.UserRegistrationDto;
import blog.models.User;

public interface UserService extends UserDetailsService{
    User save(UserRegistrationDto registrationDto);
}