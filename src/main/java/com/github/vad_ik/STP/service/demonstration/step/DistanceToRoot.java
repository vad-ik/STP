package com.github.vad_ik.STP.service.demonstration.step;

import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Getter
@Slf4j
public class DistanceToRoot {

    public void getDistanceToRoot(SwitchModel node) {
        for (ConnectionRouter connectionRouter : node.getConnection()) {
            SwitchModel node2 = getСonnectionSwitch(node, connectionRouter);
            log.info("поиск пути до корня от " + node.getRouterID() + " до " + node2.getRouterID());
            double distance = node.getDemonstration().getLenPathToRoot() + getDistance(node, node2);
            if (node2.getDemonstration().getLenPathToRoot() < distance) {

                log.info("возможно есть путь короче:");
                if (getOtherPathToRoot(node, connectionRouter)) {
                    log.info("есть путь короче");
                    connectionRouter.setActive(false);
                    connectionRouter.offLine();
                }

            } else {
                log.info("это кротчайший путь");
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
                log.info("" + node.getConnection().get(i));

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

    private SwitchModel getСonnectionSwitch(SwitchModel node, ConnectionRouter connection) {
        if (connection.getConnectedNode1().getSwitchModel() == node) {
            return connection.getConnectedNode2().getSwitchModel();
        } else {
            return connection.getConnectedNode1().getSwitchModel();
        }
    }

    private double getDistance(SwitchModel node1, SwitchModel node2) {
        double distX = node1.getX() - node2.getX();
        double distY = node1.getY() - node2.getY();

        return Math.sqrt(distX * distX + distY * distY);

    }


}
