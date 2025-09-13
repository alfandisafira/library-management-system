package com.sinaukoding.tugas_akhir.library_management_system.mapper.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.master.Buku;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.BukuRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.JudulBukuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BukuMapper {

    private final JudulBukuRepository  judulBukuRepository;

    public Buku requestToEntity(BukuRequestRecord request){
        Buku buku = Buku.builder()
                .statusBuku(request.statusBuku())
                .build();

        var judulBuku = judulBukuRepository.findById(request.judulBukuId()).orElseThrow(() -> new RuntimeException("Judul Buku tidak ditemukan"));

        buku.setJudulBuku(judulBuku);

        return buku;
    }
}
