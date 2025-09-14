package com.sinaukoding.tugas_akhir.library_management_system.service.master;

import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.RiwayatPeminjamanFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.RiwayatPeminjamanRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RiwayatPeminjamanService {
    void pinjamBuku(RiwayatPeminjamanRequestRecord request);

    void kembalikanBuku(RiwayatPeminjamanRequestRecord request);

    Page<SimpleMap> findAll(RiwayatPeminjamanFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
}
