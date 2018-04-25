package dungeon;

/**
 *
 * @author j3kaiii
 */
public class Player implements Person{
    private int x, y;
    private String name;

    public Player() {
        name = "@";
        x = 0;
        y = 0;
    }

    

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public String toString() {
        return this.name + " " + x + " " + y;
    }

    void moveUp() {
    }

    void moveDown() {
    }

    void moveRight() {
    }

    void moveLeft() {
    }

    @Override
    public String getName() {
        return this.name;
    }

}
