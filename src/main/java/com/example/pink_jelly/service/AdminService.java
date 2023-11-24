package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;

import java.util.List;

public interface AdminService {

    List<MemberDTO> adminMemberSearch(AdminSearchDTO adminSearchDTO);

    void removeMember (Long mno);
}
