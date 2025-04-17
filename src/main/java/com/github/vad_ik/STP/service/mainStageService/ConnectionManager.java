package com.github.vad_ik.STP.service.mainStageService;

import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManager {
    private SwitchView selectedNode;
    private final FindNode findNode;
    private final ObjectProvider<ConnectionRouter> ConnectionRouterProvider;

    @Autowired
    public ConnectionManager(FindNode findNode, ObjectProvider<ConnectionRouter> connectionRouterProvider) {
        this.findNode = findNode;
        ConnectionRouterProvider = connectionRouterProvider;
    }

    public void addConnection(Pane activeRegion, MouseEvent event) {
        if (selectedNode == null) {
            selectedNode = findNode.searchForTheNearestNode(activeRegion, event.getX(), event.getY());
            return;
        }
        SwitchView selectedNode2 = findNode.searchForTheNearestNode(activeRegion, event.getX(), event.getY());
        if (selectedNode2 == null) {
            return;
        }
        if (selectedNode2 == selectedNode) {
            selectedNode.getCircle().setStrokeWidth(0);
        } else {
            boolean ans = false;
            for (int i = 0; i < activeRegion.getChildren().size(); i++) {
                if ((activeRegion.getChildren().get(i)) instanceof ConnectionRouter) {
                    ans = ans || ((ConnectionRouter) activeRegion.getChildren().get(i)).isConnected(selectedNode, selectedNode2);
                }
            }
            if (!ans) {
                ConnectionRouter connectionRouter = ConnectionRouterProvider.getObject();
                connectionRouter.init(selectedNode, selectedNode2);
                activeRegion.getChildren().add(connectionRouter);
                selectedNode.getSwitchModel().addConnection(connectionRouter);
                selectedNode2.getSwitchModel().addConnection(connectionRouter);
            }
            selectedNode.getCircle().setStrokeWidth(0);
            selectedNode2.getCircle().setStrokeWidth(0);
        }
        selectedNode = null;
    }
}
