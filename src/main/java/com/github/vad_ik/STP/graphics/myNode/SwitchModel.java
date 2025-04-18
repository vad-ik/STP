package com.github.vad_ik.STP.graphics.myNode;

import com.github.vad_ik.STP.service.demonstration.SPTProtocolDemonstrationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
@Service
@Scope("prototype")  // Каждый вызов getBean() создаёт новый экземпляр
public class SwitchModel {
    private int routerID;
    private double x;
    private double y;
    private final ArrayList<ConnectionRouter> connection = new ArrayList<>();
    private final ArrayList<Boolean> connectionToRoot = new ArrayList<>();
    private final SPTProtocolDemonstrationService demonstration;
    private Double distanceToRoot;
    private boolean nodeType = false;
    @Setter
    private boolean disable=false;

    public SwitchModel(SPTProtocolDemonstrationService demonstration) {
        this.demonstration = demonstration;
    }

    public void initialize(int routerID, double x, double y) {
        this.routerID = routerID;
        this.x = x;
        this.y = y;

    }

    public boolean startSTP(int rootID, ConnectionRouter lastConnection) {
        if (demonstration.getRootID() > rootID) {
            demonstration.startSTP(this,  lastConnection);
            return true;
        }else return false;
    }

    public void distanceToRoot() {
        demonstration.distanceToRoot(this);
    }

    private final List<Consumer<SwitchModel>> listeners = new ArrayList<>();

    public void addListener(Consumer<SwitchModel> listener) {
        listeners.add(listener);
    }

    public void setDistanceToRoot(double distance) {
        this.distanceToRoot = distance;
        listeners.forEach(l -> l.accept(this)); // Уведомляем подписчиков
    }

    public void setNodeType(boolean isRoot) {
        this.nodeType = isRoot;
        listeners.forEach(l -> l.accept(this)); // Уведомляем подписчиков
    }

    public int getRootID() {
        return demonstration.getRootID();
    }

    public void addConnection(ConnectionRouter connectionRouter) {
        connection.add(connectionRouter);
        connectionToRoot.add(false);
    }
}
