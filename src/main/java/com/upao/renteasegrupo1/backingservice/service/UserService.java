package com.upao.renteasegrupo1.backingservice.service;

import com.upao.renteasegrupo1.backingservice.exception.ResourceNotFoundException;
import com.upao.renteasegrupo1.backingservice.mapper.UserMapper;
import com.upao.renteasegrupo1.backingservice.model.dto.UserRequestDTO;
import com.upao.renteasegrupo1.backingservice.model.dto.UserResponseDTO;
import com.upao.renteasegrupo1.backingservice.model.entity.User;
import com.upao.renteasegrupo1.backingservice.repository.UserRepository;
import com.upao.renteasegrupo1.backingservice.security.JwtService;
import com.upao.renteasegrupo1.backingservice.security.LoginRequest;
import com.upao.renteasegrupo1.backingservice.security.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.convertToListDTO(users);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con el ID: " + id));
        return userMapper.convertToDTO(user);
    }


    public TokenResponse createUser(UserRequestDTO userRequestDTO) {
        validateUserRequest(userRequestDTO);
        User user = userMapper.convertToEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        String token = jwtService.getToken(user, user);
        return TokenResponse.builder()
                .token(token)
                .build();
    }

    public void validateUserRequest(UserRequestDTO userRequestDTO) {
        if (dniExists(userRequestDTO.getDni())) {
            throw new IllegalArgumentException("Numero de DNI ya ha sido registrado");
        }
        if (cellNumberExists(userRequestDTO.getTelefono())) {
            throw new IllegalArgumentException("Numero de telefono ya ha sido registrado");
        }
        if (usernameExists(userRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Nombre de Usuario existente, por favor usar otro");
        }
    }

    public boolean dniExists(String dni) {
        return userRepository.existsByDni(dni);
    }

    public boolean cellNumberExists(String telefono) {
        return userRepository.existsByTelefono(telefono);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }


    // Revisar (UserDetails)
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("Nombre de usuario no existe.")
        );
        if (!passwordMatches(request.getUsername(), request.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }
        String token = jwtService.getToken(user, user);
        return TokenResponse.builder()
                .token(token)
                .build();
    }

    public boolean passwordMatches(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuario no encontrado")
        );
        userRepository.delete(user);
    }
}
