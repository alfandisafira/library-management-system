package com.sinaukoding.tugas_akhir.library_management_system.mapper.managementUser;

import com.sinaukoding.tugas_akhir.library_management_system.entity.managementUser.User;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User requestToEntity(UserRequestRecord request){

        return User.builder()
                .username(request.username().toLowerCase())
                .email(request.email().toLowerCase())
                .password(passwordEncoder.encode(request.password()))
                .nama(request.nama().toLowerCase())
                .tipeIdentitas(request.tipeIdentitas())
                .nomorIdentitas(request.nomorIdentitas())
                .role(request.role())
                .build();
    }
}
