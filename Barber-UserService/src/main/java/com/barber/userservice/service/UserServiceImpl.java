package com.barber.userservice.service;

import com.barber.userservice.dto.UserDTO;
import com.barber.userCommons.entity.*;
import com.barber.userservice.exception.EmailAlreadyExistsException;
import com.barber.userservice.exception.InvalidInputException;
import com.barber.userservice.exception.PhoneNumberAlreadyExistsException;
import com.barber.userservice.repository.RoleRepository;
import com.barber.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String PHONE_NUMBER_PATTERN = "^(\\+34|0034)?[6789]\\d{8}$";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    public UserDTO createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        // Validar el número de teléfono
        validatePhoneNumber(user.getPhoneNumber());

        // Validar la contraseña
        validatePassword(user.getPassword());

        // Encriptar la contraseña antes de guardar el usuario
        user.setPassword(passwordEncoderService.encode(user.getPassword()));

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    public UserDTO updateUser(Long id, User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (!existingUserOptional.isPresent()) {
            return null;
        }

        User existingUser = existingUserOptional.get();

        boolean isEmailChanged = !existingUser.getEmail().equals(user.getEmail());
        boolean isPhoneNumberChanged = !existingUser.getPhoneNumber().equals(user.getPhoneNumber());

        if (isEmailChanged && userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (isPhoneNumberChanged && userRepository.findByPhoneNumber(user.getPhoneNumber()) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        // Validar el número de teléfono si ha cambiado
        if (isPhoneNumberChanged) {
            validatePhoneNumber(user.getPhoneNumber());
        }

        // Validar la contraseña si está presente
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            validatePassword(user.getPassword());
            existingUser.setPassword(passwordEncoderService.encode(user.getPassword()));
        }

        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setFirstSurname(user.getFirstSurname());
        existingUser.setSecondSurname(user.getSecondSurname());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        existingUser.setRole(role);

        existingUser = userRepository.save(existingUser);

        return convertToDTO(existingUser);
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setFirstSurname(user.getFirstSurname());
        userDTO.setSecondSurname(user.getSecondSurname());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRoleId(user.getRole().getId());
        return userDTO;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> findByRoleId(Long roleId) {
        return userRepository.findByRoleId(roleId);
    }

    private void validatePassword(String password) {
        if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            throw new InvalidInputException("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!Pattern.matches(PHONE_NUMBER_PATTERN, phoneNumber)) {
            throw new InvalidInputException("Invalid phone number format for Spain.", HttpStatus.BAD_REQUEST);
        }
    }
}
