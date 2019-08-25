package todoapp.web.user;

import java.net.URI;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.web.model.UserProfile;

@RestController
public class UserRestController {
	
	private final Logger log = LoggerFactory.getLogger(UserRestController.class);
	
	private ProfilePictureChanger profilePictureChanger;
	private ProfilePictureStorage profilePictureStorage;
	private UserSessionRepository userSessionRepository;


	public UserRestController(ProfilePictureChanger profilePictureChanger,
			ProfilePictureStorage profilePictureStorage, UserSessionRepository userSessionRepository) {
		this.profilePictureChanger = profilePictureChanger;
		this.profilePictureStorage = profilePictureStorage;
		this.userSessionRepository = userSessionRepository;
	}

	// 애초에 로그인되지 않은 사용자는 사용할 수 없다
	@RolesAllowed(UserSession.ROLE_USER)
	@GetMapping("/api/user/profile")
	public UserProfile userProfile(UserSession session) {
		return new UserProfile(session.getUser());
	}
	
	@RolesAllowed(UserSession.ROLE_USER)
	@PostMapping("/api/user/profile-picture")
	public UserProfile updateProfilePicture(MultipartFile profilePicture, UserSession session) {
		// 프로필 이미지를 갱신하는 로직이 필요
		log.info("profilePicture: {}", profilePicture);
		
		URI profilePictureUri = profilePictureStorage.save(profilePicture.getResource());
		// URI => file: /Users/...
				// 물리적 경로를 다루고있기 때문에 핸들러를 통해서 클라이언트가 접근가능한 주소로의 전환을 해야 한다.
		log.info("profilePictureUri: {}", profilePictureUri);
		
		User updatedUser = profilePictureChanger.change(session.getName(), new ProfilePicture(profilePictureUri));

		userSessionRepository.set(new UserSession(updatedUser));
		
		return new UserProfile(session.getUser());
	}
}
