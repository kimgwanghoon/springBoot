package com.test.spring.repository;

import com.test.spring.domain.Member;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository {

    private static Map<String,Member> store = new HashMap<>();    // 실무에서는 동시성문제가 있을수있어 공유되는 변수일 경우 컨커런트해쉬맵(ConcurrentHashMap) 사용해야함
    private  static long sequence =0L;

    @Override
    public void save(Member member) {
        sequence++;
        member.setId("kkhq"+sequence);
        store.put(member.getId(),member);

    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.of(store.get(id));  //null이 반환 될 가능성이 있을경우 지금은 Optional.of로 감싼상태로 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()     // 데이터 읽기를 루프로 돌리는 구성
                .filter((member -> member.getName().equals(name))) //람다사용 넘어온 데이터와 같은게있는지 비교
                .findAny(); // 하나라도 찾으면 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
