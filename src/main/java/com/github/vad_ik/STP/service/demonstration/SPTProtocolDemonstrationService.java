package com.github.vad_ik.STP.service.demonstration;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.Switch;
import com.github.vad_ik.STP.service.demonstration.step.FindRootService;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Scope("prototype")  // Каждый getBean() создает новый экземпляр
public class SPTProtocolDemonstrationService {

    //STP
    private boolean root=false;
    private boolean signalReceived = false;
    private int rootID=99999;

    @Autowired
    private ApplicationContext context;  // Spring сам внедрит контекст

    FindRootService findRootService ;
    public SPTProtocolDemonstrationService(FindRootService findRootService) {
        this.findRootService = findRootService;
    }


    public void startSTP(Switch node) {
        findRootService.startSTP(node);
    }

    public void distanceToRoot(Switch aSwitch) {
    }
}
