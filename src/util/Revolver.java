package util;

public class Revolver {
    private int ammo;
    private int capacity;
    private boolean cocked;

    public Revolver() {
        this.capacity = 6;
        this.ammo = capacity;
        this.cocked = false;
    }

    public boolean canfire() {
        if ( ammo > 0 && this.cocked == true )
            return true;
        else
            return false;
    }

    public void cockHammer() {
        this.cocked = true;
    }

    public void fired() {
        this.ammo--;
        this.cocked = false;
    }

    public void reload() {
        if ( ammo < capacity)
            this.ammo++;
        // else "thud"
    }

    public int getCapacity() { return this.capacity;}

    public int getAmmo() {return  this.ammo; }

    public boolean isCocked() { return this.cocked; }
}
