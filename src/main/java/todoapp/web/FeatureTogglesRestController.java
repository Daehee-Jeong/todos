package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import todoapp.web.model.FeatureTogglesProperties;

@RestController
public class FeatureTogglesRestController {
	
	/**
	 * 미션: 확장 기능 활성화 Web API 만들기
	 */
	
	private final Logger log = LoggerFactory.getLogger(FeatureTogglesRestController.class);
	
	private FeatureTogglesProperties properties;
	
	public FeatureTogglesRestController(FeatureTogglesProperties properties) {
		this.properties = properties;
	}

	@GetMapping("/api/feature-toggles")
	public FeatureTogglesProperties featureToggles(FeatureTogglesProperties properties) {
		this.properties.setAuth(properties.isAuth());
		this.properties.setOnlineUsersCounter(properties.isOnlineUsersCounter());
		log.info("auth: {}, onlineUsersCounter: {}", this.properties.isAuth(), this.properties.isOnlineUsersCounter());
		return this.properties;
	}
	
}
