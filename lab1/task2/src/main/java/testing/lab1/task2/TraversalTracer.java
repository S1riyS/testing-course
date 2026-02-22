package testing.lab1.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TraversalTracer {

    private final List<TracePoint> sequence = new ArrayList<>();

    public void hit(TracePoint point) {
        sequence.add(point);
    }

    public List<TracePoint> getSequence() {
        return Collections.unmodifiableList(new ArrayList<>(sequence));
    }

    public void clear() {
        sequence.clear();
    }
}
