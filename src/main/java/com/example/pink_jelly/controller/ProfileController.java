package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;
    private final FriendsService friendsService;
    private final BanService banService;
    private final ProfileService profileService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myProfile")
    public String profile(@AuthenticationPrincipal MemberDTO memberDTO, Model model){
        Long mno = memberDTO.getMno();
        String memberId = memberDTO.getMemberId();

        List<FriendsDTO> gmingDTOList = friendsService.gmingList(mno); // 그루밍 목록
        List<MemberDTO> gmerDTOlist = friendsService.gmerList(memberDTO.getMemberId()); // 그루머 목록
        List<BanDTO> banDTOList = banService.banList(mno); // 차단 목록
        List<MainBoardDTO> mainBoardDTOList = profileService.mainBoardList(memberId); // 메인 게시판 목록
        List<CatsMeBoardDTO> catsMeBoardDTOList = profileService.catsMeBoardList(memberId); // 입양소 게시판 목록
        List<CatsReviewBoardDTO> reviewBoardDTOList = profileService.reviewBoardList(memberId); //입양후기 게시판 목록


        model.addAttribute("mainBoardList", mainBoardDTOList);
        model.addAttribute("catsMeBoardList", catsMeBoardDTOList);
        model.addAttribute("reviewBoardList", reviewBoardDTOList);
        model.addAttribute("banDTOList", banDTOList);
        model.addAttribute("gmerDTOList", gmerDTOlist);
        model.addAttribute("gmingDTOList", gmingDTOList);
        model.addAttribute("mno", mno);
        return "profile/myProfile";
    }

    @GetMapping("/friendProfile")
    public String friendProfile(Long mno, String memberId, Model model, @AuthenticationPrincipal MemberDTO memberDTO, HttpServletRequest request, RedirectAttributes redirectAttributes){
        log.info("파라미터 mno "+mno);
        MemberDTO friends = memberService.findById(memberId);
        if(friends.isDel()){
            log.info("탈퇴된 회원입니다 admin이에용");
            String referer = request.getHeader("Referer");
            boolean isDel = friends.isDel();
            redirectAttributes.addFlashAttribute("isDel", isDel);
            log.info("del 여부 : " + friends.isDel());
            return "redirect:" + referer;
        }else{
            log.info("탈퇴안했어용 admin이에용");
            String friendId = friends.getMemberId();

            if(memberDTO != null){
                friends.setFlag(friendsService.isGming(memberDTO.getMno(), friendId));
                friends.setBan(banService.isBan(memberDTO.getMno(), friendId));
                log.info("로그인 mno는 ? "+memberDTO.getMno());
                log.info("친구 여부는 ? "+friendsService.isGming(memberDTO.getMno(), friendId));
                log.info("밴 여부는 ? " + banService.isBan(memberDTO.getMno(), friendId));


                String bannedId = memberDTO.getMemberId();
                boolean banned = banService.banned(bannedId);
                log.info("banId : " + bannedId);
                log.info("banned : "+ banned);
                model.addAttribute("banned", banned);
            }

            List<MainBoardDTO> mainBoardDTOList = profileService.mainBoardList(memberId); // 메인 게시판 목록
            List<CatsMeBoardDTO> catsMeBoardDTOList = profileService.catsMeBoardList(memberId); // 입양소 게시판 목록
            List<CatsReviewBoardDTO> reviewBoardDTOList = profileService.reviewBoardList(memberId); //입양후기 게시판 목록

            model.addAttribute("mainBoardList", mainBoardDTOList);
            model.addAttribute("catsMeBoardList", catsMeBoardDTOList);
            model.addAttribute("reviewBoardList", reviewBoardDTOList);
            model.addAttribute("memberDTO", friends);

            return "/profile/friendProfile";
        }
    }

}
