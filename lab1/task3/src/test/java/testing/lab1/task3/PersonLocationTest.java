package testing.lab1.task3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonLocationTest {

    @Test
    void personAfterInit_hasNoLocation() {
        Person person = new Person();
        assertNull(person.getLocation());
    }

    @Test
    void fromNoLocationSitOnSand_setsLocationToSand() {
        Person person = new Person();
        Sand sand = new Sand();

        person.sitOn(sand);

        assertEquals(sand, person.getLocation());
    }

    @Test
    void fromSandSitOnSameSand_throwsIllegalArgumentException() {
        Person person = new Person();
        Sand sand = new Sand();
        person.sitOn(sand);

        assertThrows(IllegalArgumentException.class, () -> person.sitOn(sand));
    }

    @Test
    void fromSandSitOnPavement_changesLocationToPavement() {
        Person person = new Person();
        Sand sand = new Sand();
        Pavement pavement = new Pavement();

        person.sitOn(sand);
        person.sitOn(pavement);

        assertEquals(pavement, person.getLocation());
    }

    @Test
    void fromSandSitOnNull_changesLocationToPavement() {
        Person person = new Person();
        Sand sand = new Sand();

        person.sitOn(sand);

        assertThrows(IllegalArgumentException.class, () -> person.sitOn(null));

    }

    @Test
    void sittingOnNull_throwsIllegalArgumentException() {
        Person person = new Person();

        assertThrows(IllegalArgumentException.class, () -> person.sitOn(null));
    }
}
