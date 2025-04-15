package com.github.vad_ik.STP.service.mainStageService;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManager {
    private SwitchView connect;
    private final FindNode findNode;

    @Autowired
    public ConnectionManager( FindNode findNode) {
        this.findNode = findNode;
    }


    public void addConnection(Pane activeRegion, MouseEvent event) {
        if (connect == null) {
            connect = findNode.searchForTheNearestNode(activeRegion, event.getX(), event.getY());
            return;
        }
        SwitchView connect2 = findNode.searchForTheNearestNode(activeRegion, event.getX(), event.getY());
        if (connect2 == null) {
            return;
        }
        if (connect2 == connect) {
            connect.getCircle().setStrokeWidth(0);
        } else {
            boolean ans = false;
            for (int i = 0; i < activeRegion.getChildren().size(); i++) {
                if ((activeRegion.getChildren().get(i)) instanceof ConnectionRouter) {
                    ans = ans || ((ConnectionRouter) activeRegion.getChildren().get(i)).isConnected(connect, connect2);
                }
            }
            if (!ans) {
                ConnectionRouter connectionRouter = new ConnectionRouter(connect, connect2);
                activeRegion.getChildren().add(connectionRouter);
                connect.getSwitchModel().getConnection().add(connectionRouter);
                connect2.getSwitchModel().getConnection().add(connectionRouter);
                connect.getSwitchModel().getConnectionToRoot().add(false);
                connect2.getSwitchModel().getConnectionToRoot().add(false);
            }
            connect.getCircle().setStrokeWidth(0);
            connect2.getCircle().setStrokeWidth(0);
        }
        connect = null;

    }




}
