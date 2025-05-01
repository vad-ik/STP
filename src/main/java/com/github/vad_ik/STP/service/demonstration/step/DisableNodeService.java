package com.github.vad_ik.STP.service.demonstration.step;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.service.mainStageService.FindNode;
import com.github.vad_ik.STP.utils.FxUtils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
        double mouseX = event.getX();
        double mouseY = event.getY();
        var node=findNode.searchForTheNearestNode( activeRegion, mouseX , mouseY );
        if (node==null){
            for (var child  : activeRegion.getChildren()) {
                if (child instanceof ConnectionRouter connect) {
                    Line line=connect.getMeinLine();
                    if (isLineClicked(line, mouseX, mouseY)) {
                        connect.setDis(!connect.isDis());
                        connect.getMeinLine().setStrokeWidth(constantHolder.WIDTH_LINE);
                        connect.getMeinLine().setStroke(connect.isDis()?constantHolder.COLOR_NODE_DISABLE:constantHolder.COLOR_LINE);
                    }
                }
            }
        }else {
            node.getSwitchModel().setDisable(!node.getSwitchModel().isDisable());

            node.getCircle().setStroke(node.getSwitchModel().isDisable()?constantHolder.COLOR_NODE_DISABLE:constantHolder.COLOR_NODE);
        }
    }
    private boolean isLineClicked(Line line, double x, double y) {
        double startX = line.getStartX();
        double startY = line.getStartY();
        double endX = line.getEndX();
        double endY = line.getEndY();

        // Вычисляем расстояние от точки (x, y) до линии
        double distance = pointToLineDistance(startX, startY, endX, endY, x, y);


        double threshold = 5.0;
        return distance <= threshold;
    }

    private double pointToLineDistance(double x1, double y1, double x2, double y2, double clickX, double clickY) {

        // Вычисляем проекцию точки на линию
        double lenSq = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        double t = ((clickX - x1) * (x2 - x1) + (clickY - y1) * (y2 - y1)) / lenSq;

        // Ограничиваем t отрезком [0, 1]
        t = Math.max(0, Math.min(1, t));

        // Находим ближайшую точку на отрезке
        double projX = x1 + t * (x2 - x1);
        double projY = y1 + t * (y2 - y1);

        // Возвращаем расстояние от точки до проекции
        return Math.sqrt((clickX - projX) * (clickX - projX) + (clickY - projY) * (clickY - projY));
    }
}
