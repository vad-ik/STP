package com.github.vad_ik.STP.service.demonstration.step;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.service.mainStageService.FindNode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Service;

@Service
public class DisableNodeService {
    private final FindNode findNode;
    private final WindowConstantHolder constantHolder;

    public DisableNodeService(FindNode findNode, WindowConstantHolder constantHolder) {
        this.findNode = findNode;
        this.constantHolder = constantHolder;
    }

    public void disable(Pane activeRegion, MouseEvent event){
        var node=findNode.searchForTheNearestNode( activeRegion, event.getX() , event.getY() );
        if (node==null){
            //findConnection
        }else {
            node.getSwitchModel().setDisable(!node.getSwitchModel().isDisable());

            node.getCircle().setStroke(node.getSwitchModel().isDisable()?constantHolder.COLOR_NODE_DISABLE:constantHolder.COLOR_NODE);
        }
    }
}
