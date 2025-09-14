package com.sinaukoding.tugas_akhir.library_management_system.model.filter;

import java.time.LocalDateTime;

public record RiwayatPeminjamanFilterRecord(String usernamePeminjam,
                                            String usernameAdmin,
                                            String idBuku,
                                            LocalDateTime tanggalKembali) {
}
