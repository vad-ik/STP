package com.github.vad_ik.STP.graphics.myNode;

import com.github.vad_ik.STP.service.demonstration.SPTProtocolDemonstrationService;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
@Service
@Scope("prototype")  // Каждый вызов getBean() создаёт новый экземпляр
public class SwitchModel {
    private  int routerID;
    private  double x;
    private  double y;
    private final ArrayList<ConnectionRouter> connection = new ArrayList<>();
    private final ArrayList<Boolean> connectionToRoot = new ArrayList<>();
    private final SPTProtocolDemonstrationService demonstration;
    private Double distanceToRoot;
    private boolean nodeType = false;

    public SwitchModel( SPTProtocolDemonstrationService demonstration){
        this.demonstration = demonstration;
    }


    public void initialize( int routerID, double x, double y) {
        this.routerID = routerID;
        this.x = x;
        this.y = y;

    }

    public void startSTP(int rootID) {
        if (demonstration.getRootID() > rootID) {
            demonstration.startSTP(this);
        }
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
}
