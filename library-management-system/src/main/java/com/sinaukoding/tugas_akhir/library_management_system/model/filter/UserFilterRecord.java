package com.sinaukoding.tugas_akhir.library_management_system.model.filter;

import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Role;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.TipeIdentitas;

import java.time.LocalDate;

public record UserFilterRecord(String username,
                               String email,
                               String nama,
                               TipeIdentitas tipeIdentitas,
                               String nomorIdentitas,
                               Role role) {
}
