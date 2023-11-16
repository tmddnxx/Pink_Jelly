package com.example.pink_jelly.dto;

import com.example.pink_jelly.dto.upload.UploadResultDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberDTO extends User {
    private Long mno; // 회원 고유 넘버

    @NotBlank(message = "아이디를 입력해주세요.")
    private String memberId; // 회원 아이디

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8 ~ 16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String passwd; // 회원 비밀번호

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email; // 회원 이메일

    @NotBlank(message = "이름을 입력해주세요.")
    private String memberName; // 회원이름

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone; // 회원 전화번호

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,15}$", message = "닉네임은 특수문자를 제외한 2~15자리여야 합니다.")
    private String nickName; // 회원 닉네임

    private boolean hasCat; // 고양이 여부

    private String catAge; // 고양이 나이 (개월/년)
    private String catSex; // 고양이 성별
    private String variety; // 고양이 품종
    private String profileImg; // 프로필이미지 (고양이사진)
    private int gmingCnt; // 그루밍 수
    private int gmerCnt; // 그루머 수
    private String introduce; // 소개글
    private boolean del; // 탈퇴여부

    private boolean del; // 회원 탈퇴 여부
    private boolean social; // 소셜 로그인

    private boolean flag; // 친구 여부
    private boolean ban; // 차단 여부

    public MemberDTO(Long mno, String username, String password, String email,String memberName,
                     String phone, String nickName, boolean hasCat, String catAge, String catSex,
                     String variety, String profileImg, int gmingCnt, int gmerCnt, String introduce,
                     boolean del, boolean social, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mno = mno;
        this.memberId = username;
        this.passwd = password;
        this.email = email;
        this.memberName = memberName;
        this.phone = phone;
        this.nickName = nickName;
        this.hasCat = hasCat;
        this.catAge = catAge;
        this.catSex = catSex;
        this.variety = variety;
        this.profileImg = profileImg;
        this.gmingCnt = gmingCnt;
        this.gmerCnt = gmerCnt;
        this.introduce = introduce;
        this.flag = flag;
        this.ban = ban;
        this.del = del;
        this.social = social;
    }

    public UploadResultDTO getImagePath() {
        String[] splits = this.profileImg.split("/");

        return UploadResultDTO.builder()
                .fileName(splits[0])
                .dateFolder(splits[1])
                .isImage(true)
                .build();
    }

}
