package com.github.vad_ik.STP;

import com.github.vad_ik.STP.graphics.JavaFxStarter;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yml")
@Slf4j
public class StpApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxStarter.class, args);
    }

}


//todo писать какой сейчас этап + коротко описание отправляемых пакетов
