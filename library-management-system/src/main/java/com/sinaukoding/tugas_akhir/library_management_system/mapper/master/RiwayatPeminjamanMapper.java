package com.sinaukoding.tugas_akhir.library_management_system.mapper.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.master.RiwayatPeminjaman;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.RiwayatPeminjamanRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.BukuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RiwayatPeminjamanMapper {

    private final BukuRepository bukuRepository;

    public RiwayatPeminjaman requestToEntity(RiwayatPeminjamanRequestRecord request){
        RiwayatPeminjaman riwayatPeminjaman = RiwayatPeminjaman.builder()
                .idPeminjamBuku(request.idPeminjam())
                .idPemberiBuku(request.idPemberi())
                .build();

        if (request.idPenerima() != null){
            riwayatPeminjaman.setIdPenerimaBuku(request.idPenerima());
        }

        var buku = bukuRepository.findById(request.idBuku()).orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));
        riwayatPeminjaman.setBuku(buku);

        if (request.tanggalKembali() != null){
            riwayatPeminjaman.setTanggalKembali(request.tanggalKembali());
        }

        return riwayatPeminjaman;
    }
}
