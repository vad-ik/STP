package com.github.vad_ik.STP.graphics;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.Switch;
import com.github.vad_ik.STP.service.LocationService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class MainStageController {
    private int phase = 1;

    private final LocationService locationService;
    private final WindowConstantHolder consts;
    @Autowired
    private ApplicationContext context;  // Spring сам внедрит контекст

    @Autowired
    public MainStageController(LocationService locationService, WindowConstantHolder windowConstantHolder) {
        this.locationService = locationService;
        this.consts = windowConstantHolder;
    }

    public void start(Stage stage) {
        stage.show();
        stage.setTitle(consts.TITLE);
        Pane activeRegion = new Pane(); // Контейнер для кругов
        // Обработчик клика мыши
        activeRegion.setOnMouseClicked(event -> {
            switch (phase) {
                case (1) -> addNode(activeRegion, event);
                case (2) -> locationService.addConnection(activeRegion, event);
                case 3, 4 -> {                }
                default -> throw new IllegalStateException("Unknown phase" + phase);
            }
        });
        BorderPane root = new BorderPane();
        root.setCenter(activeRegion);
        root.setBottom(createButtonPanel(activeRegion));
        Scene scene = new Scene(root, consts.WIDTH, consts.HEIGHT); // Размер окна
        stage.setScene(scene);
    }

    private void addNode(Pane activeRegion, MouseEvent event) {
        Switch circle = new Switch(consts.SIZE_ROUTER, activeRegion.getChildren().size(), event.getX(), event.getY(), context);
        activeRegion.getChildren().add(circle); // Добавляем круг на сцену
    }

    private HBox createButtonPanel(Pane activeRegion) {
        HBox panel = new HBox(10);
        Text text = new Text("Добавьте узлы коммутаторы");
        Button nextButton = new Button("Далее");
        Text simulationNowText = new Text();
        nextButton.setOnAction(e -> {
            switch (phase) {
                case 1 -> {
                    text.setText("Добавьте связи между коммутаторами");
                    phase++;
                }
                case 2 -> {
                    text.setText("Поиск корней");
                    phase++;
                    locationService.startSTP(activeRegion);
                }
                case 3 -> {
                    locationService.distanceToRoot(activeRegion);
                    phase++;
                    text.setText("Поиск расстояния до корня");
                    //todo сделать перезапуск
                }
                case 4 -> {
//                    phase = 2;
//                    text.setText("Добавьте связи между коммутаторами");
                }
                default -> throw new IllegalStateException("Unknown phase" + phase);
            }
        });
        panel.getChildren().addAll(text, nextButton, simulationNowText);
        return panel;
    }


}