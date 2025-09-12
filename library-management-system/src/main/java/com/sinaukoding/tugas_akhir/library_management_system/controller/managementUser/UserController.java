package com.sinaukoding.tugas_akhir.library_management_system.controller.managementUser;

import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.response.BaseResponse;
import com.sinaukoding.tugas_akhir.library_management_system.service.managementUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("save")
    public BaseResponse<?> save(@RequestBody UserRequestRecord request){
        userService.add(request);

        return BaseResponse.ok("Data berhasil disimpan", null);
    }
}
