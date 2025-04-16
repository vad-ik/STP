package com.github.vad_ik.STP.service.demonstration.step;


import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchModel;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindRootService {

    private final WindowConstantHolder consts;

    @Autowired
    public FindRootService(WindowConstantHolder windowConstantHolder) {
        this.consts = windowConstantHolder;
    }

    private void onPath(SwitchModel node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            // TODo сложно читать
            connectionRouter.activateAnimation(connectionRouter.getConnectedNode2().getSwitchModel() == node);
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(consts.TIME_ANIMATION));
        delay.setOnFinished(event -> offPath(node));
        delay.play();
    }

    private void offPath(SwitchModel node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            while (connectionRouter.getChildren().size() > 1) {
                connectionRouter.offAnimation();
            }
        }
        setRoot(node);
    }

    private void setRoot(SwitchModel node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {

            int minRootID = Math.min(connectionRouter.getConnectedNode1().getDemonstration().getRootID(), connectionRouter.getConnectedNode2().getDemonstration().getRootID());

            // TODo сложно читать
            if (connectionRouter.getConnectedNode1().getDemonstration().getRootID() < node.getDemonstration().getRootID() ||
                    connectionRouter.getConnectedNode2().getDemonstration().getRootID() < node.getDemonstration().getRootID()) {
                node.getDemonstration().setRoot(false);
            }
            node.getDemonstration().setRootID(minRootID);
        }
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            // TODo сложно читать
            connectionRouter.getConnectedNode1().getSwitchModel().startSTP(node.getDemonstration().getRootID());
            connectionRouter.getConnectedNode2().getSwitchModel().startSTP(node.getDemonstration().getRootID());
        }
        node.setNodeType(node.getDemonstration().isRoot());
    }

    public void startSTP(SwitchModel node) {
        node.getDemonstration().setRootID(node.getRouterID());
        node.getDemonstration().setRoot(true);
        onPath(node);
    }

}


