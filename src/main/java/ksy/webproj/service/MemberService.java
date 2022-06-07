package ksy.webproj.service;

import ksy.webproj.domain.Member;
import ksy.webproj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member){
        return memberRepository.save(validateDuplicateMember(member)).getId();
    }

    private Member validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        return member;
    }

    /**
     * 회원 전체 조회
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     * @param memberId
     * @return
     */
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalArgumentException("존재하는 회원이 없습니다."));
    }

}
