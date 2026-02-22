package testing.lab1.task3;

public class Person {
    private Location location;
    private Emotion emotion;

    public Person() {
        this.emotion = Emotion.CALM;
    }

    public void sitOn(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        this.location = location;
    }

    public void watch(Observable target) {
        if (target == null) {
            throw new IllegalArgumentException("Observable target cannot be null");
        }

        if (target instanceof Child) {
            Child child = (Child) target;
            if (child.getSize() == Size.HUGE) {
                this.emotion = Emotion.SOMEWHAT_WORRIED;
            }
        } else if (target instanceof Horse) {
            Horse horse = (Horse) target;
            if (horse.isWild()) {
                this.emotion = Emotion.WORRIED;
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }
}
