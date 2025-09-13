package com.sinaukoding.tugas_akhir.library_management_system.service.master.impl;

import com.sinaukoding.tugas_akhir.library_management_system.builder.CustomBuilder;
import com.sinaukoding.tugas_akhir.library_management_system.entity.master.JudulBuku;
import com.sinaukoding.tugas_akhir.library_management_system.mapper.master.JudulBukuMapper;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.AppPage;
import com.sinaukoding.tugas_akhir.library_management_system.model.app.SimpleMap;
import com.sinaukoding.tugas_akhir.library_management_system.model.filter.JudulBukuFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.JudulBukuRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.repository.master.JudulBukuRepository;
import com.sinaukoding.tugas_akhir.library_management_system.service.app.ValidatorService;
import com.sinaukoding.tugas_akhir.library_management_system.service.master.JudulBukuService;
import com.sinaukoding.tugas_akhir.library_management_system.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JudulBukuServiceImpl implements JudulBukuService {

    private final JudulBukuRepository judulBukuRepository;
    private final ValidatorService validatorService;
    private final JudulBukuMapper judulBukuMapper;

    @Override
    public void add(JudulBukuRequestRecord request) {
        // validasi mandatory
        validatorService.validator(request);

        if (judulBukuRepository.existsByJudulAndPenulis(request.judul().toLowerCase(), request.penulis().toLowerCase())){
            throw new RuntimeException("Buku dengan judul ["+ request.judul() +"] dan penulis ["+ request.penulis()+"] sudah terdaftar");
        }

        var judulBuku = judulBukuMapper.requestToEntity(request);
        judulBukuRepository.save(judulBuku);
    }

    @Override
    public void edit(JudulBukuRequestRecord request) {
        // validasi mandatory
        validatorService.validator(request);

        // validasi id
        if (request.id() == null || request.id().isEmpty()){
            throw new RuntimeException("Id tidak boleh kosong");
        }

        if (judulBukuRepository.existsByJudulAndPenulisAndIdNot(request.judul().toUpperCase(), request.penulis().toUpperCase(), request.id())){
            throw new RuntimeException("Buku dengan judul ["+ request.judul() +"] dan penulis ["+ request.penulis()+"] sudah terdaftar");
        }

        var judulBukuExisting = judulBukuRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("Judul Buku tidak ditemukan"));

        var judulBuku = judulBukuMapper.requestToEntity(request);
        judulBuku.setId(judulBukuExisting.getId());
        judulBukuRepository.save(judulBuku);
    }

    @Override
    public Page<SimpleMap> findAll(JudulBukuFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<JudulBuku> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("judul", filterRequest.judul(), builder);
        FilterUtil.builderConditionNotBlankLike("penulis", filterRequest.penulis(), builder);

        Page<JudulBuku> listJudulBuku = judulBukuRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listJudulBuku.stream().map(judulBuku -> {
            SimpleMap data = new SimpleMap();

            data.put("id", judulBuku.getId());
            data.put("judul", judulBuku.getJudul());
            data.put("penulis", judulBuku.getPenulis());
            data.put("stokTersedia", judulBuku.getStokTersedia());

            return data;
        }).toList();

        return AppPage.create(listData, pageable, listJudulBuku.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var judulBuku = judulBukuRepository.findById(id).orElseThrow(() ->  new RuntimeException("Data user tidak ditemukan"));
        SimpleMap data = new SimpleMap();

        data.put("id", judulBuku.getId());
        data.put("judul", judulBuku.getJudul());
        data.put("penulis", judulBuku.getPenulis());
        data.put("stokTersedia", judulBuku.getStokTersedia());

        return data;
    }
}
