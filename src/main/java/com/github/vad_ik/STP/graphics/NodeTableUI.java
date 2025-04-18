package com.github.vad_ik.STP.graphics;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NodeTableUI extends ScrollPane {
    VBox pane = new VBox(10);
    ArrayList<Label> nodeArray = new ArrayList<>();

    public NodeTableUI() {
        setPrefViewportHeight(100);
        setPrefViewportWidth(250);
        Label cap = new Label("\nid\tкорень\t\tрасстояние до корня");
        pane.getChildren().add(cap);
        setContent(pane);
    }

    public void addNode(int routerID) {
        var textField = new Label("" + routerID);
        nodeArray.add(textField);
        pane.getChildren().add(textField);
    }

    public void setRoot(int id, String isRoot) {
        nodeArray.get(id).setText("" + id + "\t" + isRoot);
    }

    public void setDist(int id, String isRoot, double dist) {
        nodeArray.get(id).setText(id + "\t" + isRoot + "\t" + (int) dist);
    }
}
