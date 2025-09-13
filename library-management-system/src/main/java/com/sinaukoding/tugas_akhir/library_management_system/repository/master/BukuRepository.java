package com.sinaukoding.tugas_akhir.library_management_system.repository.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.master.Buku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BukuRepository extends JpaRepository<Buku, String>, JpaSpecificationExecutor<Buku> {
}
