package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ricardo.M.Lu
 */
@Slf4j
@SpringBootApplication
public class ZeromqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeromqApplication.class, args);
        log.info("0mq项目启动成功");
    }
}