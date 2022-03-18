package mar2022.servlet2.web.frontcontroller.v3.controller;

import mar2022.servlet2.domain.member.Member;
import mar2022.servlet2.domain.member.MemberRepository;
import mar2022.servlet2.web.frontcontroller.ModelView;
import mar2022.servlet2.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        return mv;
    }
}
