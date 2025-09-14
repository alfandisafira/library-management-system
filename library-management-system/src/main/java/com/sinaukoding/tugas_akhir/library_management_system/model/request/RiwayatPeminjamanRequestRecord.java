package com.sinaukoding.tugas_akhir.library_management_system.model.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RiwayatPeminjamanRequestRecord(String id,
                                             @NotBlank(message = "Id Peminjam tidak boleh kosong") String idPeminjam,
                                             @NotBlank(message = "Id Pemberi tidak boleh kosong") String idPemberi,
                                             String idPenerima,
                                             @NotBlank(message = "Id Buku tidak boleh kosong") String idBuku,
                                             LocalDateTime tanggalKembali) {
}
