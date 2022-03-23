package mar2022.servlet2.web.frontcontroller.v5;

import mar2022.servlet2.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerAdapter {

    boolean supports(Object handler);

    ModelView handle(HttpServletResponse response, HttpServletRequest request, Object handler) throws ServletException, IOException;
}
