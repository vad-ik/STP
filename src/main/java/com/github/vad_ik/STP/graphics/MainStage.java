package com.github.vad_ik.STP.graphics;

import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.Router;
import com.github.vad_ik.STP.service.CalculateLocationService;
import jakarta.annotation.PostConstruct;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.vad_ik.STP.graphics.utils.WindowConstantHolder.*;

@Service
public class MainStage extends Application {
    private int phase = 1;
    private Router connect;

    private CalculateLocationService calculateLocationService;

    @PostConstruct
    public void launch() {
        Application.launch();
    }

    @PostConstruct
    @Autowired
    public void init(CalculateLocationService calculateLocationService) {
        this.calculateLocationService = calculateLocationService;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
        stage.setTitle("STP");
        Pane activeRegion = new Pane(); // Контейнер для кругов

        // Обработчик клика мыши
        activeRegion.setOnMouseClicked(event -> {
            switch (phase) {
                case (1) -> addNode(activeRegion, event);
                case (2) -> addConnection(activeRegion, event);
                case (3) -> {
                }
                default -> throw new IllegalStateException("Unknown phase" + phase);
            }
        });
        BorderPane root = new BorderPane();
        root.setCenter(activeRegion);
        root.setBottom(addBut(activeRegion));
        Scene scene = new Scene(root, WIDTH, HEIGHT); // Размер окна // todo константы

        stage.setScene(scene);
    }


    private void addNode(Pane activeRegion, MouseEvent event) {
        Router circle = new Router(SIZE_ROUTER, activeRegion.getChildren().size(), event.getX(), event.getY());
        activeRegion.getChildren().add(circle); // Добавляем круг на сцену
    }

    private void addConnection(Pane activeRegion, MouseEvent event) {
        if (connect == null) {
            connect = findConnect(activeRegion, event);
            return;
        }
        Router connect2 = findConnect(activeRegion, event);
        if (connect2 == null) {
            return;
        }
        if (connect2 == connect) {
            connect.getCircle().setStrokeWidth(0);
        } else {
            boolean ans = false;
            for (int i = 0; i < activeRegion.getChildren().size(); i++) {
                if ((activeRegion.getChildren().get(i)) instanceof ConnectionRouter) {
                    ans = ans || ((ConnectionRouter) activeRegion.getChildren().get(i)).isConnected(connect, connect2);
                }
            }
            if (!ans) {
                ConnectionRouter connectionRouter = new ConnectionRouter(connect, connect2);
                activeRegion.getChildren().add(connectionRouter);
                connect.getConnection().add(connectionRouter);
                connect2.getConnection().add(connectionRouter);
            }
            connect.getCircle().setStrokeWidth(0);
            connect2.getCircle().setStrokeWidth(0);
        }
        connect = null;

    }

    private Router findConnect(Pane activeRegion, MouseEvent event) {
        return calculateLocationService.findConnect(activeRegion, event.getX(), event.getY());
    }


    private HBox addBut(Pane activeRegion) {
        HBox butPanel = new HBox(2);
        Text text = new Text("Добавьте узлы коммутаторы");
        Button buttonNext = new Button("Далее");
        buttonNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (phase) {
                    case (1) -> {
                        text.setText("Добавьте связи между коммутаторами");
                        phase++;
                    }
                    case (2) -> {
                        text.setText("Симуляция");
                        phase++;
                    }
                    case (3) -> {
                        text.setText("Перезапустить");
                    }
                    default -> throw new IllegalStateException("Unknown phase" + phase);
                }
            }
        });

        butPanel.getChildren().add(text);
        butPanel.getChildren().add(buttonNext);

        return butPanel;
    }
}
