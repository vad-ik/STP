package com.github.vad_ik.STP.graphics;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import com.github.vad_ik.STP.service.PhaseManager;
import com.github.vad_ik.STP.service.demonstration.step.DisableNodeService;
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
    private final NodeTableUI nodeTableUI;
    private final DisableNodeService disableNodeService;

    @Autowired
    public MainStageController(WindowConstantHolder windowConstantHolder,
                               PhaseManager phaseManager,
                               ObjectProvider<SwitchView> switchViewProvider, NodeTableUI nodeTableUI, DisableNodeService disableNodeService) {
        this.constants = windowConstantHolder;
        this.phaseManager = phaseManager;
        this.switchViewProvider = switchViewProvider;
        this.nodeTableUI = nodeTableUI;
        this.disableNodeService = disableNodeService;
    }

    public void start(Stage stage) {
        stage.show();
        stage.setTitle(constants.TITLE);
        Pane activeRegion = new Pane(); // Контейнер для кругов

        // Обработчик клика мыши
        phaseManager.setOnPhaseActions(
                event -> addNode(activeRegion, event),  // Действие для фазы 1
                event -> phaseManager.getConnectionManager().addConnection(activeRegion, event),  // Действие для фазы 2
                event -> disableNodeService.disable(activeRegion, event)
        );
        activeRegion.setOnMouseClicked(phaseManager::handleMouseClick);
        BorderPane root = new BorderPane();
        root.setCenter(activeRegion);
        root.setBottom(createButtonPanel(activeRegion));
        root.setRight(nodeTableUI);
        Scene scene = new Scene(root, constants.WIDTH, constants.HEIGHT); // Размер окна
        stage.setScene(scene);
//        System.out.println(stage.getHeight());
//        System.out.println(stage.getWidth());
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
        Text text = new Text(constants.ADD_NODE);
        Button nextPhase = new Button(constants.NEXT);
        nextPhase.setOnAction(e -> {
            phaseManager.handlePhase(text, activeRegion);
        });
        Button nextStep = new Button(constants.NEXT_STEP);
        nextStep.setOnAction(e -> {
            phaseManager.handleStep(text, activeRegion);
        });
        panel.getChildren().addAll(text, nextPhase,nextStep);
        return panel;
    }
}