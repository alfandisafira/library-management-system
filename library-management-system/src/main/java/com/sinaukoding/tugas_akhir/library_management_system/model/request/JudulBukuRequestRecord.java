package com.sinaukoding.tugas_akhir.library_management_system.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record JudulBukuRequestRecord(String id,
                                     @NotBlank(message = "Judul Buku tidak boleh kosong") String judul,
                                     @NotBlank(message = "Penulis tidak boleh kosong") String penulis,
                                     Integer stokTersedia,
                                     Set<String> listIdBuku) {
}
