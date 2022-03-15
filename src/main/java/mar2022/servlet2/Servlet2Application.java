package mar2022.servlet2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //서블릿 자동 등록
@SpringBootApplication
public class Servlet2Application {
	public static void main(String[] args) {
		SpringApplication.run(Servlet2Application.class, args);

	}

}
