package com.sinaukoding.tugas_akhir.library_management_system.service.master;

import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.JudulBukuFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.JudulBukuRequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JudulBukuService {
    void add(JudulBukuRequestRecord request);

    void edit(JudulBukuRequestRecord request);

    Page<SimpleMap> findAll(JudulBukuFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
}
