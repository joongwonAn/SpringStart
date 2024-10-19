package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
// ctrl + shift + t: test 코드 생성 단축키
class MemberServiceTest {
    /*
     * MemberService의 MemoryMemberRepository와 TestCase에서 만든 MemoryMemberRepository가 서로 다른 인스턴스인 것이 문제
     * 같은 인스턴스로 test하는 것이 옳다.
     * MemberService와 MemberServiceTest 모두 수정하기
     */

    /*MemberService memberService = new MemberService();
   MemoryMemberRepository memberRepository = new MemoryMemberRepository();*/
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    /* @BeforeEach
     * 각 테스트 실행 전에 호출됨
     * 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 맺어줌
     */
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 아래와 같이 해도 되지만, try-catch로 묶기 좀 번잡....
    /*@Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

        //then
    }*/

    //새로운 방법
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}