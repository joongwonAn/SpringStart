package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//@Service
public class MemberService {
    /*
     * test와 같은 인스터스 사용하기 위해 바꿔야 한다.
     * 여기에서 레포지토리를 만드는게 아니라 외부에서 만들 수 있게 바꾸기
     */
//    private final MemberRepository memberRepository = new MemberRepository();
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/

        // 중복회원 검증
        validateDuplicateMember(member); // ctrl + alt + m: extract method 단축키, validateDuplicateMember 생성

        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m-> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
