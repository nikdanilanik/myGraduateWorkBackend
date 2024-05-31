package dev.vorstu;

import dev.vorstu.components.Initializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VorstuApplication {
    private static Initializer initializer;

	@Autowired
	public void setInitialLoader(Initializer initiator) {
		VorstuApplication.initializer = initiator;
	}

	public static void main(String[] args) {
		SpringApplication.run(VorstuApplication.class, args);
        initializer.initial();
	}
}
