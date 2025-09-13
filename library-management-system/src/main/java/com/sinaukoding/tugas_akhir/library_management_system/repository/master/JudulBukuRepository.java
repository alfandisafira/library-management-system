package com.sinaukoding.tugas_akhir.library_management_system.repository.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.master.JudulBuku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JudulBukuRepository extends JpaRepository<JudulBuku, String>, JpaSpecificationExecutor<JudulBuku> {

    public boolean existsByJudulAndPenulis(String judul, String penulis);

    public boolean existsByJudulAndPenulisAndIdNot(String judul, String penulis, String id);
}
