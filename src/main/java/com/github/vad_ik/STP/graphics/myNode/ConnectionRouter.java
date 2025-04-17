package com.github.vad_ik.STP.graphics.myNode;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.utils.FxUtils;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope("prototype")


public class ConnectionRouter extends Pane {
    private SwitchView connectedNode1;
    private SwitchView connectedNode2;
    private boolean active = true;
    private final WindowConstantHolder constant;
    private Line meinLine;

    public ConnectionRouter(WindowConstantHolder constant) {
        this.constant = constant;
    }

    public void init(SwitchView connectedNode1, SwitchView connectedNode2) {
        this.connectedNode1 = connectedNode1;
        this.connectedNode2 = connectedNode2;
        meinLine = FxUtils.getLine(connectedNode1, connectedNode2, constant.WIDTH_LINE, constant.COLOR_LINE);
        getChildren().add(meinLine);
    }

    public boolean isConnected(SwitchView conn1, SwitchView conn2) {
        return conn1 == connectedNode1 && conn2 == connectedNode2 || conn2 == connectedNode1 && conn1 == connectedNode2;
    }

    public void activateAnimation(boolean direction) {
        Line line;
        if (direction) {
            line = FxUtils.getLine(connectedNode1, connectedNode2, constant.WIDTH_DOTTED_LINE, constant.COLOR_DOTTED_LINE);
        } else {
            line = FxUtils.getLine(connectedNode2, connectedNode1, constant.WIDTH_DOTTED_LINE, constant.COLOR_DOTTED_LINE);
        }
        FxUtils.setDotted(line);
        getChildren().add(line);
    }

    public void offAnimation() {
        getChildren().remove(1);
    }

    public void offLine() {
        meinLine.setStroke(constant.COLOR_OFF_LINE);
        meinLine.setStrokeWidth(constant.WIDTH_OFF_LINE);
    }

    public void onLine() {
        meinLine.setStroke(constant.COLOR_LINE);
        meinLine.setStrokeWidth(constant.WIDTH_LINE);
    }

    public SwitchModel getModel1() {
        return connectedNode1.getSwitchModel();
    }

    public SwitchModel getModel2() {
        return connectedNode2.getSwitchModel();
    }

    public int getRootIDSwitch(int switchNum) {
        int ans;
        switch (switchNum) {
            case 1 -> ans = connectedNode1.getDemonstration().getRootID();
            case 2 -> ans = connectedNode2.getDemonstration().getRootID();
            default -> throw new IllegalStateException("Unknown switchNum" + switchNum);
        }
        return ans;
    }
}
