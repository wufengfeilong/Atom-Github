package fcn.co.jp.park;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("fcn.co.jp.park.mapper")
public class SmartParkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartParkApplication.class, args);
	}
}
