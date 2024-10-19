package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;
/*
 * 동시성 문제가 고려되어 있지 않음.
 * 실무에서는 HashMap, Sequence 대신 ConcurrentHashMap, AtomicLong 사용 고려
 */

// 회원 리포지토리 메모리 구현체
public class MemoryMemberRepository implements MemberRepository { // alt + enter: implements 단축키

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 0, 1, 2와 같은 키 값 생성

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /* Optional을 사용하는 요즘 방식
         * null이 생길 가능성이 있을 때 사용하면 client에서 조작 가능
         */
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // findAny: 하나라도 찾는 것
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
