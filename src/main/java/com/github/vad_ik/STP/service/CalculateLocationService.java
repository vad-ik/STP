package com.github.vad_ik.STP.service;

import com.github.vad_ik.STP.graphics.myNode.Router;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import static com.github.vad_ik.STP.graphics.utils.WindowConstantHolder.SIZE_ROUTER;

@Service
public class CalculateLocationService {

    public Router findConnect(Pane activeRegion, double xClick, double yClick) {
        double min = SIZE_ROUTER;
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
            router.getCircle().setStrokeWidth((double) SIZE_ROUTER / 2);
        }
        return router;
    }
}
