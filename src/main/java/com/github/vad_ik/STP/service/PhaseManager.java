package com.github.vad_ik.STP.service;

import com.github.vad_ik.STP.service.demonstration.step.queue.VisualizationQueueDistanceToRoot;
import com.github.vad_ik.STP.service.demonstration.step.queue.VisualizationQueueFindRoot;
import com.github.vad_ik.STP.service.mainStageService.ConnectionManager;
import com.github.vad_ik.STP.service.mainStageService.FindNode;
import com.github.vad_ik.STP.service.mainStageService.StartSTP;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Getter
@Service
public class PhaseManager {
    public int phase = 1;
    private final StartSTP startSTP;
    private final FindNode findNode;
    private final ConnectionManager connectionManager;
    private Consumer<MouseEvent> onPhase1Action;
    private Consumer<MouseEvent> onPhase2Action;
    private Consumer<MouseEvent> onPhase3Action;
    private boolean startStep = false;
    private final VisualizationQueueFindRoot queueFindRoot;
    private final VisualizationQueueDistanceToRoot queueDistanceToRoot;


    @Autowired
    public PhaseManager(StartSTP startSTP, FindNode findNode, ConnectionManager connectionManager, VisualizationQueueFindRoot queueFindRoot, VisualizationQueueDistanceToRoot queueDistanceToRoot) {
        this.startSTP = startSTP;
        this.findNode = findNode;
        this.connectionManager = connectionManager;
        this.queueFindRoot = queueFindRoot;
        this.queueDistanceToRoot = queueDistanceToRoot;
    }

    public void setOnPhaseActions(Consumer<MouseEvent> onPhase1Action,
                                  Consumer<MouseEvent> onPhase2Action,
                                  Consumer<MouseEvent> onPhase3Action) {
        this.onPhase1Action = onPhase1Action;
        this.onPhase2Action = onPhase2Action;
        this.onPhase3Action = onPhase3Action;
    }

    public void handlePhase(Text text, Pane activeRegion) {
        switch (phase) {
            case 1 -> {
                task1(text);
            }
            case 2 -> {
                text.setText("Поиск корней");
                phase++;
                startSTP.setActive(true);
                startSTP.startSTP(activeRegion);
                startStep=false;
            }
            case 3 -> {
                findNode.setActive(true);
                findNode.distanceToRoot(activeRegion);
                phase++;
                text.setText("Поиск расстояния до корня");
                startStep=false;
                //todo сделать перезапуск
            }
            case 4 -> {
//                    phase = 2;
//                    text.setText("Добавьте связи между коммутаторами");
            }
            default -> throw new IllegalStateException("Unknown phase" + phase);
        }
    }

    public void handleStep(Text text, Pane activeRegion) {
        switch (phase) {
            case 1 -> {
                task1(text);
            }
            case 2 -> {
                task2(text, activeRegion);
            }
            case 3 -> {
task3(text, activeRegion);
            }
            case 4 -> {
//                    phase = 2;
//                    text.setText("Добавьте связи между коммутаторами");
            }
            default -> throw new IllegalStateException("Unknown phase" + phase);
        }
    }

    public void task1(Text text) {
        text.setText("Добавьте связи между коммутаторами");
        phase++;
    }

    public void task2(Text text, Pane activeRegion) {

        if (!startStep) {
            startStep=true;
            text.setText("Поиск корней");
            startSTP.setActive(false);
            startSTP.startSTP(activeRegion);
        }
        if (queueFindRoot.isEmpty()) {
            phase++;
            text.setText("Поиск расстояния до корня");
            startStep=false;
            return;
        }
        var nextNode = queueFindRoot.getNext();
        boolean ans = nextNode.getKey().getKey().startSTP(nextNode.getKey().getValue(), nextNode.getValue());
        if (!ans) {
            task2(text, activeRegion);
        }
    }
    public void task3(Text text, Pane activeRegion) {

        if (!startStep) {
            startStep=true;
            text.setText("Поиск расстояния до корня");
            findNode.setActive(false);
            findNode.distanceToRoot(activeRegion);
        }
        if (queueDistanceToRoot.isEmpty()) {
            phase++;
            startStep=false;
            return;
        }
        var nextNode = queueDistanceToRoot.getNext();
        nextNode.distanceToRoot();
        boolean ans=true;
        if (!ans) {
            task3(text, activeRegion);
        }
    }



    public void handleMouseClick(MouseEvent event) {
        if (onPhase1Action == null || onPhase2Action == null) {
            throw new IllegalStateException("onPhase1Action and onPhase2Action mist be initialized");
        }
        switch (phase) {
            case 1 -> onPhase1Action.accept(event);
            case 2 -> onPhase2Action.accept(event);
            case 3 -> {
            }
            case 4 ->{
                onPhase3Action.accept(event);
            }
            default -> throw new IllegalStateException("Unknown phase" + phase);
        }
    }
}
