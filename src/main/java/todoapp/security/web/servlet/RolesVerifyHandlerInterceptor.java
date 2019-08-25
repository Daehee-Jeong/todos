package todoapp.security.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import todoapp.security.UnauthorizedAccessException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.security.support.RolesAllowedSupport;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Role(역할) 기반으로 사용자가 사용 권한을 확인하는 인터셉터 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class RolesVerifyHandlerInterceptor implements HandlerInterceptor, RolesAllowedSupport {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private UserSessionRepository sessionRepository;

    public RolesVerifyHandlerInterceptor(UserSessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
    }

	@Override
    public final boolean preHandle(
    		HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	if (handler instanceof HandlerMethod) { // 직접 작성한 핸들러라면
    
    		HandlerMethod handlerMethod = ((HandlerMethod) handler);
    		RolesAllowed rolesAllowed = ((HandlerMethod) handler)
    				.getMethodAnnotation(RolesAllowed.class);
    		
    		if (Objects.isNull(rolesAllowed)) {
    			// 컨트롤러 안의 핸들러(메서드)에 붙어있는 애노테이션을 찾는 로직에서 Controller에 붙어있는 애노테이션도 찾을 수 있도록 수정
    			rolesAllowed = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getBeanType(), RolesAllowed.class);
    		}
    		
    		if (Objects.nonNull(rolesAllowed)) {
    			// 1. 사용자가 로그인되어 있습니까?
    			UserSession session = sessionRepository.get();
    			if (Objects.isNull(session)) {
    				throw new UnauthorizedAccessException();
    			}
    			// 2. 사용자가 핸들러를 실행할 권한을 가지고 있습니까?
    		}
    	}
    	
    	return true;
    }

}
