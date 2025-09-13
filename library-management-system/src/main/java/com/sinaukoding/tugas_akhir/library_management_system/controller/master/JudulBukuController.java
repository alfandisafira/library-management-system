package com.sinaukoding.tugas_akhir.library_management_system.controller.master;

import com.sinaukoding.tugas_akhir.library_management_system.model.filter.JudulBukuFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.JudulBukuRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.response.BaseResponse;
import com.sinaukoding.tugas_akhir.library_management_system.service.master.JudulBukuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("judul-buku")
@RequiredArgsConstructor
public class JudulBukuController {

    private final JudulBukuService judulBukuService;

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> save(@RequestBody JudulBukuRequestRecord request){
        judulBukuService.add(request);

        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PostMapping("edit")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> edit(@RequestBody JudulBukuRequestRecord request){
        judulBukuService.edit(request);

        return BaseResponse.ok("Data berhasil diubah", null);
    }

    @PostMapping("find-all")
    public BaseResponse<?> findAll(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                   @RequestBody JudulBukuFilterRecord filterRequest){
        return BaseResponse.ok(null, judulBukuService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, judulBukuService.findById(id));
    }

}
