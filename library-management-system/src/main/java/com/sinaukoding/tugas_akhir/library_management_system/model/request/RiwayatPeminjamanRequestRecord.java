package com.sinaukoding.tugas_akhir.library_management_system.model.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RiwayatPeminjamanRequestRecord(String id,
                                             @NotBlank(message = "Username peminjam tidak boleh kosong") String usernamePeminjam,
                                             @NotBlank(message = "Username admin tidak boleh kosong") String usernameAdmin,
                                             @NotBlank(message = "Id Buku tidak boleh kosong") String idBuku,
                                             LocalDateTime tanggalKembali) {
}
