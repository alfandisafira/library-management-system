package com.sinaukoding.tugas_akhir.library_management_system.controller.app;

import com.sinaukoding.tugas_akhir.library_management_system.config.UserLoggedInConfig;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.LoginRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.response.BaseResponse;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public BaseResponse<?> register(@RequestBody UserRequestRecord request){
        authService.register(request);
        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody LoginRequestRecord request){
        return BaseResponse.ok(null, authService.login(request));
    }

    @GetMapping("logout")
    public BaseResponse<?> logout(@AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig){
        var userLoggedIn = userLoggedInConfig.getUser();
        authService.logout(userLoggedIn);
        return BaseResponse.ok("Berhasil logout", null);
    }

}