package com.sinaukoding.tugas_akhir.library_management_system.repository.managementUser;

import com.sinaukoding.tugas_akhir.library_management_system.entity.managementUser.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNomorIdentitas(String nomorIdentitas);

    Optional<User> findUserByUsername(String username);
}
