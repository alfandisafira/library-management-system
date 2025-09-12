package com.sinaukoding.tugas_akhir.library_management_system.model.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("Admin"),
    PENGUNJUNG("Pengunjung");

    private final String label;

    Role(String label){
        this.label = label;
    }
}
