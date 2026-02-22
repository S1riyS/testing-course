package testing.lab1.task3;

public class Child implements Observable {
    private final Size size;
    private Location location;

    public Child(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        this.size = size;
    }

    public void jumpOn(Sand sand) {
        if (sand == null) {
            throw new IllegalArgumentException("Sand cannot be null");
        }
        this.location = sand;
    }

    public Size getSize() {
        return size;
    }

    public Location getLocation() {
        return location;
    }
}
