package util;

public class Revolver {
    private int capacity;

    public Revolver() {
        this.capacity = 6;
    }

    public boolean canfire() {
        if ( capacity > 0 )
            return true;
        else
            return false;
    }

    public void fired() {
        this.capacity--;
    }

    public void reload() {
        if ( capacity < 6 )
            this.capacity++;
        // else "thud"
    }

    public int getCapacity() { return this.capacity;}
}
