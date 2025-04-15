package com.github.vad_ik.STP.graphics;

import com.github.vad_ik.STP.StpApplication;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxStarter extends Application {
    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(StpApplication.class)
                .headless(false)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStageController controller = springContext.getBean(MainStageController.class);
        controller.start(primaryStage);
    }

    @Override
    public void stop() {
        springContext.close();
    }
}