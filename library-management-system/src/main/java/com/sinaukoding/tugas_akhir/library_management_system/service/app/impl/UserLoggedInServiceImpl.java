package com.sinaukoding.tugas_akhir.library_management_system.service.app.impl;

import com.sinaukoding.tugas_akhir.library_management_system.config.UserLoggedInConfig;
import com.sinaukoding.tugas_akhir.library_management_system.repository.managementUser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoggedInServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " tidak ditemukan"));

        return new UserLoggedInConfig(user);
    }
}
