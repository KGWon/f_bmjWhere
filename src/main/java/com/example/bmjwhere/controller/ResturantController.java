package com.example.bmjwhere.controller;

import com.example.bmjwhere.dto.ClubMemberDTO;
import com.example.bmjwhere.dto.PageRequestDTO;
import com.example.bmjwhere.dto.ResturantDTO;
import com.example.bmjwhere.repository.ClubMemberRepository;
import com.example.bmjwhere.security.dto.ClubAuthMemberDTO;
import com.example.bmjwhere.service.ClubMemberService;
import com.example.bmjwhere.service.ReplyService;
import com.example.bmjwhere.service.ResturantService;
import com.example.bmjwhere.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("/resturant")
@Log4j2
@RequiredArgsConstructor
public class ResturantController {

    private final ResturantService resturantService;
    private final ClubMemberService ClubMemberService;
    @Autowired
    private ClubMemberRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("/register")
    public String register(ResturantDTO resturantDTO, RedirectAttributes redirectAttributes) {
        log.info("resturantDTO: " + resturantDTO);
        Long rno = resturantService.register(resturantDTO);
        redirectAttributes.addFlashAttribute("msg", rno);
        return "redirect:/resturant/list";
    }

    @GetMapping({"/list", "/list1"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", resturantService.getList(pageRequestDTO));
    }

 /*   @GetMapping({"/read", "/modify"})
    public void read(long rno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("rno: " + rno);
        ResturantDTO resturantDTO = resturantService.getResturant(rno);
        model.addAttribute("dto", resturantDTO);



    }*/

    @GetMapping({"/read"})
    public String read(long rno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                       @AuthenticationPrincipal UserDetails user, Model model) {
        log.info("rno: " + rno);
        ResturantDTO resturantDTO = resturantService.getResturant(rno);
        model.addAttribute("dto", resturantDTO);
        model.addAttribute("author",user.getUsername());
        return "/resturant/read";
    }

    @GetMapping({"/modify"})
    public void modify(long rno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                       Model model) {
        log.info("rno: " + rno);
        ResturantDTO resturantDTO = resturantService.getResturant(rno);
        model.addAttribute("dto", resturantDTO);
    }

/*
    @GetMapping({"/read"})
    public String read(long rno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, @AuthenticationPrincipal UserDetails user, Model model) {
        log.info("rno: " + rno);
        ResturantDTO resturantDTO = resturantService.getResturant(rno);
        model.addAttribute("dto", resturantDTO);
        model.addAttribute("writer",user.getUsername());

        return "/resturant/read";
    }

    @GetMapping({"/modify"})
    public void modify(long rno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("rno: " + rno);
        ResturantDTO resturantDTO = resturantService.getResturant(rno);
        model.addAttribute("dto", resturantDTO);
    }

*/

    @GetMapping("/login")
    public void login(){

    }

    @PostMapping("/remove")
    public String remove(long rno, RedirectAttributes redirectAttributes) {
        log.info("rno: " + rno);
        resturantService.removeWithReplise(rno);
        redirectAttributes.addFlashAttribute("msg", rno);
        return "redirect:/resturant/list";
    }

    @PostMapping("/modify")
    public String modify(ResturantDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        log.info("post modify..................");
        log.info("dto: " + dto);

        resturantService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addAttribute("rno", dto.getRno());

        return "redirect:/resturant/read";
    }

    @GetMapping("/eatDeal")
    public void eatDeal() {

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/eatDeal2")
    public void eatDeal2() {

    }

    @GetMapping({"/categoryHansik", "/categoryIlsik", "/categoryJongsik", "/categoryYangsik", "/categoryCafe"})
    public void categoryIlsik(PageRequestDTO pageRequestDTO, Model model) {
        log.info("get categoryilsik......");
        model.addAttribute("result", resturantService.getJList(pageRequestDTO));
    }

    @GetMapping("/joininSuccess")
    public void joininSuccess() {

    }


    @GetMapping("/cess2")
    public void cess2() {
//        log.info("register..........");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/memberMypage")
    public void memberMypage() {

    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminOnly")
    public void adminOnly() {
//        log.info("register..........");
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/memberOnly")
    public void memberOnly(@AuthenticationPrincipal ClubAuthMemberDTO memberDTO, Model model){

        log.info("Sample....");
        log.info(memberDTO);

        model.addAttribute("member", memberDTO.getName());
    }



    @GetMapping("/register2")
    public void register2() {
//        log.info("register..........");
    }

    @PostMapping("/register2")
    public String register2(ClubMemberDTO clubMemberDTO, RedirectAttributes redirectAttributes) {
        log.info("clubmemberDTO: " + clubMemberDTO);

        String email = ClubMemberService.register2(clubMemberDTO);
//
        // addAttribute로 전달한 값은 url뒤에 붙으며, 리프레시(새로고침)해도 데이터가 유지
        // addFlashAttribute로 전달한 값은 url뒤에 붙지 않는다. 일회성이라 리프레시할 경우 데이터가 소멸한다.
        // 또한 2개이상 쓸 경우, 데이터는 소멸한다. 따라서 맵을 이용하여 한번에 값을 전달해야한다.
        redirectAttributes.addFlashAttribute("msg2", email);

        return "redirect:/resturant/joininSuccess";

    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/index.html";
    }


    @GetMapping("/test")
    public String getName(Model model, @AuthenticationPrincipal UserDetails user) {
        //  if(user != null){
        //      log.trace("user : {}", user.getUsername());
        //   }

        model.addAttribute("author", user.getUsername());
        return "/resturant/test";

    }



}


