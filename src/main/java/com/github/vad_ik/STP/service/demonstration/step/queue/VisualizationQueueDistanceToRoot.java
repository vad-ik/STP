package com.github.vad_ik.STP.service.demonstration.step.queue;

import com.github.vad_ik.STP.graphics.myNode.SwitchModel;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class VisualizationQueueDistanceToRoot {
    ArrayDeque<SwitchModel> queue = new ArrayDeque<>();
    private final List<Consumer<VisualizationQueueDistanceToRoot>> listeners = new ArrayList<>();

    public void addListener(Consumer<VisualizationQueueDistanceToRoot> listener) {
        listeners.add(listener);
    }

    public void addNode(SwitchModel node) {
        queue.add(node);
        listeners.forEach(l -> l.accept(this));
    }

    public SwitchModel getNext() {
        return queue.remove();
    }
    public boolean isEmpty(){
       return queue.isEmpty();
    }
}
