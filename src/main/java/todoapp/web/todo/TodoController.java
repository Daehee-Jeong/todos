package todoapp.web.todo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {
	
//	방법 1
//	private Environment env;
//	
//	public TodoController(Environment env) {
//		this.env = env;
//	}
	
	private Environment env;
	
	@Value("${site.author}")
	private String author;
	
	private SiteProperties site;
	
	public TodoController(Environment env, SiteProperties site) {
		this.env = env;
		this.site = site;
	}
	
	@RequestMapping("/todos")
	public ModelAndView todos() {
//		String view = "classpath:tempaltes" + viewName + ".html";	// 타임리프의 기본 설정
//		ViewResolver가 이 역할(가져오는)을 한다.
		
		SiteProperties model = new SiteProperties();
		model.setAuthor(env.getProperty("site.author"));
		model.setAuthor(env.getProperty("site.description"));
		
		return new ModelAndView("todos", "site", site);
	}
}
