package com.sinaukoding.tugas_akhir.library_management_system.model.enums;

import lombok.Getter;

@Getter
public enum StatusBuku {
    TERSEDIA("Tersedia"),
    DIPINJAM("Dipinjam"),
    HILANG("Hilang");

    private final String label;

    StatusBuku(String label){
        this.label = label;
    }
}
