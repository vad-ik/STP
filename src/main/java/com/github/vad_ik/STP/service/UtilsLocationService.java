package com.github.vad_ik.STP.service;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.Switch;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilsLocationService {

    private final WindowConstantHolder consts;
    private Switch connect;

    @Autowired
    public UtilsLocationService(WindowConstantHolder consts) {
        this.consts = consts;
    }

    public Switch searchForTheNearestNode(Pane activeRegion, double xClick, double yClick) {
        double min = consts.SIZE_ROUTER;
        Switch router = null;
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof Switch) {
                double xLen = ((Switch) activeRegion.getChildren().get(i)).getX() - xClick;
                double yLen = ((Switch) activeRegion.getChildren().get(i)).getY() - yClick;
                double len = Math.sqrt(yLen * yLen + xLen * xLen);
                if (min > len) {
                    min = len;
                    router = (Switch) activeRegion.getChildren().get(i);
                }
            }
        }
        if (router != null) {

            router.getCircle().setStroke(Color.GREEN);
            router.getCircle().setStrokeWidth((double) consts.SIZE_ROUTER / 2);
        }
        return router;
    }

    public void addConnection(Pane activeRegion, MouseEvent event) {
        if (connect == null) {
            connect = searchForTheNearestNode(activeRegion, event.getX(), event.getY());
            return;
        }
        Switch connect2 = searchForTheNearestNode(activeRegion, event.getX(), event.getY());
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
                connect.getConnection().add(connectionRouter);
                connect2.getConnection().add(connectionRouter);
                connect.getConnectionToRoot().add(false);
                connect2.getConnectionToRoot().add(false);
            }
            connect.getCircle().setStrokeWidth(0);
            connect2.getCircle().setStrokeWidth(0);
        }
        connect = null;

    }

    public void startSTP(Pane activeRegion) {
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof Switch) {
                ((Switch) activeRegion.getChildren().get(i)).startSTP(consts.MAX_INT - 1);
            }
        }
    }

    public void distanceToRoot(Pane activeRegion) {
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof Switch &&
                    ((Switch) activeRegion.getChildren().get(i)).getDemonstration().isRoot()
            ) {
                ((Switch) activeRegion.getChildren().get(i)).getDemonstration().setLenPathToRoot(0);
                ((Switch) activeRegion.getChildren().get(i)).distanceToRoot();
            }
        }
    }
}
