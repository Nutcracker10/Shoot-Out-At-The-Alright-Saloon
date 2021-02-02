public class revolver {
    private int capacity, damage;

    public revolver() {
        this.capacity = 6;
        this.damage = 5;
    }

    public void fire() {
        if ( capacity > 0 )
         this.capacity--;
        // else "click"
    }

    public void reload() {
        if ( capacity < 6 )
        this.capacity++;
        // else "thud"
    }

    public int deliverDamage() {
        return damage;
    }
}
