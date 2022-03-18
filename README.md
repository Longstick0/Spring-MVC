#Spring MVC 

------------
##1. MVC 프레임워크 만들기
###FrontController 패턴의 특징
+ 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
+ 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
+ 입구를 하나로!!
+ 공통 처리 가능

![img.png](img.png)

+ ###FrontController 단계적 도입 - v1
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
