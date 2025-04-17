package com.github.vad_ik.STP.service.demonstration.step;

import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DistanceToRoot {

    public void getDistanceToRoot(SwitchModel node) {
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
                node2.distanceToRoot();
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
