package com.sinaukoding.tugas_akhir.library_management_system.entity.master;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
        @Index(name = "idx_riwayat_peminjaman_username_peminjam", columnList = "username_peminjam"),
        @Index(name = "idx_riwayat_peminjaman_username_admin", columnList = "username_admin"),
        @Index(name = "idx_riwayat_peminjaman_id_buku", columnList = "id_buku")
})
public class RiwayatPeminjaman extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String usernamePeminjam;

    @Column(nullable = false)
    private String usernameAdmin;

    @ManyToOne
    @JoinColumn(name = "id_buku", nullable = false)
    @JsonBackReference
    @JsonIgnore
    private Buku buku;

    @Column
    private LocalDateTime tanggalKembali;
}
