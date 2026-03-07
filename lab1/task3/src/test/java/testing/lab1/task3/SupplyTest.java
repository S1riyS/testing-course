package testing.lab1.task3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SupplyTest {
    @Test
    void initSupplyWithNullList_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Supply(null));
    }

    @Test
    void initSupplyWithEmptyList_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Supply(List.of()));
    }

    @Test
    void initWithAllFreshFences_overallFreshnessIsFresh() {
        Supply supply = new Supply(List.of(
                new ReinforcedFence(Freshness.FRESH),
                new ReinforcedFence(Freshness.FRESH)));

        assertEquals(Freshness.FRESH, supply.getOverallFreshness());
    }

    @Test
    void initWithAtLeastOneStaleFence_overallFreshnessIsStale() {
        Supply supply = new Supply(List.of(
                new ReinforcedFence(Freshness.FRESH),
                new ReinforcedFence(Freshness.FRESH),
                new ReinforcedFence(Freshness.STALE)));

        assertEquals(Freshness.STALE, supply.getOverallFreshness());
    }
}
