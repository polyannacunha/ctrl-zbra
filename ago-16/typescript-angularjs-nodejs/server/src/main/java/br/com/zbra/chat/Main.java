package br.com.zbra.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.actuate.system.EmbeddedServerPortFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@ComponentScan
@EnableAutoConfiguration
@EnableAsync
public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Configuring application UI look and feel...");

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("Could not set look and feel for cross platform");
        }

        final SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.addListeners(new ApplicationPidFileWriter(), new EmbeddedServerPortFileWriter());
        springApplication.run(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Path path = Paths.get(System.getProperty("user.dir"));
                Runtime.getRuntime().exec(String.format("%s\\stop.bat", path.getParent().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}