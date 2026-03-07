package testing.lab1.task3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChildTest {
    @Test
    void childAfterInit_hasNoLocation() {
        Child child = new Child(Size.HUGE);
        assertNull(child.getLocation());
    }

    @Test
    void childInitWithNullSize_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Child(null));
    }

    @Test
    void fromNoLocationJumpOnSand_movesChildToSand() {
        Child child = new Child(Size.HUGE);
        Sand sand = new Sand();

        child.jumpOn(sand);

        assertEquals(sand, child.getLocation());
    }

    @Test
    void jumpOnNullSand_throwsIllegalArgumentException() {
        Child child = new Child(Size.HUGE);

        assertThrows(IllegalArgumentException.class, () -> child.jumpOn(null));
    }

    @Test
    void fromOnSandJumpOnAnotherSand_changesLocationToNewSand() {
        Child child = new Child(Size.HUGE);
        Sand firstSand = new Sand();
        Sand secondSand = new Sand();

        child.jumpOn(firstSand);
        child.jumpOn(secondSand);

        assertEquals(secondSand, child.getLocation());
    }
}
