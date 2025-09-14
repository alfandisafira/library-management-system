package com.sinaukoding.tugas_akhir.library_management_system.mapper.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.master.Buku;
import com.sinaukoding.tugas_akhir.library_management_system.entity.master.JudulBuku;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.JudulBukuRequestRecord;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JudulBukuMapper {

    public JudulBuku requestToEntity(JudulBukuRequestRecord request){
        JudulBuku judulBuku = JudulBuku.builder()
                .judul(request.judul().toUpperCase())
                .stokTersedia(0)
                .penulis(request.penulis().toUpperCase())
                .build();

        if (request.stokTersedia() != null){
            judulBuku.setStokTersedia(request.stokTersedia());
        }

        if (request.listIdBuku() != null){
            judulBuku.setListBuku(request.listIdBuku().stream()
                    .map(id -> Buku.builder()
                            .id(id)
                            .judulBuku(judulBuku)
                            .build())
                    .collect(Collectors.toList()));
        }

        return judulBuku;
    }
}
