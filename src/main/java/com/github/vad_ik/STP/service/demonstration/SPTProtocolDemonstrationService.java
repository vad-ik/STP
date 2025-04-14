package com.github.vad_ik.STP.service.demonstration;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.Switch;
import com.github.vad_ik.STP.service.demonstration.step.DistanceToRoot;
import com.github.vad_ik.STP.service.demonstration.step.FindRootService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Scope("prototype")  // Каждый getBean() создает новый экземпляр
public class SPTProtocolDemonstrationService {

    //STP
    private boolean root = false;
    private boolean signalReceived = false;
    private int rootID;
    private double lenPathToRoot;

    private WindowConstantHolder consts;
    FindRootService findRootService;
    DistanceToRoot distanceToRoot;

    public SPTProtocolDemonstrationService(WindowConstantHolder consts, FindRootService findRootService, DistanceToRoot distanceToRoot) {
        this.consts = consts;
        this.findRootService = findRootService;
        this.distanceToRoot = distanceToRoot;
        rootID = consts.MAX_INT;
        lenPathToRoot = consts.MAX_INT;
    }


    public void startSTP(Switch node) {
        findRootService.startSTP(node);
    }

    public void distanceToRoot(Switch aSwitch) {
        distanceToRoot.getDistanceToRoot(aSwitch);
    }
}
