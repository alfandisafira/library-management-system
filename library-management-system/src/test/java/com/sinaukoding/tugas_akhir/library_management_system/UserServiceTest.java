package com.sinaukoding.tugas_akhir.library_management_system;

import com.sinaukoding.tugas_akhir.library_management_system.entity.managementUser.User;
import com.sinaukoding.tugas_akhir.library_management_system.mapper.managementUser.UserMapper;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Role;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Status;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.TipeIdentitas;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.UserRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.managementUser.UserRepository;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.ValidatorService;
import com.sinaukoding.tugas_akhir.library_management_system.service.managementUser.impl.UserSeviceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidatorService validatorService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserSeviceImpl userService;

    @Test
    void testAddUser_Success(){
        var request = new UserRequestRecord(null, "admin123", "admin123@gmail.com", "admin123",
                "Handaya", TipeIdentitas.NIK, "123", Status.AKTIF, Role.ADMIN);

        var userEntity = new User();
        when(userMapper.requestToEntity(request)).thenReturn(userEntity);

        // when
        userService.add(request);

        // then
        verify(validatorService, times(1)).validator(request);
        verify(userRepository, times(1)).save(userEntity);
    }
}
