package com.sinaukoding.tugas_akhir.library_management_system.model.enums;

import lombok.Getter;

@Getter
public enum Status {

    BELUM_AKTIF("Belum Aktif"),
    AKTIF("Aktif"),
    TIDAK_AKTIF("Tidak Aktif");

    private final String label;

    Status(String label) {
        this.label = label;
    }

}
