package com.sinaukoding.tugas_akhir.library_management_system.entity.managementUser;

import com.sinaukoding.tugas_akhir.library_management_system.entity.app.BaseEntity;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Role;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Status;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.TipeIdentitas;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_user", indexes = {
        @Index(name = "idx_user_created_date", columnList = "createdDate"),
        @Index(name = "idx_user_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_user_username", columnList = "username"),
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_nama", columnList = "nama"),
        @Index(name = "idx_user_tipe_identitas", columnList = "tipeIdentitas"),
        @Index(name = "idx_user_nomor_identitas", columnList = "nomorIdentitas"),
        @Index(name = "idx_user_status", columnList = "status"),
        @Index(name = "idx_user_role", columnList = "role")
})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Size(min = 6, max = 16, message = "Username harus minimal 3 karakter dan maksimal 16 karakter")
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "Email tidak valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nama;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipeIdentitas tipeIdentitas;

    @Column(nullable = false)
    private String nomorIdentitas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String token;
    private LocalDateTime expiredTokenAt;

}
