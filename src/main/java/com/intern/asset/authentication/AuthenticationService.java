package com.intern.asset.authentication;

import com.intern.asset.model.UserEntity;
import com.intern.asset.model.UserRole;
import com.intern.asset.repository.UserRepository;
import com.intern.asset.security.JwtHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtHandler jwtHandler;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JwtHandler jwtHandler,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtHandler = jwtHandler;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void register(String username, String password, UserRole role) throws UserAlreadyExistException {
        // 检查用户名是否已存在（防止重复注册）
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException("Username already exists");
        }

        // 如果用户名不存在，使用 PasswordEncoder 对明文密码进行加密存储
        UserEntity userEntity = new UserEntity(null, username, passwordEncoder.encode(password), role);
        userRepository.save(userEntity);
    }

    // 生成一个JWT(String)，返回给前端
    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); // 创建认证令牌
        return jwtHandler.generateToken(username);
    }
}
