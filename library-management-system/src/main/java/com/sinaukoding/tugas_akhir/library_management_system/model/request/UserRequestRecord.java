package com.sinaukoding.tugas_akhir.library_management_system.model.request;

import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Role;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.TipeIdentitas;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestRecord(String id,
                                @NotBlank(message = "Username tidak boleh kosong")
                                @Size(min = 6, max = 16, message = "Username harus minimal 3 karakter dan maksimal 16 karakter")
                                String username,

                                @NotBlank(message = "Email tidak boleh kosong")
                                @Email(message = "Email tidak valid")
                                String email,

                                @NotBlank(message = "Password tidak boleh kosong") String password,
                                @NotBlank(message = "Nama tidak boleh kosong") String nama,
                                @NotNull(message = "Tipe Identitas tidak boleh kosong") TipeIdentitas tipeIdentitas,
                                @NotNull(message = "Nomor Identitas tidak boleh kosong") String nomorIdentitas,
                                @NotNull(message = "Role tidak boleh kosong") Role role) {
}
