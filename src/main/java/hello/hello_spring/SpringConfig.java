package hello.hello_spring;

import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* @Configuration: 직접 스프링 빈에 등록하는 것을 인식
 * @Bean: 스프링 빈에 등록
 */
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
        /* MemberService에 가서 생성자를 확인해보니 MemberRepository가 필요함
         * 그래서 MemberRepository를 @Bean을 이용해서 스프링 빈에 주입
         */
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
        /* MemoryMemberRepository는 MemberRepository의 구현체이다.
         * MemberRepository는 인테페이스라 new 불가능
         */
    }
}
