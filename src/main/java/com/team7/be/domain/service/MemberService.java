package com.team7.be.domain.service;


import com.team7.be.domain.controller.request.member.SignUpRequest;
import com.team7.be.domain.entity.Member;
import com.team7.be.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class MemberService {
    private final MemberRepository memberRepository;


    @Transactional
    public String signUp(SignUpRequest signUpRequest){
        checkDuplicateMemberId(signUpRequest.getUserId());
        // 사용자의 id를 가져와서 이미 repository에 존재하는 userid인지 체크
        Member member = Member.builder()
                .userId(signUpRequest.getUserId())
                .build();
        // 사용자로부터 전달된 member정보(signUpRequest)에서 필요한 정보(memberID)를 가져와 Member엔티티를 build한다.
        return memberRepository.save(member).getUserId(); // 저장한 member의 userid를 반환한다.
    } // Controller에서 해당 메소드 사용

    private void checkDuplicateMemberId(String userId){
        if(memberRepository.findByUserId(userId).isPresent()){
            System.out.println("이미 존재하는 id입니다.");
        }
        //member에 대한 정보를 저장하고있는 repo에서 입력받은 userid가 존재하는 지에 대한 여부를 판단.
        // 이미 member 정보가 존재할경우 이를 알림.
    }
}
