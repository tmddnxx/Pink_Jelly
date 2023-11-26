package com.example.pink_jelly.chat.controller;

import com.example.pink_jelly.chat.dto.ChatRoomDTO;
import com.example.pink_jelly.chat.service.ChatService;
import com.example.pink_jelly.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Log4j2
public class RoomController {
    private final ChatService chatService;

    // 채팅방 목록 조회
    @GetMapping("/rooms")
    public ModelAndView rooms(@AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("# All Chat Rooms");
        ModelAndView modelAndView = new ModelAndView("chat/rooms");

        modelAndView.addObject("list", chatService.getRooms(memberDTO.getMno()));
        log.info("/rooms(GET)....");

        return modelAndView;
    }

    // 채팅방 개설
    @PostMapping("/room")
    public String create(Long fromMno, String name, @AuthenticationPrincipal MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
        log.info("# Create Chat Room...");

        Long toMno = memberDTO.getMno();

        ChatRoomDTO chatRoomDTO = chatService.createChatRoom(toMno, fromMno, name);
        log.info(chatRoomDTO);

        redirectAttributes.addAttribute("roomId", chatRoomDTO.getRoomId());

        return "redirect:/chat/room";
    }
    
    // 채팅방 조회
    @GetMapping("/room")
    public void getRoom(String roomId, Model model) {
        log.info("# get Chat Room, roomID : " + roomId);
        model.addAttribute("messages", chatService.getMessages(roomId));
        model.addAttribute("room", chatService.getRoom(roomId));
    }
}
