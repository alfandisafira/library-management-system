package com.sinaukoding.tugas_akhir.library_management_system.model.enums;

import lombok.Getter;

@Getter
public enum TipeIdentitas {

    NIK("NIK"),
    SIM("SIM"),
    KARTU_PELAJAR("Kartu Pelajar"),
    PASPOR("Paspor");

    private final String label;

    TipeIdentitas(String label){
        this.label = label;
    }
}
