package com.github.vad_ik.STP.service.demonstration;

import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Service;

@Service
public class SPTProtocolDemonstrationService {
    public void activateSTP(Pane activeRegion) {
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof ConnectionRouter) {
                ((ConnectionRouter) activeRegion.getChildren().get(i)).activateAnimation(false);
                ((ConnectionRouter) activeRegion.getChildren().get(i)).activateAnimation(true);

            }
        }
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof ConnectionRouter) {
                ((ConnectionRouter) activeRegion.getChildren().get(i)).offAnimation();
                ((ConnectionRouter) activeRegion.getChildren().get(i)).offAnimation();
            }
        }
    }
}
