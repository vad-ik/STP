package com.github.vad_ik.STP.service.mainStageService;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import com.github.vad_ik.STP.service.demonstration.step.queue.VisualizationQueueFindRoot;
import javafx.scene.layout.Pane;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartSTP {

    private final WindowConstantHolder constants;
    private final VisualizationQueueFindRoot queue;
    @Setter
    private boolean active = false;

    @Autowired
    public StartSTP(WindowConstantHolder consts, VisualizationQueueFindRoot queue) {
        this.constants = consts;
        this.queue = queue;
        queue.addListener(updatedModel ->
        {
            if (active) {
                while (!queue.isEmpty()) {
                    var nextNode = queue.getNext();
                    nextNode.getKey().getKey().startSTP(nextNode.getKey().getValue(), nextNode.getValue());
                }
            }
        });
    }


    public void startSTP(Pane activeRegion) {

        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof SwitchView) {
                //((SwitchView) activeRegion.getChildren().get(i)).getSwitchModel().startSTP(constants.MAX_INT - 1, null);
                queue.addNode(((SwitchView) activeRegion.getChildren().get(i)).getSwitchModel(), constants.MAX_INT - 1, null);
            }
        }
    }
}
