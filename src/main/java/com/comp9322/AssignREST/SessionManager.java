package com.comp9322.AssignREST;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.comp9322.AssignREST.response.JsonResponse;
import com.google.gson.Gson;

public class SessionManager implements HandlerInterceptor{
	private final static String CLIENT_TOKEN="$2y$10$HnIpEBB9xgFQmUfDYWpvQenYRiBhr336VDRV.DOCd3OWMY8emf3o2";
	private final static String OFFICER_TOKEN="o6RdxluHDu7fHBgpk9R2hBMLLfP6z74v9pnopAE0co5EuZflZgw9cXavJ3zL";

    // This method is called before the controller
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    		if(request.getHeader("authorization") == null){
    			response.setStatus(401);
            response.getWriter().append(new Gson().toJson(new JsonResponse(401, "authentication_reuqired")));
    			return false;
    		}
        String xHeader = request.getHeader("Authorization");
        //boolean permission = true;
        System.out.println(request.getRequestURI());
        System.out.println(request.getMethod());
        String url = request.getRequestURI();
        String method = request.getMethod();
        if(xHeader.equals(CLIENT_TOKEN)) {
        		//
        		if(method.equals("PUT")){
            		Pattern pattern = Pattern.compile("/payments|/renewals|/licenses");
        		    Matcher match = pattern.matcher(url);
        		    if(match.find()){
        		    		return true;
        		    }
        		}else if(method.equals("GET")){
            		Pattern pattern = Pattern.compile("/payments/[0-9]+|/renewals/.*|/licenses/.*|/licenseNotice/token/.*"
            				+ "|/payments/nid/[0-9]+");
        		    Matcher match = pattern.matcher(url);
        		    if(match.find()){
        		    		return true;
        		    }
        		}
        		response.setStatus(401);
            response.getWriter().append(new Gson().toJson(new JsonResponse(401, "authentication_reuqired")));

            return false; 
        }
        else if(xHeader.equals(OFFICER_TOKEN)){
            return true;
        } else {
        		response.setStatus(401);
            response.getWriter().append(new Gson().toJson(new JsonResponse(401, "authentication_reuqired")));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}