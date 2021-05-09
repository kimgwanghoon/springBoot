package com.test.spring.repository;

import com.test.spring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired  //생성자가 하나일경우 @Autowired 생략가능하다.
    public JdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Member member) {

    }

    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = jdbcTemplate.query("select * from user where userID = ?",memberRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    private RowMapper<Member> memberRowMapper(){
        //람다 변경
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getString("userID"));
            member.setName(rs.getString("userName"));
            return member;
        };

        //람다변경전
        /*return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getString("userID"));
                member.setName(rs.getString("userName"));
                return member;
            }
        };*/
    }
}
