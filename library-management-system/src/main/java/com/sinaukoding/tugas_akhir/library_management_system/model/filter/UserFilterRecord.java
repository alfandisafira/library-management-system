package com.sinaukoding.tugas_akhir.library_management_system.model.filter;

import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Role;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Status;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.TipeIdentitas;

public record UserFilterRecord(String username,
                               String email,
                               String nama,
                               TipeIdentitas tipeIdentitas,
                               String nomorIdentitas,
                               Status status,
                               Role role) {
}
