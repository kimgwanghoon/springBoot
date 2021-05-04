package com.test.spring;

import com.test.spring.repository.MemberRepository;
import com.test.spring.repository.MemoryMemberRepository;
import com.test.spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//직접 spring bean 등록하기
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

}
