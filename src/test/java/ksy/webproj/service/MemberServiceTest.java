package ksy.webproj.service;

import ksy.webproj.domain.Member;
import ksy.webproj.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원가입")
    @Test
    public void join() throws Exception {
        //given
        Member member = Member.builder().name("kimsiyong").build();

        //when
        Long saveId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findById(saveId).get());
    }



    @DisplayName("중복회원예약")
    @Test()
    public void validateDuplicateMember() throws Exception {
        //given
        Member member1 = Member.builder().name("kimsiyong").build();
        Member member2 = Member.builder().name("kimsiyong").build();

        //when
        memberService.join(member1);

        //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat("이미 존재하는 회원입니다.").isEqualTo(thrown.getMessage());
    }
}