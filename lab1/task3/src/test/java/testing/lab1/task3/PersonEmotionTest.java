package testing.lab1.task3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonEmotionTest {
    @Test
    void personIsCalmByDefault_afterInit_stateIsCALM() {
        Person person = new Person();
        assertEquals(Emotion.CALM, person.getEmotion());
    }

    @Test
    void fromCalmWatchingHugeChild_transitionsToSomewhatWorried() {
        Person person = new Person();
        Child hugeChild = new Child(Size.HUGE);

        person.watch(hugeChild);

        assertEquals(Emotion.SOMEWHAT_WORRIED, person.getEmotion());
    }

    @Test
    void fromSomewhatWorriedWatchingHugeChildAgain_staysSomewhatWorried() {
        Person person = new Person();
        Child hugeChild = new Child(Size.HUGE);

        person.watch(hugeChild);
        person.watch(hugeChild);

        assertEquals(Emotion.SOMEWHAT_WORRIED, person.getEmotion());
    }

    @Test
    void fromCalmWatchingNormalChild_transitionsToCalm() {
        Person person = new Person();
        Child normalChild = new Child(Size.NORMAL);

        person.watch(normalChild);

        assertEquals(Emotion.CALM, person.getEmotion());
    }

    @Test
    void fromSomewhatWorriedWatchingNormalChild_transitionsToCalm() {
        Person person = new Person();
        Child hugeChild = new Child(Size.HUGE);
        Child normalChild = new Child(Size.NORMAL);

        person.watch(hugeChild);
        person.watch(normalChild);

        assertEquals(Emotion.CALM, person.getEmotion());
    }

    @Test
    void fromWorriedWatchingNormalChild_transitionsToCalm() {
        Person person = new Person();
        Horse wildHorse = new Horse(true);
        Child normalChild = new Child(Size.NORMAL);

        person.watch(wildHorse);
        person.watch(normalChild);

        assertEquals(Emotion.CALM, person.getEmotion());
    }

    @Test
    void fromCalmWatchingWildHorse_transitionsToWorried() {
        Person person = new Person();
        Horse wildHorse = new Horse(true);

        person.watch(wildHorse);

        assertEquals(Emotion.WORRIED, person.getEmotion());
    }

    @Test
    void fromSomewhatWorriedWatchingWildHorse_transitionsToWorried() {
        Person person = new Person();
        Child hugeChild = new Child(Size.HUGE);
        Horse wildHorse = new Horse(true);

        person.watch(hugeChild);
        person.watch(wildHorse);

        assertEquals(Emotion.WORRIED, person.getEmotion());
    }

    @Test
    void fromCalmWatchingTameHorse_staysCalm() {
        Person person = new Person();
        Horse tameHorse = new Horse(false);

        person.watch(tameHorse);

        assertEquals(Emotion.CALM, person.getEmotion());
    }

    @Test
    void watchingNull_throwsIllegalArgumentException() {
        Person person = new Person();

        assertThrows(IllegalArgumentException.class, () -> person.watch(null));
    }
}
