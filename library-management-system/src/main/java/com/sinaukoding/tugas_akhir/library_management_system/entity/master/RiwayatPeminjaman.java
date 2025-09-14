package com.sinaukoding.tugas_akhir.library_management_system.entity.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.app.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_riwayat_peminjaman", indexes = {
        @Index(name = "idx_riwayat_peminjaman_created_date", columnList = "createdDate"),
        @Index(name = "idx_riwayat_peminjaman_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_riwayat_peminjaman_id_peminjam_buku", columnList = "id_peminjam_buku"),
        @Index(name = "idx_riwayat_peminjaman_id_pemberi_buku", columnList = "id_pemberi_buku"),
        @Index(name = "idx_riwayat_peminjaman_id_penerima_buku", columnList = "id_penerima_buku"),
        @Index(name = "idx_riwayat_peminjaman_id_buku", columnList = "id_buku")
})
public class RiwayatPeminjaman extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String idPeminjamBuku;

    @Column(nullable = false)
    private String idPemberiBuku;

    @Column
    private String idPenerimaBuku;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_buku", nullable = false)
    private Buku buku;

    @Column
    private LocalDateTime tanggalKembali;
}
