package com.sinaukoding.tugas_akhir.library_management_system.service.app.impl;

import com.sinaukoding.tugas_akhir.library_management_system.entity.managementUser.User;
import com.sinaukoding.tugas_akhir.library_management_system.mapper.managementUser.UserMapper;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.LoginRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.managementUser.UserRepository;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.AuthService;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.ValidatorService;
import com.sinaukoding.tugas_akhir.library_management_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ValidatorService validatorService;
    private final UserMapper userMapper;

    @Override
    public void register(UserRequestRecord request) {
        validatorService.validator(request);

        if (!request.status().toString().equalsIgnoreCase("belum_aktif")){
            throw new RuntimeException("Status harus 'BELUM_AKTIF'");
        }

        if (!request.role().toString().equalsIgnoreCase("pengunjung")){
            throw new RuntimeException("Role harus 'PENGUNJUNG'");
        }

        if (userRepository.existsByUsername(request.username().toLowerCase())){
            throw new RuntimeException("Username [" + request.username() + "] sudah digunakan");
        }

        if (userRepository.existsByEmail(request.email().toLowerCase())){
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }

        if (userRepository.existsByNomorIdentitas(request.nomorIdentitas())){
            throw new RuntimeException("Nomor Identitas [" + request.nomorIdentitas() + "] sudah digunakan");
        }

        var user = userMapper.requestToEntity(request);

        userRepository.save(user);
    }

    @Override
    public SimpleMap login(LoginRequestRecord request){

        validatorService.validator(request);

        var user = userRepository.findUserByUsername(request.username().toLowerCase()).orElseThrow(() -> new RuntimeException("Username atau Password salah"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Password tidak sesuai");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        user.setToken(token);
        user.setExpiredTokenAt(LocalDateTime.now().plusHours(1));

        userRepository.save(user);

        SimpleMap result = new SimpleMap();
        result.put("token", token);

        return result;
    }

    @Override
    public void logout(User userLoggedIn){
        userLoggedIn.setToken(null);
        userLoggedIn.setExpiredTokenAt(null);

        userRepository.save(userLoggedIn);
    }

}
