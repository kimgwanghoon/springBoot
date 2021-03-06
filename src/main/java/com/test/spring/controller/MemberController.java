package com.test.spring.controller;

import com.test.spring.domain.Member;
import com.test.spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    //spring 컨테이너에 있는 MemberService 를 연결해주는 기능
    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;

    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        member.setId(form.getId());
        member.setPassword(form.getPassword());
        member.setCountry(form.getCountry());
        member.setEmail(form.getEmail());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public  String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
