package mar2022.servlet2.web.frontcontroller.v3.controller;

import mar2022.servlet2.web.frontcontroller.ModelView;
import mar2022.servlet2.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
