package com.github.vad_ik.STP.graphics.clickHandler;

import javafx.scene.input.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MainStageClickHandler {
    private final Supplier<Integer> getPhase;  // Получаем текущую фазу извне
    private final Consumer<MouseEvent> onPhase1Action;
    private final Consumer<MouseEvent> onPhase2Action;

    public MainStageClickHandler(
            Supplier<Integer> getPhase,
            Consumer<MouseEvent> onPhase1Action,
            Consumer<MouseEvent> onPhase2Action
    ) {
        this.getPhase = getPhase;
        this.onPhase1Action = onPhase1Action;
        this.onPhase2Action = onPhase2Action;
    }

    public void handle(MouseEvent event) {
        switch (getPhase.get()) {
            case 1 -> onPhase1Action.accept(event);
            case 2 -> onPhase2Action.accept(event);
            case 3, 4 -> {
            }
            default -> throw new IllegalStateException("Unknown phase" + getPhase.get());
        }
    }
}