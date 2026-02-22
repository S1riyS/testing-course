package testing.lab1.task3;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Pavement pavement = new Pavement();
        Sand sand = new Sand();
        Sky sky = new Sky();
        UnknownRegions unknownRegions = new UnknownRegions();

        Person personA = new Person();
        Person personB = new Person();
        List<Person> they = List.of(personA, personB);

        they.forEach(p -> p.sitOn(pavement));

        Child hugeChild = new Child(Size.HUGE);
        hugeChild.jumpOn(sand);

        they.forEach(p -> p.watch(hugeChild));
        
        Supply supply = new Supply(List.of(
                new ReinforcedFence(Freshness.FRESH),
                new ReinforcedFence(Freshness.FRESH)));

        Horse wildHorse = new Horse(true);
        wildHorse.pull(supply, sky, unknownRegions);

        System.out.println("personA location: " + personA.getLocation().getName());
        System.out.println("personA emotion: " + personA.getEmotion());
        System.out.println("hugeChild location: " + hugeChild.getLocation().getName());
        System.out.println("wildHorse location: " + wildHorse.getLocation().getName());
        System.out.println("supply freshness: " + supply.getOverallFreshness());
    }
}
