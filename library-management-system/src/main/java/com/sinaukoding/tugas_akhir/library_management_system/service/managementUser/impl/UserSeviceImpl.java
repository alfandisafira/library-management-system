package com.sinaukoding.tugas_akhir.library_management_system.service.managementUser.impl;

import com.sinaukoding.tugas_akhir.library_management_system.mapper.managementUser.UserMapper;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.UserFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.managementUser.UserRepository;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.ValidatorService;
import com.sinaukoding.tugas_akhir.library_management_system.service.managementUser.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSeviceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidatorService validatorService;
    private final UserMapper userMapper;

    @Override
    public void add(UserRequestRecord request) {
        // validator mandatory
        validatorService.validator(request);

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
    public void edit(UserRequestRecord request) {

    }

    @Override
    public Page<SimpleMap> findAll(UserFilterRecord filerRequest, Pageable pageable) {
        return null;
    }

    @Override
    public SimpleMap findById(String id) {
        return null;
    }

}
