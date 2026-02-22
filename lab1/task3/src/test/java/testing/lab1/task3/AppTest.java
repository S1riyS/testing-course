package testing.lab1.task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private Person personA;
    private Person personB;
    private Child hugeChild;
    private Horse wildHorse;
    private Pavement pavement;
    private Sand sand;
    private Sky sky;
    private UnknownRegions unknownRegions;
    private Supply freshSupply;

    @BeforeEach
    void setUp() {
        pavement = new Pavement();
        sand = new Sand();
        sky = new Sky();
        unknownRegions = new UnknownRegions();

        personA = new Person();
        personB = new Person();
        List<Person> they = List.of(personA, personB);

        they.forEach(p -> p.sitOn(pavement));

        hugeChild = new Child(Size.HUGE);
        hugeChild.jumpOn(sand);

        freshSupply = new Supply(List.of(
                new ReinforcedFence(Freshness.FRESH),
                new ReinforcedFence(Freshness.FRESH)));

        wildHorse = new Horse(true);
        wildHorse.pull(freshSupply, sky, unknownRegions);
    }

    @Test
    void theyAreOnThePavement() {
        assertEquals(pavement, personA.getLocation());
        assertEquals(pavement, personB.getLocation());
    }

    @Test
    void pavementIsCalledPavement() {
        assertEquals("мостовая", pavement.getName());
    }

    @Test
    void watchingHugeChildCausesSomeWorry() {
        personA.watch(hugeChild);
        assertEquals(Emotion.SOMEWHAT_WORRIED, personA.getEmotion());
    }

    @Test
    void worryIsSomeNotExtreme() {
        personA.watch(hugeChild);
        assertNotEquals(Emotion.WORRIED, personA.getEmotion());
    }

    @Test
    void childrenAreHuge() {
        assertEquals(Size.HUGE, hugeChild.getSize());
    }

    @Test
    void childrenAreNotNormalSized() {
        assertNotEquals(Size.NORMAL, hugeChild.getSize());
    }

    @Test
    void childrenJumpOnSand() {
        assertEquals(sand, hugeChild.getLocation());
    }

    @Test
    void sandIsCalledSand() {
        assertEquals("песок", sand.getName());
    }

    @Test
    void childHasNoLocationBeforeJumping() {
        Child freshChild = new Child(Size.HUGE);
        assertNull(freshChild.getLocation());
    }

    @Test
    void horsesAreWild() {
        assertTrue(wildHorse.isWild());
    }

    @Test
    void horsesArriveAtUnknownRegions() {
        assertEquals(unknownRegions, wildHorse.getLocation());
    }

    @Test
    void unknownRegionsIsCalledCorrectly() {
        assertEquals("Неизведанные Области", unknownRegions.getName());
    }

    @Test
    void routeGoesthroughTheSky() {
        assertEquals("небо", sky.getName());
    }

    @Test
    void horseHasNoDestinationBeforePulling() {
        Horse freshHorse = new Horse(true);
        assertNull(freshHorse.getLocation());
    }

    @Test
    void suppliesAreFresh() {
        assertEquals(Freshness.FRESH, freshSupply.getOverallFreshness());
    }

    @Test
    void supplyWithOneStaleFenceIsNotFresh() {
        Supply mixedSupply = new Supply(List.of(
                new ReinforcedFence(Freshness.FRESH),
                new ReinforcedFence(Freshness.STALE)));
        assertNotEquals(Freshness.FRESH, mixedSupply.getOverallFreshness());
    }

    @Test
    void allFencesInSupplyAreReinforced() {
        assertTrue(freshSupply.getFences().stream().allMatch(ReinforcedFence::isReinforced));
    }

    @Test
    void fenceIsAlwaysReinforcedRegardlessOfFreshness() {
        assertTrue(new ReinforcedFence(Freshness.FRESH).isReinforced());
        assertTrue(new ReinforcedFence(Freshness.STALE).isReinforced());
    }
}
