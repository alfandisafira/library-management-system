package com.sinaukoding.tugas_akhir.library_management_system.service.master;

import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.BukuFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.BukuRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BukuService {
    void add(BukuRequestRecord request);

    void edit(BukuRequestRecord request);

    Page<SimpleMap> findAll(BukuFilterRecord filterRequest, Pageable pageable);

    SimpleMap findyById(String id);
}
