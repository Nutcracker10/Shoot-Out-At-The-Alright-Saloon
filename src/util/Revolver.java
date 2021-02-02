package util;

public class Revolver {
    private int ammo, capacity;

    public Revolver() {
        this.capacity = 6;
        this.ammo = capacity;
    }

    public boolean canfire() {
        if ( ammo > 0 )
            return true;
        else
            return false;
    }

    public void fired() {
        this.ammo--;
    }

    public void reload() {
        if ( ammo < capacity)
            this.ammo++;
        // else "thud"
    }

    public int getCapacity() { return this.capacity;}

    public int getAmmo() {return  this.ammo; }
}
