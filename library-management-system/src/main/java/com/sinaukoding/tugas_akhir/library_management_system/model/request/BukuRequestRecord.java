package com.sinaukoding.tugas_akhir.library_management_system.model.request;

import com.sinaukoding.tugas_akhir.library_management_system.model.enums.StatusBuku;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BukuRequestRecord(String id,
                                @NotBlank(message = "Id Judul Buku tidak boleh kosong") String judulBukuId,
                                @NotNull(message = "Status Buku tidak boleh kosong")StatusBuku statusBuku) {
}
