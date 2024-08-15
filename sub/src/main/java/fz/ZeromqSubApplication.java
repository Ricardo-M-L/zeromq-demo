package fz;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ricardo.M.Lu
 */
@Slf4j
@SpringBootApplication
public class ZeromqSubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeromqSubApplication.class, args);
        log.info("0mq订阅者项目启动成功");
    }
}