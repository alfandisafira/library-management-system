package com.sinaukoding.tugas_akhir.library_management_system.service.app;

import com.sinaukoding.tugas_akhir.library_management_system.entity.managementUser.User;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.LoginRequestRecord;

public interface AuthService {

    SimpleMap login(LoginRequestRecord request);

    void logout(User useLoggedIn);

}
