package fz;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ricardo.M.Lu
 */
@Slf4j
@SpringBootApplication
public class ZeromqPubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeromqPubApplication.class, args);
        log.info("0mq发布者项目启动成功");
    }
}