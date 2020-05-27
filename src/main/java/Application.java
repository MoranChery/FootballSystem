import Service.TeamOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan(basePackages = "Service")
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
