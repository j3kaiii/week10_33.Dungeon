package dungeon;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author j3kaiii
 */
public class Ui {
    private Player p;
    private List<Vampire> v;
    private String[][] field;
    private int length, height;
    private Scanner reader;

    public Ui(Player p, List<Vampire> v, String[][] field, int length, int height) {
        this.p = p;
        this.v = v;
        this.field = field;
        this.length = length;
        this.height = height;
        reader = new Scanner(System.in);
    }

    void status(int moves) {
        System.out.println(moves);          //солько осталось ходов
        System.out.println("");
        System.out.println(p);              //инфа о игроке
        for(Vampire vampire : v) {
            System.out.println(vampire);    //инфа о вампирах
        }
        
        System.out.println("");
        
        for (int i = 0; i < length; i++) {  //отрисовка поля
            for(int j = 0; j < height; j++) {
                System.out.print(field[j][i]);
            }
            System.out.println("");
        }
        
        System.out.println("");
    }

    public String getCommand() {
        return reader.nextLine();
    }

    void youWin() {
        System.out.println("YOU WIN");
    }

    void youLose() {
        System.out.println("YOU LOSE");
    }
    
    

}
