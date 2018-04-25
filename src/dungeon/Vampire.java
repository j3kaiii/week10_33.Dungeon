package dungeon;

/**
 *
 * @author j3kaiii
 */
public class Vampire implements Person{
    private int x, y;
    private String name;

    public Vampire(int x, int y) {
        name = "v";
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return this.name + " " + this.x + " " + this.y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
