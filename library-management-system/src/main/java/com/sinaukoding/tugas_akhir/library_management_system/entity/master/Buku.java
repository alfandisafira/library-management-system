package com.sinaukoding.tugas_akhir.library_management_system.entity.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.app.BaseEntity;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.StatusBuku;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_buku", indexes = {
        @Index(name = "idx_buku_created_date", columnList = "createdDate"),
        @Index(name = "idx_buku_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_buku_id_judul_buku", columnList = "id_judul_buku"),
        @Index(name = "idx_buku_status_buku", columnList = "status_buku")
})
public class Buku extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_judul_buku", nullable = false)
    private JudulBuku judulBuku;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusBuku statusBuku;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buku", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RiwayatPeminjaman> listBukuDipinjam = new HashSet<>();
}
