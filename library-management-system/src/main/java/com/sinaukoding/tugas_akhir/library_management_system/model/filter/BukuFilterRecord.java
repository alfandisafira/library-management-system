package com.sinaukoding.tugas_akhir.library_management_system.model.filter;

import com.sinaukoding.tugas_akhir.library_management_system.model.enums.StatusBuku;

public record BukuFilterRecord(String judulBukuId,
                               StatusBuku statusBuku) {
}
