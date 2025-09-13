package com.sinaukoding.tugas_akhir.library_management_system.model.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestRecord(@NotBlank(message = "Username tidak boleh kosong") String username,
                                 @NotBlank(message = "Password tidak boleh kosong") String password) {
}
