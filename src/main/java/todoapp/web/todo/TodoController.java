package todoapp.web.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import todoapp.commons.domain.Spreadsheet;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.web.convert.TodoToSpreadsheetConverter;
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
	
	private TodoFinder finder;
	
	@Value("${site.author}")
	private String author;
	
	private SiteProperties site;
	
	public TodoController(Environment env, SiteProperties site, TodoFinder finder) {
		this.env = env;
		this.site = site;
		this.finder = finder;
	}
	
	@RequestMapping("/todos")
	public void todos(Model model) {
		List<Todo> todos = finder.getAll();
		Spreadsheet sheet = new TodoToSpreadsheetConverter().convert(todos);
		model.addAttribute(sheet);
	}
}
