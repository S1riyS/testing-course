package testing.lab1.task3;

public class Horse implements Observable {
    private final boolean wild;
    private Location location;

    public Horse(boolean wild) {
        this.wild = wild;
    }

    public void pull(Supply cargo, Sky through, UnknownRegions destination) {
        if (cargo == null) {
            throw new IllegalArgumentException("Cargo cannot be null");
        }
        if (through == null) {
            throw new IllegalArgumentException("'Through' cannot be null");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }
        this.location = destination;
    }

    public boolean isWild() {
        return wild;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
