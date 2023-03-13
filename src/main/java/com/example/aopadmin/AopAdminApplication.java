package com.example.aopadmin;

import com.example.aopadmin.config.ServiceBeanContext;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
@EnableScheduling
@MapperScan({"om.example.aopadmin.mapper"})
public class AopAdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication springApplication = new SpringApplication(AopAdminApplication.class);
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        Environment env = applicationContext.getEnvironment();
        ServiceBeanContext.load(applicationContext);
        String name = env.getProperty("spring.application.name");
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Doc: \thttp://{}:{}{}/doc.html\n" +
                        "----------------------------------------------------------",
                name,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                path);

        log.info("==========启动成功==========");
    }
//
//    @Value("${project.tmp.files.path}")
//    public String filesPath;
//
//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //设置路径xxx
//        factory.setLocation(filesPath);
//        return factory.createMultipartConfig();
//    }
}
