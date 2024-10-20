package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// 회원 리포지토리 인터페이스
//@Repository
public interface MemberRepository {

    Member save(Member member); // 회원 저장
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
