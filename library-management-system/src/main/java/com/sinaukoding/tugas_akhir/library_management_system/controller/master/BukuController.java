package com.sinaukoding.tugas_akhir.library_management_system.controller.master;

import com.sinaukoding.tugas_akhir.library_management_system.model.filter.BukuFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.BukuRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.response.BaseResponse;
import com.sinaukoding.tugas_akhir.library_management_system.service.master.BukuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("buku")
@RequiredArgsConstructor
public class BukuController {

    private final BukuService bukuService;

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> save(@RequestBody BukuRequestRecord request){
        bukuService.add(request);

        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PostMapping("edit")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> edit(@RequestBody BukuRequestRecord request){
        bukuService.edit(request);

        return BaseResponse.ok("Data berhasil diubah", null);
    }

    @PostMapping("find-all")
    public BaseResponse<?> finByAll(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                    @RequestBody BukuFilterRecord filterRequest){
        return BaseResponse.ok(null, bukuService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, bukuService.findyById(id));
    }
}
