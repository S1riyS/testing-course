package testing.lab1.task3;

public class ReinforcedFence {
    private final boolean reinforced = true;
    private final Freshness freshness;

    public ReinforcedFence(Freshness freshness) {
        if (freshness == null) {
            throw new IllegalArgumentException("Freshness cannot be null");
        }
        this.freshness = freshness;
    }

    public boolean isReinforced() {
        return reinforced;
    }

    public Freshness getFreshness() {
        return freshness;
    }
}
