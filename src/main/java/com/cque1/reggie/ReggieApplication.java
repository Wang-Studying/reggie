package com.cque1.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
	public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(ReggieApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.context-path");
        if (StringUtils.isEmpty(path)) {
            path = "";
        }
        log.info("\n\t----------------------------------------------------------\n\t" +
                "项目启动成功! \n\t" +
                "管理端网址:\n\t\t\t http://localhost:" + port + path + "/backend/page/login/login.html" + "\n\t" +
                "用户端网址:\n\t\t\t http://localhost:" + port + path +"/front/page/login.html"+ "\n\t" +
                "----------------------------------------------------------");

    }

}
