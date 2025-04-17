package com.github.vad_ik.STP.service.demonstration;

import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchModel;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class VisualizationQueue {
    ArrayDeque<Pair<Pair<SwitchModel,Integer>, ConnectionRouter>> queue = new ArrayDeque<>();
    private final List<Consumer<VisualizationQueue>> listeners = new ArrayList<>();

    public void addListener(Consumer<VisualizationQueue> listener) {
        listeners.add(listener);
    }

    public void addNode(SwitchModel node, int id,ConnectionRouter lastConnect) {
        queue.add(new Pair<>(new Pair<>(node,id), lastConnect));
        listeners.forEach(l -> l.accept(this));
    }

    public Pair<Pair<SwitchModel,Integer>, ConnectionRouter> getNext() {
        return queue.remove();
    }
    public boolean isEmpty(){
       return queue.isEmpty();
    }
}
