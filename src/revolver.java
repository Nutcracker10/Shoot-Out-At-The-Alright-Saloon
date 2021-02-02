public class revolver {
    int capacity, damage;

    public revolver() {
        this.capacity = 6;
        this.damage = 5;
    }

    public void fire() {
        if ( capacity > 0 )
         this.capacity--;
    }

    public void reload() {
        this.capacity++;
    }

    public int deliverDamage() {
        return damage;
    }
}
