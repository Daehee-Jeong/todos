package todoapp.security.web;

import java.io.OutputStream;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OnlineUsersCounterController {
	
	// user count를 어떻게 구할 것인지에 대해서는 따로고민. 응답형식에 치중
	@RequestMapping("/stream/online-users-counter")
	public void counter(HttpServletResponse response) {
		response.setContentType("text/event-stream");
		
		IntStream.range(1, 11).forEach(number -> {
			try {
				Thread.sleep(1000);
				
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(String.format("data: %d\n\n", number).getBytes());
				outputStream.flush();
				// SSE의 특징, 클라이언트 입장에서는 연결이 끊기면 잽싸게 다시연결함
				// 서버입장에서는 굉장히 느긋함
				// 톰캣의 기본 쓰레드 200개
				// 한서버가 많아봐야 500개 이상 안함. 서버의 처리느려짐
			} catch (Exception ignore) {
				
			}
		});
	}
}
