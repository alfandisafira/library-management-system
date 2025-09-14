API Library Management System

Alur Menambahkan Data Buku
1. Membuat user atau Akun dengan role ADMIN pada endpoint `/user/save`
2. Pertama-pertama untuk membuat user pada endpoint `/user/save`, perlu _uncomment_ pada line berikut di file SecurityConfig.Java
```
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/user/save").permitAll() -> UNCOMMENT INI
                        .requestMatchers("/auth/login/**", "/auth/register/**").permitAll()
                        .requestMatchers("/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
                                "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPointConfig)
                        .accessDeniedHandler(accessDeniedConfig)
                )
                .addFilterBefore(jwtAuthenticationConfig, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
```
3. Setelah membuat akun berhasil, kembali uncomment line tersebut
4. Kemudian login dengan akun tersebut
5. Tambahkan data Judul Buku pada endpoint `/judul-buku/save` sesuai keinginan
6. Kemudian tambahkan data Buku pada endpoint `/buku/save` sesuai dengan Judul Buku yang terdaftar

Alur Proses Peminjaman Buku
1. Login terlebih dahulu sebagai Role ADMIN
2. Mendaftarkan akun dengan Role PENGUNJUNG pada endpoint `/auth/register`
3. Setelah itu aktivasi akun oleh user dengan Role ADMIN sesuai dengan akun yang teregistrasi pada endpoint `/user/activation`
4. Kemudian pilih buku yang ingin dipinjam dengan melihat daftar buku pada endpoint `/buku/find-all`
5. User dengan Role ADMIN akan membuat Riwayat Peminjaman sesuai dengan buku yang akan dipinjam oleh user dengan Role PENGUNJUNG pada endpoint `/riwayat-peminjaman/pinjam`

Alur Proses Pengembalian Buku
1. Login terlebih dahulu sebagai Role ADMIN
2. Lihat daftar Riwayat Pinjaman dan cari data yang ingin dikembalikan bukunya pada endpoint `/riwayat-peminjaman/find-all`
3. Lalu kirim id pada _request body_ pada endpoint `/riwayat-peminjaman/kembalikan`
