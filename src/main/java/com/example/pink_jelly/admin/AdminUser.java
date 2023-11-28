package com.example.pink_jelly.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUser implements UserDetails {
    private Long ano; // 관리자 고유번호
    private String adminId; // 관리자 아이디
    private String password; // 비밀번호
    private String adminName; // 관리자 이름
    private String role; // 관리자 역할
    private LocalDateTime createdAt; // 생성 날짜
    private LocalDateTime updatedAt; // 수정 날짜
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : role.split(",")) {
            System.out.println("role: " + role);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.adminId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
