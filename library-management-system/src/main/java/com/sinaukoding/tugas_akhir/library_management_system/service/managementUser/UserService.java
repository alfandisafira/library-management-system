package com.sinaukoding.tugas_akhir.library_management_system.service.managementUser;

import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.UserFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void add(UserRequestRecord request);

    void edit(UserRequestRecord request);

    Page<SimpleMap> findAll(UserFilterRecord filerRequest, Pageable pageable);

    SimpleMap findById(String id);

    void activation(String username);

}
