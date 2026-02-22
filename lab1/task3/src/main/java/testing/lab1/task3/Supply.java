package testing.lab1.task3;

import java.util.Collections;
import java.util.List;

public class Supply {
    private final List<ReinforcedFence> fences;

    public Supply(List<ReinforcedFence> fences) {
        if (fences == null || fences.isEmpty()) {
            throw new IllegalArgumentException("Supply must contain at least one fence");
        }
        this.fences = Collections.unmodifiableList(fences);
    }

    public List<ReinforcedFence> getFences() {
        return fences;
    }

    public Freshness getOverallFreshness() {
        boolean allFresh = fences.stream().allMatch(f -> f.getFreshness() == Freshness.FRESH);
        return allFresh ? Freshness.FRESH : Freshness.STALE;
    }

    public int size() {
        return fences.size();
    }
}
