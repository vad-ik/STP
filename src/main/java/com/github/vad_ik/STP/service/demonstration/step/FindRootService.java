package com.github.vad_ik.STP.service.demonstration.step;


import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.Switch;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class FindRootService {

    //STP

    private final WindowConstantHolder consts;

    @Autowired
    public FindRootService(WindowConstantHolder windowConstantHolder) {
        this.consts = windowConstantHolder;
    }

    private void onPath(Switch node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            connectionRouter.activateAnimation(connectionRouter.getConnectedNode2() == node);
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(consts.TIME_ANIMATION));
        delay.setOnFinished(event -> offPath(node));
        delay.play();
    }

    private void offPath(Switch node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            while (connectionRouter.getChildren().size() > 1) {
                connectionRouter.offAnimation();
            }
        }
        setRoot(node);
    }

    private void setRoot(Switch node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {

            int minRootID = Math.min(connectionRouter.getConnectedNode1().getDemonstration().getRootID(), connectionRouter.getConnectedNode2().getDemonstration().getRootID());

            if (connectionRouter.getConnectedNode1().getDemonstration().getRootID() < node.getDemonstration().getRootID() ||
                    connectionRouter.getConnectedNode2().getDemonstration().getRootID() < node.getDemonstration().getRootID()) {
                node.getDemonstration().setRoot(false);
            }
            node.getDemonstration().setRootID(minRootID);
        }
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            connectionRouter.getConnectedNode1().startSTP(node.getDemonstration().getRootID());
            connectionRouter.getConnectedNode2().startSTP(node.getDemonstration().getRootID());
        }
        if (node.getDemonstration().isRoot()) {
            node.getNodeType().setText("корневой коммутатор");
        } else {
            node.getNodeType().setText("не корневой коммутатор, присутствует коммутатор с меньшим ID");
        }
    }

    public void startSTP(Switch node) {
        node.getDemonstration().setRootID(node.getRouterID());
        node.getDemonstration().setRoot(true);

        onPath(node);
    }
}


