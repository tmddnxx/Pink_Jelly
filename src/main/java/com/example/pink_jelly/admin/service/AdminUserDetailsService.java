package com.example.pink_jelly.admin.service;

import com.example.pink_jelly.admin.AdminUser;
import com.example.pink_jelly.admin.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminUserDetailsService implements UserDetailsService {
    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {
        // 관리자 정보 조회
        log.info("adminUser...");
        AdminUser adminUser = adminUserMapper.findByAdminId(adminId);
        log.info(adminUser);

        if (adminUser == null) {
            throw new UsernameNotFoundException("해당 관리자를 찾을 수 없습니다.");
        }

        String enPassword = passwordEncoder.encode(adminUser.getPassword());
        adminUser.setPassword(enPassword);


        return adminUser;
    }
}
