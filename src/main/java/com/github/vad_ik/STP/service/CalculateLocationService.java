package com.github.vad_ik.STP.service;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.Router;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateLocationService {

    private final WindowConstantHolder consts;

    @Autowired
    public CalculateLocationService(WindowConstantHolder consts) {
        this.consts = consts;
    }

    public Router findConnect(Pane activeRegion, double xClick, double yClick) {
        double min = consts.SIZE_ROUTER;
        Router router = null;
        for (int i = 0; i < activeRegion.getChildren().size(); i++) {
            if ((activeRegion.getChildren().get(i)) instanceof Router) {
                double xLen = ((Router) activeRegion.getChildren().get(i)).getX() - xClick;
                double yLen = ((Router) activeRegion.getChildren().get(i)).getY() - yClick;
                double len = Math.sqrt(yLen * yLen + xLen * xLen);
                if (min > len) {
                    min = len;
                    router = (Router) activeRegion.getChildren().get(i);
                }
            }
        }
        if (router != null) {

            router.getCircle().setStroke(Color.GREEN);
            router.getCircle().setStrokeWidth((double) consts.SIZE_ROUTER / 2);
        }
        return router;
    }
}
