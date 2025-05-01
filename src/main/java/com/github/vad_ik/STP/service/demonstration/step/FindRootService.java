package com.github.vad_ik.STP.service.demonstration.step;


import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchModel;
import com.github.vad_ik.STP.service.demonstration.step.queue.VisualizationQueueFindRoot;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindRootService {

    private final WindowConstantHolder consts;
    private final VisualizationQueueFindRoot queue;

    @Autowired
    public FindRootService(WindowConstantHolder windowConstantHolder, VisualizationQueueFindRoot queue) {
        this.consts = windowConstantHolder;
        this.queue = queue;
    }

    private void onPath(SwitchModel node, ConnectionRouter lastConnection) {
        if (node.isDisable()){
            return;
        }
        for (ConnectionRouter connectionRouter : node.getActiveConnection()) {
           
            connectionRouter.activateAnimation(connectionRouter.getModel2() == node);
        }
        PauseTransition delay = new PauseTransition(Duration.millis(consts.TIME_ANIMATION));
        delay.setOnFinished(event -> offPath(node,lastConnection));
        delay.play();

    }

    private void offPath(SwitchModel node, ConnectionRouter lastConnection) {
        for (ConnectionRouter connectionRouter : node.getActiveConnection()) {
            while (connectionRouter.getChildren().size() > 1) {
                connectionRouter.offAnimation();
            }
        }
        setRoot(node,lastConnection);
    }

    private void setRoot(SwitchModel node, ConnectionRouter lastConnection) {
        for (ConnectionRouter connectionRouter : node.getActiveConnection()) {
          if (connectToOff(connectionRouter)) {
              int minRootID = Math.min(
                      connectionRouter.getRootIDSwitch(1),
                      connectionRouter.getRootIDSwitch(2));

              if (connectionRouter.getRootIDSwitch(1) < node.getRootID() ||
                      connectionRouter.getRootIDSwitch(2) < node.getRootID()) {
                  node.getDemonstration().setRoot(false);
              }
              node.getDemonstration().setRootID(minRootID);
          }
        }
        for (ConnectionRouter connectionRouter : node.getActiveConnection()) {
            if (connectToOff(connectionRouter)) {
                if (lastConnection != connectionRouter) {
                    if (node == connectionRouter.getModel1()) {
                        queue.addNode(connectionRouter.getModel2(), node.getRootID(), connectionRouter);
                    } else {
                        queue.addNode(connectionRouter.getModel1(), node.getRootID(), connectionRouter);
                    }
                }
            }
        }
        node.setNodeType(node.getDemonstration().isRoot());
    }
private boolean connectToOff(ConnectionRouter connectionRouter){
        return !connectionRouter.getConnectedNode1().getSwitchModel().isDisable()&&
                !connectionRouter.getConnectedNode2().getSwitchModel().isDisable();
}
    public void startSTP(SwitchModel node, ConnectionRouter lastConnection) {
        node.getDemonstration().setRootID(node.getRouterID());
        node.getDemonstration().setRoot(true);
        onPath(node,lastConnection);
    }
}


