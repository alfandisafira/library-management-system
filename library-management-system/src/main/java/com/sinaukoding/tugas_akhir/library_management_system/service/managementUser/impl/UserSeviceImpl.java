package com.sinaukoding.tugas_akhir.library_management_system.service.managementUser.impl;

import com.sinaukoding.tugas_akhir.library_management_system.builder.CustomBuilder;
import com.sinaukoding.tugas_akhir.library_management_system.entity.managementUser.User;
import com.sinaukoding.tugas_akhir.library_management_system.mapper.managementUser.UserMapper;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.AppPage;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Status;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.UserFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.managementUser.UserRepository;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.ValidatorService;
import com.sinaukoding.tugas_akhir.library_management_system.service.managementUser.UserService;
import com.sinaukoding.tugas_akhir.library_management_system.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

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
        // validator mandatory
        validatorService.validator(request);

        // validasi id
        if (request.id() == null || request.id().isEmpty()){
            throw new RuntimeException("id tidak boleh kosong");
        }

        var userExisting = userRepository.findById(request.id()).orElseThrow(
                () -> new RuntimeException("Data User tidak ditemukan")
        );

        // validasi data existing
        if (userRepository.existsByEmailAndIdNot(request.email().toLowerCase(), request.id())){
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }

        if (userRepository.existsByUsernameAndIdNot(request.username().toLowerCase(), request.id())){
            throw new RuntimeException("Username [" + request.username() + "] sudah digunakan");
        }

        if (userRepository.existsByNomorIdentitasAndIdNot(request.nomorIdentitas().toLowerCase(), request.id())){
            throw new RuntimeException("Nomor Identitas [" + request.nomorIdentitas() + "] sudah digunakan");
        }

        var user = userMapper.requestToEntity(request);
        user.setId(userExisting.getId());
        userRepository.save(user);
    }

    @Override
    public Page<SimpleMap> findAll(UserFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<User> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("username", filterRequest.username(), builder);
        FilterUtil.builderConditionNotBlankLike("email", filterRequest.email(), builder);
        FilterUtil.builderConditionNotBlankLike("nama", filterRequest.nama(), builder);
        FilterUtil.builderConditionNotNullEqual("tipeIdentitas", filterRequest.tipeIdentitas(), builder);
        FilterUtil.builderConditionNotBlankLike("nomorIdentitas", filterRequest.nomorIdentitas(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);
        FilterUtil.builderConditionNotNullEqual("role", filterRequest.role(), builder);

        Page<User> listUser = userRepository.findAll(builder.build(), pageable);

        List<SimpleMap> listData = listUser.stream().map(user -> {
            SimpleMap data = new SimpleMap();

            data.put("id", user.getId());
            data.put("username", user.getUsername());
            data.put("email", user.getEmail());
            data.put("nama", user.getNama());
            data.put("tipeIdentitas", user.getTipeIdentitas());
            data.put("nomorIdentitas", user.getNomorIdentitas());
            data.put("status", user.getStatus());
            data.put("role", user.getRole());

            return data;
        }).toList();

        return AppPage.create(listData, pageable, listUser.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var user = userRepository.findById(id).orElseThrow(() ->  new RuntimeException("Data user tidak ditemukan"));
        SimpleMap data = new SimpleMap();

        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("nama", user.getNama());
        data.put("tipeIdentitas", user.getTipeIdentitas());
        data.put("nomorIdentitas", user.getNomorIdentitas());
        data.put("status", user.getStatus());
        data.put("role", user.getRole());

        return data;
    }

    @Override
    public void activation(String username){
        if (username == null || username.isEmpty()){
            throw new RuntimeException("Username tidak boleh kosong");
        }

        var user = userRepository.findUserByUsername(username).orElseThrow(() ->  new RuntimeException("Username tidak terdaftar"));

        if (user.getStatus().toString().equalsIgnoreCase("AKTIF")){
            throw new RuntimeException("Data user sudah aktif");
        }

        user.setStatus(Status.AKTIF);
        userRepository.save(user);
    }

}
