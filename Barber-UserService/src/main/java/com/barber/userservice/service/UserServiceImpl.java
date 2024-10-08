package com.barber.userservice.service;

import com.barber.userservice.dto.UserDTO;
import com.barber.userCommons.entity.*;
import com.barber.userservice.exception.EmailAlreadyExistsException;
import com.barber.userservice.exception.InvalidInputException;
import com.barber.userservice.exception.PhoneNumberAlreadyExistsException;
import com.barber.userservice.feignClient.AppointmentClientRest;
import com.barber.userservice.repository.RoleRepository;
import com.barber.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Implementación del servicio para la gestión de usuarios
@Service
public class UserServiceImpl implements IUserService {

    // Patrones de validación para contraseña y número de teléfono
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String PHONE_NUMBER_PATTERN = "^(\\+34|0034)?[6789]\\d{8}$";

    @Autowired
    private UserRepository userRepository; // Repositorio para la persistencia de usuarios

    @Autowired
    private RoleRepository roleRepository; // Repositorio para la persistencia de roles
    
    @Autowired
    private AppointmentClientRest appointmentClientRest; // Cliente Feign para el servicio de citas

    @Autowired
    private PasswordEncoderService passwordEncoderService; // Servicio para encriptar contraseñas

    // Crea un nuevo usuario, validando email, número de teléfono y contraseña
    public UserDTO createUser(User user) {
        // Verifica si el email ya existe
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Verifica si el número de teléfono ya existe
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        // Validar el número de teléfono
        validatePhoneNumber(user.getPhoneNumber());

        // Validar la contraseña
        validatePassword(user.getPassword());

        // Encriptar la contraseña antes de guardar el usuario
        user.setPassword(passwordEncoderService.encode(user.getPassword()));

        // Busca el rol y asigna al usuario
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        // Guarda y retorna el usuario creado
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser); // Convierte a DTO antes de retornar
    }

    // Obtiene todos los usuarios y los convierte a DTO
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Busca un usuario por su ID y lo convierte a DTO
    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null); // Retorna el usuario si existe, o null si no
    }

    // Actualiza un usuario existente, validando cambios en email, teléfono y contraseña
    public UserDTO updateUser(Long id, User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (!existingUserOptional.isPresent()) {
            return null; // Retorna null si el usuario no existe
        }

        User existingUser = existingUserOptional.get();

        // Verifica si el email o el número de teléfono han cambiado
        boolean isEmailChanged = !existingUser.getEmail().equals(user.getEmail());
        boolean isPhoneNumberChanged = !existingUser.getPhoneNumber().equals(user.getPhoneNumber());

        // Verifica si el nuevo email ya existe
        if (isEmailChanged && userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Verifica si el nuevo número de teléfono ya existe
        if (isPhoneNumberChanged && userRepository.findByPhoneNumber(user.getPhoneNumber()) != null) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }

        // Validar el número de teléfono si ha cambiado
        if (isPhoneNumberChanged) {
            validatePhoneNumber(user.getPhoneNumber());
        }

        // Validar y actualizar la contraseña si está presente
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            validatePassword(user.getPassword());
            existingUser.setPassword(passwordEncoderService.encode(user.getPassword())); // Encripta la nueva contraseña
        }

        // Actualiza los detalles del usuario
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setFirstSurname(user.getFirstSurname());
        existingUser.setSecondSurname(user.getSecondSurname());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        // Busca el rol y asigna al usuario
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        existingUser.setRole(role);

        existingUser = userRepository.save(existingUser); // Guarda el usuario actualizado
        return convertToDTO(existingUser); // Convierte a DTO antes de retornar
    }

    // Elimina un usuario y sus citas
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false; // Retorna false si el usuario no existe
        }
        appointmentClientRest.deleteAppointmentByClientId(id); // Elimina citas asociadas al usuario
        userRepository.deleteById(id); // Elimina el usuario
        return true; // Retorna true si la eliminación fue exitosa
    }

    // Convierte un usuario a UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setFirstSurname(user.getFirstSurname());
        userDTO.setSecondSurname(user.getSecondSurname());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRoleId(user.getRole().getId());
        return userDTO; // Retorna el DTO convertido
    }

    // Busca un usuario por su email
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Busca un usuario por su número de teléfono
    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    // Busca usuarios por su ID de rol
    @Override
    public List<User> findByRoleId(Long roleId) {
        return userRepository.findByRoleId(roleId);
    }

    // Valida la contraseña según el patrón definido
    private void validatePassword(String password) {
        if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            throw new InvalidInputException("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.", HttpStatus.BAD_REQUEST);
        }
    }

    // Valida el número de teléfono según el patrón definido
    private void validatePhoneNumber(String phoneNumber) {
        if (!Pattern.matches(PHONE_NUMBER_PATTERN, phoneNumber)) {
            throw new InvalidInputException("Invalid phone number format for Spain.", HttpStatus.BAD_REQUEST);
        }
    }
}
