package com.roncoo.education.interceptor;

import com.roncoo.education.util.MyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("======before======");
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String path = request.getRequestURI();
        if (path.equals("/") || path.contains("/login")) {
            return true;
        } else {
            String method = request.getMethod().toLowerCase();
            String token;
            if(method.equals("get")){
//                token = controller.getPara("token");
                token = "debug";
                return true;
            }else{
                token = request.getHeader("token");
            }

            if(token == null){
                response.getWriter().write(MyUtil.getJson("用户登录失效。", 611, "").toString());
                return false;
            }

            if (token.equals("debug")) {
                return true;
            } else {
//                return true;
                response.getWriter().write(MyUtil.getJson("用户登录失效。", 611, "").toString());
                return false;

//				HttpSession session = request.getSession();
//				JSONObject loginObj = (JSONObject) session.getAttribute(token);
//				// 如果loginObj已经是null
//				if (loginObj == null) {
//					response.getWriter().write(MyUtil.getJson("用户登录失效。", 611, "").toString())
//				} else {
//					String loginName;
//					try {
//						loginName =(String) loginObj.get("login_name");
//						String sessionToken = (String) session.getAttribute(loginName);
//						int nowTime = Math.round(new Date().getTime() / 1000);
//						int expireTime = (int)loginObj.get("expireTime");
//						// 两个token值相同
//						if(sessionToken.equals(token)) && expireTime >= nowTime){
//							loginObj.put("expireTime", MyUtil.getRefreshTime()); // 刷新过期时间
//							session.setAttribute(token, loginObj);
//							return true;
//						}else{
//							session.removeAttribute(token);
//							response.getWriter().write(MyUtil.getJson("用户登录失效。", 611, "").toString());
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
            }

        }

//        return false;
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("======after======");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        logger.info("======afterCompletion======");
    }
}
