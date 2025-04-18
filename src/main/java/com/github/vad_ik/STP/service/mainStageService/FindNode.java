package com.github.vad_ik.STP.service.mainStageService;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import com.github.vad_ik.STP.service.demonstration.step.queue.VisualizationQueueDistanceToRoot;
import javafx.scene.layout.Pane;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindNode {
    private final WindowConstantHolder constants;
    private final VisualizationQueueDistanceToRoot queueDistanceToRoot;
    @Setter
    private boolean active = false;

    @Autowired
    public FindNode(WindowConstantHolder consts, VisualizationQueueDistanceToRoot distanceToRoot) {
        this.constants = consts;
        this.queueDistanceToRoot = distanceToRoot;
        queueDistanceToRoot.addListener(updatedModel ->
        {
            if (active) {
                while (!queueDistanceToRoot.isEmpty()) {
                    var nextNode = queueDistanceToRoot.getNext();
                    nextNode.distanceToRoot();
                }
            }
        });
    }

    public SwitchView searchForTheNearestNode(Pane activeRegion, double xClick, double yClick) {
        double min = constants.SIZE_ROUTER;
        SwitchView router = null;
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof SwitchView) {
                double xLen = ((SwitchView) activeRegion.getChildren().get(i)).getX() - xClick;
                double yLen = ((SwitchView) activeRegion.getChildren().get(i)).getY() - yClick;
                double len = Math.sqrt(yLen * yLen + xLen * xLen);
                if (min > len) {
                    min = len;
                    router = (SwitchView) activeRegion.getChildren().get(i);
                }
            }
        }
        if (router != null) {

            router.getCircle().setStroke(constants.COLOR_NODE_ACTIVE);
            router.getCircle().setStrokeWidth((double) constants.SIZE_ROUTER / 2);
        }
        return router;
    }

    public void distanceToRoot(Pane activeRegion) {
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof SwitchView &&
                    ((SwitchView) activeRegion.getChildren().get(i)).getSwitchModel().getDemonstration().isRoot()
            ) {
                ((SwitchView) activeRegion.getChildren().get(i)).getSwitchModel().getDemonstration().setLenPathToRoot(0);

                queueDistanceToRoot.addNode(((SwitchView) activeRegion.getChildren().get(i)).getSwitchModel());
            }
        }
    }
}
