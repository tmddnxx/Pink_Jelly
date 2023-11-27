package com.example.pink_jelly.admin.mapper;

import com.example.pink_jelly.admin.AdminUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper {
    public AdminUser findByAdminId(String adminId);
}
