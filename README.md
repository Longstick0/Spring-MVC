# Spring MVC 

------------
## 1. MVC 프레임워크 만들기
### FrontController 패턴의 특징
+ 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
+ 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
+ 입구를 하나로!!
+ 공통 처리 가능

![img.png](img.png)

+ ### FrontController 단계적 도입 - v1
프론트 컨트롤러를 단계적으로 도입하자.
![img_1.png](img_1.png)

```java
private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-from", new MemberFormtControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String requestURI=req.getRequestURI();

        ControllerV1 controller=controllerMap.get(requestURI);
        }
```
핵심은 위의 코드다. FrontController에 위와 같이 매핑정보를 부여하여 ```getRequestURI```메서드를 통해 주소로 들어오는 값을 매핑하여 ```Map```을 통해 컨트롤러를 호출한다.

---
+ ### View 분리 - v2

![img_2.png](img_2.png)
```java
public class MyView {

    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
```
기존 v1에서 Controller에서 직접 JSP페이지로 넘겨주던 방식을
```MyView```클래스를 새로 생성하여 기존에 ```getRequestURI```을 가져와 ```forward```해야했던 번거로움이 사라짐.

+ ### Model 추가 - v3

![img_3.png](img_3.png)

```java
@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(req);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), req, resp);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap;
    }
```
전체적인 FrontController의 코드 양이 증가했지만, viewResolver와 요청 파라미터 정보를 Map으로 넘기도록 하여 
구체적인 Controller에서는 훨씬 코드가 간결해진 것을 확인할 수 있다. 

```java
public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");
    }
}
```
```ModelView```

이 컨트롤러는 서블릿 기술을 전혀 사용하지 않는다. 따라서 구현이 매우 단순해지고, 테스트 코드 작성시
테스트 하기 쉽다.
```HttpServletRequest```가 제공하는 파라미터는 프론트 컨트롤러가 ```paramMap```에 담아서 호출해주면 된다.
응답 결과로 뷰 이름과 뷰에 전달할 Model 데이터를 포함하는 ```ModelView``` 객체를 반환하면 된다.