package com.github.vad_ik.STP.service.mainStageService;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartSTP {

    private final WindowConstantHolder constants;

    @Autowired
    public StartSTP(WindowConstantHolder consts) {
        this.constants = consts;
    }

    public void startSTP(Pane activeRegion) {
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof SwitchView) {
                ((SwitchView) activeRegion.getChildren().get(i)).getSwitchModel().startSTP(constants.MAX_INT - 1);
            }
        }
    }
}
