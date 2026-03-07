package testing.lab1.task3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HorseTest {
    @Test
    void horseAfterInit_hasNoDestinationAndKeepsWildFlag() {
        Horse wildHorse = new Horse(true);

        assertTrue(wildHorse.isWild());
        assertNull(wildHorse.getLocation());
    }

    @Test
    void fromNoDestinationSuccessfulPull_movesHorseToDestination() {
        Horse horse = new Horse(true);
        Supply cargo = new Supply(List.of(new ReinforcedFence(Freshness.FRESH)));
        Sky sky = new Sky();
        UnknownRegions destination = new UnknownRegions();

        horse.pull(cargo, sky, destination);

        assertEquals(destination, horse.getLocation());
    }

    @Test
    void pullWithNullCargo_throwsIllegalArgumentException() {
        Horse horse = new Horse(true);
        Sky sky = new Sky();
        UnknownRegions destination = new UnknownRegions();

        assertThrows(IllegalArgumentException.class, () -> horse.pull(null, sky, destination));
    }

    @Test
    void pullWithNullSky_throwsIllegalArgumentException() {
        Horse horse = new Horse(true);
        Supply cargo = new Supply(List.of(new ReinforcedFence(Freshness.FRESH)));
        UnknownRegions destination = new UnknownRegions();

        assertThrows(IllegalArgumentException.class, () -> horse.pull(cargo, null, destination));
    }

    @Test
    void pullWithNullDestination_throwsIllegalArgumentException() {
        Horse horse = new Horse(true);
        Supply cargo = new Supply(List.of(new ReinforcedFence(Freshness.FRESH)));
        Sky sky = new Sky();

        assertThrows(IllegalArgumentException.class, () -> horse.pull(cargo, sky, null));
    }
}
