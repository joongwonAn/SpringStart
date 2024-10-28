package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/* @Controller를 사용하면 해당 클래스를 스프링 컨테이너에 자동 등록한다.
 * 해당 클래스는 스프링 컨테이너 내부의 스프링 빈이 된다.
 */
@Controller
public class MemberController {

    private final MemberService memberService;

    /* 생성자에 @Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다.
     * 이것을 DI(Dependency Injection, 의존성 주입)이라 한다,
     * DI : 객체 의존관계를 외부에서 넣어주는 것
     *
     * 이전 테스트에서는 개발자가 직접 주입했고, 여기서는 @Autowired에 의해 스프링이 주입해준다.
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 추가: 회원 등록 폼 컨트롤러
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    // 추가2: 회원 실제 등록하는 기능
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        // 홈화면으로 보내기
        return "redirect:/home";
    }

    // 회원 컨트롤러에서 조회 기능
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}

/* DI의 종류
 * 1. 필드 주입
 *    - 별로 안 좋은 방식
 *    - @Autowired private MemberService memberService;
 * 2. setter 주입
 *    - @Autowired
 *      public void setMemberService(MemberService memberService) { ... }
 *    - public하게 노출되서 별로 안 좋은 방식이다.
 * 3. 생성자 주입
 *    - 컨트롤러에서 생성자를 통해 주입
 *    - 위에서 한 방식
 *
 * 의존관계가 실행 중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입 권장
 */