package com.test.spring.repository;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.test.spring.domain.Member;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into user(userID,userPassword,userName,userCountry,userEmail) values(?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;  //결과를 받는 변수


        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);    //RETURN_GENERATED_KEYS

            pstmt.setString(1,member.getId());
            pstmt.setString(2,member.getPassword());
            pstmt.setString(3,member.getName());
            pstmt.setString(4,member.getCountry());
            pstmt.setString(5,member.getEmail());

            pstmt.executeUpdate();  //db에 실제쿼리날리는 부분
            rs = pstmt.getGeneratedKeys();

            if(rs.next()){
                member.setId(rs.getString(1));
            }else{
                throw new SQLException("id 조회 실패");
            }
            return member;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Optional<Member> findById(String id) {
        String sql = "select * from user where userID=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;  //결과를 받는 변수


        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,id);

            rs = pstmt.executeQuery();  //db에 실제쿼리날리는 부분

            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getString("userID"));
                member.setId(rs.getString("userName"));
                member.setId(rs.getString("userCountry"));
                member.setId(rs.getString("userEmail"));
                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }


    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from user";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;  //결과를 받는 변수


        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();  //db에 실제쿼리날리는 부분

            List<Member> members = new ArrayList<>();

            while (rs.next()){
                Member member = new Member();
                member.setId(rs.getString("userID"));
                member.setName(rs.getString("userName"));
                member.setCountry(rs.getString("userCountry"));
                member.setEmail(rs.getString("userEmail"));
                members.add(member);
            }
            return members;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn) throws SQLException{
        DataSourceUtils.releaseConnection(conn,dataSource);
    }

    //연결종료
    public void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
      try {
          if(rs !=null){
              rs.close();
          }
      }catch (SQLException e){
          e.printStackTrace();
      }
      try {
          if(pstmt != null){
              pstmt.close();
          }
      }catch (SQLException e){
          e.printStackTrace();
      }
      try { //DB연결 종료
          if(conn !=null){
              close(conn);
          }
      }catch (SQLException e){
          e.printStackTrace();
      }
    }
}
