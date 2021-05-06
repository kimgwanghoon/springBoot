package com.test.spring.service;

import com.test.spring.domain.Member;
import com.test.spring.repository.MemberRepository;
import com.test.spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {   //직접 new 하지않고 외부 memberRepository를 넣어주는 것 DI
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public String join(Member member){
        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);    //중복회원검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //권장하는 사용방식
        memberRepository.findByName(member.getName())
                .ifPresent(m ->   {       // null 이 아니라 값이있으면 아래 로직동작하는 구문, Optional 라서 가능
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        /*Optional<Member> result = memberRepository.findByName(member.getName());

        result.ifPresent(m ->   {       // null 이 아니라 값이있으면 아래 로직동작하는 구문, Optional 라서 가능
                throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/
    }

    /**
    *   전체회원조회
    */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(String memberId){
        return memberRepository.findById(memberId);
    }
}
