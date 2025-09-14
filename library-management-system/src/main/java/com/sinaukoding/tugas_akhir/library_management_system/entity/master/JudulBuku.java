package com.sinaukoding.tugas_akhir.library_management_system.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sinaukoding.tugas_akhir.library_management_system.entity.app.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_judul_buku", indexes = {
        @Index(name = "idx_judul_buku_created_date", columnList = "createdDate"),
        @Index(name = "idx_judul_buku_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_judul_buku_judul", columnList = "judul"),
        @Index(name = "idx_judul_buku_penulis", columnList = "penulis")
})
public class JudulBuku extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String judul;

    @Column(nullable = false)
    private String penulis;

    @Min(value = 0, message = "Stok tidak boleh negatif")
    @Column(nullable = false)
    private Integer stokTersedia;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "judulBuku", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference
    @JsonIgnore
    private List<Buku> listBuku = new ArrayList<>();
}
