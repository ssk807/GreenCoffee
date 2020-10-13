package echogaurd.greencoffe.interceptor;

import echogaurd.greencoffe.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getMethod() + " : " + request.getServletPath());

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            // request의 parameter에서 auth_token으로 넘어온 녀석을 찾는다.
            // String token = request.getParameter("auth_token");

            String token = request.getHeader("jwt-auth-token");
            if (token != null && token.length() > 0) {
                // 유효한 토큰이면 진행, 그렇지 않으면 예외를 발생
                jwtService.checkValid(token);
                return true;
            } else {
                throw new RuntimeException("인증 토큰이 없다.");
            }
        }
    }
}
