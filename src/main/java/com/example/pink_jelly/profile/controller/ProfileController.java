package com.example.pink_jelly.profile.controller;

import com.example.pink_jelly.ban.dto.BanDTO;
import com.example.pink_jelly.ban.service.BanService;
import com.example.pink_jelly.catsMe.dto.CatsMeBoardDTO;
import com.example.pink_jelly.catsMe.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.chat.dto.ChatRoomDTO;
import com.example.pink_jelly.chat.service.ChatService;
import com.example.pink_jelly.friends.dto.FriendsDTO;
import com.example.pink_jelly.friends.service.FriendsService;
import com.example.pink_jelly.mainBoard.dto.MainBoardDTO;
import com.example.pink_jelly.member.dto.MemberDTO;
import com.example.pink_jelly.member.service.MemberService;
import com.example.pink_jelly.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
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
    private final UserDetailsService userDetailsService;
    private final ChatService chatService;

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
        List<ChatRoomDTO> chatRoomDTOList = chatService.getRooms(mno); // 채팅 목록

        sessionReset(memberDTO);
        model.addAttribute("mainBoardList", mainBoardDTOList);
        model.addAttribute("catsMeBoardList", catsMeBoardDTOList);
        model.addAttribute("reviewBoardList", reviewBoardDTOList);
        model.addAttribute("chatRoomDTOList", chatRoomDTOList);
        model.addAttribute("banDTOList", banDTOList);
        model.addAttribute("gmerDTOList", gmerDTOlist);
        model.addAttribute("gmingDTOList", gmingDTOList);
        model.addAttribute("mno", mno);

        return "profile/myProfile";
    }

    @GetMapping("/friendProfile")
    public String friendProfile(Long mno, String memberId, Model model, @AuthenticationPrincipal MemberDTO memberDTO, HttpServletRequest request, RedirectAttributes redirectAttributes){

        log.info("파라미터 mno "+mno);
        log.info("memberID는 ?" + memberId);

        if( memberService.findById(memberId) != null){ // 친구가 멤버테이블에 존재할 경우
            MemberDTO friends = memberService.findById(memberId);
            log.info("friends는? "+friends);
            if(friends.isDel()){ // 친구가 탈퇴한 회원일 경우(isDel = true)
                log.info("탈퇴된 회원입니다");
                String referer = request.getHeader("Referer");
                boolean isDel = friends.isDel();
                redirectAttributes.addFlashAttribute("isDel", isDel);
                log.info("del 여부 : " + friends.isDel());
                return "redirect:" + referer;
            }else{ // 친구가 탈퇴 안한 회원일 경우
                log.info("탈퇴안했어용");
                String friendId = friends.getMemberId();

                if(memberDTO != null){ // 로그인 했을 경우.
                    if(friends.getMemberId().equals(memberDTO.getMemberId())){  // 친구아이디가 로그인한 본인일 경우 내 프로필로 이동
                        return "redirect:/profile/myProfile";
                    } // 로그인을 하고, 친구가 본인이 아닐경우
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

                    log.info("고양이 여부 ? " + friends.isHasCat());
                    List<MainBoardDTO> mainBoardDTOList = profileService.mainBoardList(memberId); // 메인 게시판 목록
                    List<CatsMeBoardDTO> catsMeBoardDTOList = profileService.catsMeBoardList(memberId); // 입양소 게시판 목록
                    List<CatsReviewBoardDTO> reviewBoardDTOList = profileService.reviewBoardList(memberId); //입양후기 게시판 목록

                    model.addAttribute("mainBoardList", mainBoardDTOList);
                    model.addAttribute("catsMeBoardList", catsMeBoardDTOList);
                    model.addAttribute("reviewBoardList", reviewBoardDTOList);
                    model.addAttribute("memberDTO", friends);

                    return "/profile/friendProfile";
                } else{ // 로그인 안했을 경우
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
        }else{
            return "redirect:/main";
        }

    }

    private void sessionReset(MemberDTO memberDTO) {
        // 세션 사용자 정보 업데이트
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberDTO.getMemberId());
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, new HashSet<GrantedAuthority>());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }
}
