package com.sinaukoding.tugas_akhir.library_management_system.entity.master;

import com.sinaukoding.tugas_akhir.library_management_system.entity.app.BaseEntity;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.StatusBuku;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_buku", indexes = {
        @Index(name = "idx_buku_created_date", columnList = "createdDate"),
        @Index(name = "idx_buku_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_buku_judul_buku_id", columnList = "judul_buku_id"),
        @Index(name = "idx_buku_status_" +
                "buku", columnList = "status_buku")
})
public class Buku extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judul_buku_id", nullable = false)
    private JudulBuku judulBuku;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusBuku statusBuku;
}
