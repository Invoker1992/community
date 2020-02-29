package com.mrh.community.interceptor;

import com.mrh.community.mapper.UserMapper;
import com.mrh.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Thanks For Watching！
 *
 * @author HuJiaqun
 * @date 2020/2/26 21:50
 **/

@Service
public class SessionInterceptor implements HandlerInterceptor {

    //由于拦截器不是spring接管的工具，所以此类要标注@service，不然Autowird无法注入Bean
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)
        {
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals("token"))
                {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user!=null)
                    {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}