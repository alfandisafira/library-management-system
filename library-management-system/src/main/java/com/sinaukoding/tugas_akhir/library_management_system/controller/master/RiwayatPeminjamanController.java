package com.sinaukoding.tugas_akhir.library_management_system.controller.master;

import com.sinaukoding.tugas_akhir.library_management_system.model.filter.RiwayatPeminjamanFilterRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.request.RiwayatPeminjamanRequestRecord;
import com.sinaukoding.tugas_akhir.library_management_system.model.response.BaseResponse;
import com.sinaukoding.tugas_akhir.library_management_system.service.master.RiwayatPeminjamanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("riwayat-peminjaman")
@RequiredArgsConstructor
public class RiwayatPeminjamanController {

    private final RiwayatPeminjamanService riwayatPeminjamanService;

    @PostMapping("pinjam")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> pinjamBuku(@RequestBody RiwayatPeminjamanRequestRecord request){
        riwayatPeminjamanService.pinjamBuku(request);

        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PostMapping("kembalikan")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> kembalikanBuku(@RequestBody RiwayatPeminjamanRequestRecord request){
        riwayatPeminjamanService.kembalikanBuku(request);

        return BaseResponse.ok("Data berhasil diubah", null);
    }

    @PostMapping("find-all")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<?> findAll(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                   @RequestBody RiwayatPeminjamanFilterRecord filterRequest){
        return BaseResponse.ok(null, riwayatPeminjamanService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, riwayatPeminjamanService.findById(id));
    }
}
