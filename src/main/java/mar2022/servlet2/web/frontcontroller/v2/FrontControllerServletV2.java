package mar2022.servlet2.web.frontcontroller.v2;

import mar2022.servlet2.web.frontcontroller.MyView;
import mar2022.servlet2.web.frontcontroller.v1.ControllerV1;
import mar2022.servlet2.web.frontcontroller.v1.controller.MemberFormtControllerV1;
import mar2022.servlet2.web.frontcontroller.v1.controller.MemberListControllerV1;
import mar2022.servlet2.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import mar2022.servlet2.web.frontcontroller.v2.controller.MemberFromControllerV2;
import mar2022.servlet2.web.frontcontroller.v2.controller.MemberListControllerV2;
import mar2022.servlet2.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-from", new MemberFromControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();

        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(req, resp);
        view.render(req, resp);
    }
}
