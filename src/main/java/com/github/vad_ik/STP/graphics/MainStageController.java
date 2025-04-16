package com.github.vad_ik.STP.graphics;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import com.github.vad_ik.STP.service.PhaseManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainStageController {
    private final WindowConstantHolder constants;
    private final PhaseManager phaseManager;
    private final ObjectProvider<SwitchView> switchViewProvider;

    @Autowired
    public MainStageController(WindowConstantHolder windowConstantHolder,
                               PhaseManager phaseManager,
                               ObjectProvider<SwitchView> switchViewProvider) {
        this.constants = windowConstantHolder;
        this.phaseManager = phaseManager;
        this.switchViewProvider = switchViewProvider;
    }

    public void start(Stage stage) {
        stage.show();
        stage.setTitle(constants.TITLE);
        Pane activeRegion = new Pane(); // Контейнер для кругов

        // Обработчик клика мыши
        phaseManager.setOnPhaseActions(
                event -> addNode(activeRegion, event),  // Действие для фазы 1
                event -> phaseManager.getConnectionManager().addConnection(activeRegion, event)  // Действие для фазы 2
        );
        activeRegion.setOnMouseClicked(phaseManager::handleMouseClick);
        BorderPane root = new BorderPane();
        root.setCenter(activeRegion);
        root.setBottom(createButtonPanel(activeRegion));
        Scene scene = new Scene(root, constants.WIDTH, constants.HEIGHT); // Размер окна
        stage.setScene(scene);
    }

    private void addNode(Pane activeRegion, MouseEvent event) {
        SwitchView circle = switchViewProvider.getObject();  // Получаем экземпляр от Spring
        circle.initialize(
                event.getX(),
                event.getY(),
                constants.SIZE_ROUTER,
                activeRegion.getChildren().size()
        );
        activeRegion.getChildren().add(circle); // Добавляем круг на сцену
    }

    private HBox createButtonPanel(Pane activeRegion) {
        HBox panel = new HBox(10); // TODO мб в константы
        Text text = new Text("Добавьте узлы коммутаторы");// TODO мб в константы
        Button nextButton = new Button("Далее");// TODO мб в константы
        Text simulationNowText = new Text();
        nextButton.setOnAction(e -> {
            phaseManager.handlePhase(text, activeRegion);
        });
        panel.getChildren().addAll(text, nextButton, simulationNowText);
        return panel;
    }
}