package com.sinaukoding.tugas_akhir.library_management_system.service.master.impl;

import com.sinaukoding.tugas_akhir.library_management_system.builder.CustomBuilder;
import com.sinaukoding.tugas_akhir.library_management_system.entity.master.Buku;
import com.sinaukoding.tugas_akhir.library_management_system.mapper.master.BukuMapper;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.AppPage;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.BukuFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.BukuRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.BukuRepository;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.JudulBukuRepository;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.ValidatorService;
import com.sinaukoding.tugas_akhir.library_management_system.service.master.BukuService;
import com.sinaukoding.tugas_akhir.library_management_system.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BukuServiceImpl implements BukuService {

    private final BukuRepository bukuRepository;
    private final ValidatorService validatorService;
    private final BukuMapper bukuMapper;

    private final JudulBukuRepository judulBukuRepository;

    @Override
    public void add(BukuRequestRecord request) {
        // validasi mandatory
        validatorService.validator(request);

        var buku = bukuMapper.requestToEntity(request);
        bukuRepository.save(buku);

        var judulBukuExisting = judulBukuRepository.findById(request.judulBukuId()).orElseThrow(() -> new RuntimeException("Judul Buku tidak ditemukan"));
        judulBukuExisting.setStokTersedia(judulBukuExisting.getStokTersedia() + 1);
        judulBukuRepository.save(judulBukuExisting);
    }

    @Override
    public void edit(BukuRequestRecord request) {
        // validasi mandatory
        validatorService.validator(request);

        // validasi
        if (request.id() == null || request.id().isEmpty()){
            throw new RuntimeException("Id tidak boleh kosong");
        }

        var bukuExisting = bukuRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        String judulBukuIdOld = bukuExisting.getJudulBuku().getId();

        var buku = bukuMapper.requestToEntity(request);
        buku.setId(bukuExisting.getId());
        bukuRepository.save(buku);

        if (judulBukuIdOld != request.judulBukuId()){
            // kurangi stok yang lama
            var judulBukuOld = judulBukuRepository.findById(judulBukuIdOld).orElseThrow(() -> new RuntimeException("Judul Buku lama tidak ditemukan"));

            Integer stokBukuTersediaOld = judulBukuOld.getStokTersedia() - 1;

            judulBukuOld.setStokTersedia(stokBukuTersediaOld);
            judulBukuRepository.save(judulBukuOld);


            // tambahi stok yang baru
            var judulBukuNew = judulBukuRepository.findById(request.judulBukuId()).orElseThrow(() -> new RuntimeException("Judul Buku baru tidak ditemukan"));

            Integer stokBukuTersediaNew = judulBukuNew.getStokTersedia() + 1;

            judulBukuNew.setStokTersedia(stokBukuTersediaNew);
            judulBukuRepository.save(judulBukuNew);
        }

    }

    @Override
    public Page<SimpleMap> findAll(BukuFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Buku> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("judulBukuId", filterRequest.judulBukuId(), builder);
        FilterUtil.builderConditionNotNullEqual("statusBuku", filterRequest.statusBuku(), builder);

        Page<Buku> listBuku = bukuRepository.findAll(builder.build(), pageable);

        List<SimpleMap> listData = listBuku.stream().map(buku -> {
            SimpleMap data = new SimpleMap();

            data.put("id", buku.getId());
            data.put("statusBuku", buku.getStatusBuku());
            data.put("createdDate", buku.getCreatedDate());
            data.put("modifiedDate", buku.getModifiedDate());
            data.put("judulBuku", buku.getJudulBuku());

            var listBukuDipinjam = buku.getListBukuDipinjam().stream().toList();

            data.put("listRiwayatPinjaman", listBukuDipinjam);

            return data;
        }).toList();

        return AppPage.create(listData, pageable, listBuku.getTotalElements());
    }

    @Override
    public SimpleMap findyById(String id) {
        var buku = bukuRepository.findById(id).orElseThrow(() ->  new RuntimeException("Data Buku tidak ditemukan"));

        SimpleMap data = new SimpleMap();

        data.put("id", buku.getId());
        data.put("statusBuku", buku.getStatusBuku());
        data.put("createdDate", buku.getCreatedDate());
        data.put("modifiedDate", buku.getModifiedDate());
        data.put("judulBuku", buku.getJudulBuku());

        var listBukuDipinjam = buku.getListBukuDipinjam().stream().toList();

        data.put("listRiwayatPinjaman", listBukuDipinjam);

        return data;
    }
}
