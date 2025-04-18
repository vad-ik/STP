package com.github.vad_ik.STP.service.demonstration.step;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchModel;
import com.github.vad_ik.STP.service.demonstration.step.queue.VisualizationQueueDistanceToRoot;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DistanceToRoot {
   private final VisualizationQueueDistanceToRoot queueDistanceToRoot;
   private final WindowConstantHolder consts;

    public DistanceToRoot(VisualizationQueueDistanceToRoot queueDistanceToRoot, WindowConstantHolder constant) {
        this.queueDistanceToRoot = queueDistanceToRoot;
        this.consts = constant;
    }
    public void onPath(SwitchModel node) {
        if (node.isDisable()){
            return;
        }
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            connectionRouter.activateAnimation(connectionRouter.getModel2() == node);
        }
        PauseTransition delay = new PauseTransition(Duration.millis(consts.TIME_ANIMATION));
        delay.setOnFinished(event -> offPath(node));
        delay.play();
    }
    private void offPath(SwitchModel node ) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            while (connectionRouter.getChildren().size() > 1) {
                connectionRouter.offAnimation();
            }
        }
        getDistanceToRoot(node);
    }
    private void getDistanceToRoot(SwitchModel node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            SwitchModel node2 = getConnectionSwitch(node, connectionRouter);

            double distance = node.getDemonstration().getLenPathToRoot() + getDistance(node, node2);
            if (node2.getDemonstration().getLenPathToRoot() < distance) {
                if (getOtherPathToRoot(node, connectionRouter)) {
                    connectionRouter.setActive(false);
                    connectionRouter.offLine();
                }
            } else {
                node2.getDemonstration().setLenPathToRoot(distance);
                activeAll(node2);
                delNonShortCat(node, connectionRouter);
                for (int i = 0; i < node2.getConnection().size(); i++) {
                    if (node2.getConnection().get(i) == connectionRouter) {
                        node2.getConnectionToRoot().set(i, true);
                        node2.setDistanceToRoot(distance);
                    } else if (node2.getConnectionToRoot().get(i)) {
                        node2.getConnection().get(i).setActive(false);
                        node2.getConnection().get(i).offLine();
                    }
                }
                queueDistanceToRoot.addNode(node2);
//                node2.distanceToRoot();
            }
        }
    }

    private void delNonShortCat(SwitchModel node, ConnectionRouter connectionRouter) {
        for (int i = 0; i < node.getConnection().size(); i++) {
            if (node.getConnection().get(i) == connectionRouter) {
                node.getConnectionToRoot().set(i, false);
            }
        }
    }

    private boolean getOtherPathToRoot(SwitchModel node, ConnectionRouter connection) {
        for (int i = 0; i < node.getConnectionToRoot().size(); i++) {
            if (node.getConnectionToRoot().get(i)
                    && node.getConnection().get(i) != connection
                    && node.getConnection().get(i).isActive()) {
                return true;
            }
        }
        return false;
    }

    private void activeAll(SwitchModel node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            connectionRouter.setActive(true);
            connectionRouter.onLine();
        }
    }

    private SwitchModel getConnectionSwitch(SwitchModel node, ConnectionRouter connection) {
        if (connection.getModel1() == node) {
            return connection.getModel2();
        } else {
            return connection.getModel1();
        }
    }

    private double getDistance(SwitchModel node1, SwitchModel node2) {
        double distX = node1.getX() - node2.getX();
        double distY = node1.getY() - node2.getY();

        return Math.sqrt(distX * distX + distY * distY);
    }
}
