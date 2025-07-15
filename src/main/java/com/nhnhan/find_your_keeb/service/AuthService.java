package com.nhnhan.find_your_keeb.service;

import com.nhnhan.find_your_keeb.dto.AuthRequest;
import com.nhnhan.find_your_keeb.dto.AuthResponse;
import com.nhnhan.find_your_keeb.dto.RegisterRequest;
import com.nhnhan.find_your_keeb.dto.UserProfileResponse;
import com.nhnhan.find_your_keeb.dto.UserProfileUpdateRequest;
import com.nhnhan.find_your_keeb.entity.Cart;
import com.nhnhan.find_your_keeb.entity.Role;
import com.nhnhan.find_your_keeb.entity.User;
import com.nhnhan.find_your_keeb.repository.CartRepository;
import com.nhnhan.find_your_keeb.repository.UserRepository;
import com.nhnhan.find_your_keeb.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        // Create cart for the user
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

        String token = jwtUtil.generateToken(user);
        
        return new AuthResponse(token, "Bearer", savedUser.getId(), 
                savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole().name());
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user);
        
        return new AuthResponse(token, "Bearer", user.getId(), 
                user.getUsername(), user.getEmail(), user.getRole().name());
    }

    public UserProfileResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String displayName = (user.getFirstName() != null ? user.getFirstName() : "") +
                (user.getLastName() != null ? (" " + user.getLastName()) : "");
        String gender = user.getGender();
        String dateOfBirth = user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : null;
        String address = user.getAddress();
        String phoneNumber = user.getPhoneNumber();
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                displayName.trim(),
                gender,
                dateOfBirth,
                address,
                phoneNumber
        );
    }

    public UserProfileResponse updateUserProfile(String username, UserProfileUpdateRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (request.getDisplayName() != null) {
            String[] parts = request.getDisplayName().trim().split(" ", 2);
            user.setFirstName(parts[0]);
            user.setLastName(parts.length > 1 ? parts[1] : "");
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(java.time.LocalDate.parse(request.getDateOfBirth()));
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        userRepository.save(user);
        return getUserProfile(username);
    }
} 