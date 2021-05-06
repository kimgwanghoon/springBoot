package com.test.spring;

import com.test.spring.repository.JdbcMemberRepository;
import com.test.spring.repository.MemberRepository;
import com.test.spring.repository.MemoryMemberRepository;
import com.test.spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//직접 spring bean 등록하기
@Configuration
public class SpringConfig {

     DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }

}
