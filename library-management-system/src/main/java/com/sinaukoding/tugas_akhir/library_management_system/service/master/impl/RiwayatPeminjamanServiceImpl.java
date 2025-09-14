package com.sinaukoding.tugas_akhir.library_management_system.service.master.impl;

import com.sinaukoding.tugas_akhir.library_management_system.builder.CustomBuilder;
import com.sinaukoding.tugas_akhir.library_management_system.entity.master.RiwayatPeminjaman;
import com.sinaukoding.tugas_akhir.library_management_system.mapper.master.RiwayatPeminjamanMapper;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.AppPage;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Role;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.Status;
import com.sinaukoding.tugas_akhir.library_management_system.model.enums.StatusBuku;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.RiwayatPeminjamanFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.RiwayatPeminjamanRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.managementUser.UserRepository;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.BukuRepository;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.JudulBukuRepository;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.RiwayatPeminjamanRepository;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.ValidatorService;
import com.sinaukoding.tugas_akhir.library_management_system.service.master.RiwayatPeminjamanService;
import com.sinaukoding.tugas_akhir.library_management_system.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiwayatPeminjamanServiceImpl implements RiwayatPeminjamanService {

    private final RiwayatPeminjamanRepository riwayatPeminjamanRepository;
    private final ValidatorService validatorService;
    private final RiwayatPeminjamanMapper riwayatPeminjamanMapper;

    private final BukuRepository bukuRepository;
    private final UserRepository userRepository;
    private final JudulBukuRepository judulBukuRepository;

    @Override
    public void pinjamBuku(RiwayatPeminjamanRequestRecord request) {
        // validasi mandatory
        validatorService.validator(request);

        // validasi logic business
        var peminjam = userRepository.findUserByUsername(request.usernamePeminjam()).orElseThrow(() -> new RuntimeException("User peminjam tidak ditemukan"));

        if (peminjam.getStatus() != Status.AKTIF){
            throw new RuntimeException("User belum aktif, silahkan aktivasi terlebih dahulu");
        }

        if (peminjam.getRole() != Role.PENGUNJUNG){
            throw new RuntimeException("Role user peminjam bukan PENGUNJUNG");
        }

        var admin = userRepository.findUserByUsername(request.usernameAdmin()).orElseThrow(() -> new RuntimeException("User admin tidak ditemukan"));

        if (admin.getRole() != Role.ADMIN){
            throw new RuntimeException("Role user pemberi bukan ADMIN");
        }

        var buku = bukuRepository.findById(request.idBuku()).orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        if (buku.getStatusBuku() != StatusBuku.TERSEDIA){
            throw new RuntimeException("Buku tidak tersedia, silahkan pilih yang lain");
        }

        var riwayatPeminjaman = riwayatPeminjamanMapper.requestToEntity(request);
        riwayatPeminjamanRepository.save(riwayatPeminjaman);

        // change status buku
        buku.setStatusBuku(StatusBuku.DIPINJAM);
        bukuRepository.save(buku);

        // stok tersedia dikurangi
        var judulBuku = judulBukuRepository.findById(buku.getJudulBuku().getId()).orElseThrow(() -> new RuntimeException("Judul Buku tidak ditemukan"));
        Integer stokTersediaOldJudulBuku = judulBuku.getStokTersedia();

        judulBuku.setStokTersedia(stokTersediaOldJudulBuku - 1);
        judulBukuRepository.save(judulBuku);
    }

    @Override
    public void kembalikanBuku(RiwayatPeminjamanRequestRecord request) {
        // validasi id
        if (request.id() == null || request.id().isEmpty()){
            throw new RuntimeException("Id tidak boleh kosong");
        }

        RiwayatPeminjaman riwayatPeminjaman = riwayatPeminjamanRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("Data Riwayat Peminjaman tidak ditemukan"));

        var buku = bukuRepository.findById(riwayatPeminjaman.getBuku().getId()).orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        if (buku.getStatusBuku() == StatusBuku.TERSEDIA){
            throw new RuntimeException("Buku sudah kembali");
        }

        riwayatPeminjaman.setTanggalKembali(LocalDateTime.now());
        riwayatPeminjamanRepository.save(riwayatPeminjaman);

        // change status buku
        buku.setStatusBuku(StatusBuku.TERSEDIA);
        bukuRepository.save(buku);

        // tambah stok tersedia
        var judulBuku = judulBukuRepository.findById(buku.getJudulBuku().getId()).orElseThrow(() -> new RuntimeException("Judul Buku tidak ditemukan"));
        Integer stokTersediaOldJudulBuku = judulBuku.getStokTersedia();

        judulBuku.setStokTersedia(stokTersediaOldJudulBuku + 1);
        judulBukuRepository.save(judulBuku);

    }

    @Override
    public Page<SimpleMap> findAll(RiwayatPeminjamanFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<RiwayatPeminjaman> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("usernamePeminjam", filterRequest.usernamePeminjam(), builder);
        FilterUtil.builderConditionNotBlankLike("usernameAdmin", filterRequest.usernameAdmin(), builder);
        FilterUtil.builderConditionNotBlankLike("idBuku", filterRequest.idBuku(), builder);

        Page<RiwayatPeminjaman> listRiwayatPeminjaman = riwayatPeminjamanRepository.findAll(builder.build(), pageable);

        List<SimpleMap> listData = listRiwayatPeminjaman.stream().map(riwayatPeminjaman -> {
            SimpleMap data = new SimpleMap();

            data.put("id", riwayatPeminjaman.getId());
            data.put("usernamePeminjam", riwayatPeminjaman.getUsernameAdmin());
            data.put("usernameAdmin", riwayatPeminjaman.getUsernamePeminjam());
            data.put("createdDate", riwayatPeminjaman.getCreatedDate());
            data.put("modifiedDate", riwayatPeminjaman.getModifiedDate());
            data.put("buku", riwayatPeminjaman.getBuku());
            data.put("judul", riwayatPeminjaman.getBuku().getJudulBuku());

            if (riwayatPeminjaman.getTanggalKembali() != null){
                data.put("tanggalKembali", riwayatPeminjaman.getTanggalKembali());
            }

            return data;
        }).toList();

        return AppPage.create(listData, pageable, listRiwayatPeminjaman.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var riwayatPeminjaman = riwayatPeminjamanRepository.findById(id).orElseThrow(() ->  new RuntimeException("Data Riwayat Peminjaman tidak ditemukan"));

        SimpleMap data = new SimpleMap();

        data.put("id", riwayatPeminjaman.getId());
        data.put("usernamePeminjam", riwayatPeminjaman.getUsernameAdmin());
        data.put("usernameAdmin", riwayatPeminjaman.getUsernamePeminjam());
        data.put("createdDate", riwayatPeminjaman.getCreatedDate());
        data.put("modifiedDate", riwayatPeminjaman.getModifiedDate());
        data.put("buku", riwayatPeminjaman.getBuku());
        data.put("judul", riwayatPeminjaman.getBuku().getJudulBuku());

        if (riwayatPeminjaman.getTanggalKembali() != null){
            data.put("tanggalKembali", riwayatPeminjaman.getTanggalKembali());
        }

        return data;
    }
}
