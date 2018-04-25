package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author j3kaiii
 */
public class Dungeon {
    private int length, height, moves;
    private String[][] field;
    private boolean vampiresMove;
    private List<Vampire> vamps;
    private Person player;
    private Ui ui;

    public Dungeon(int length, int height, int vampires, int moves, boolean vampiresMove) {
        //основные переменные
        this.vampiresMove = vampiresMove;
        this.length = length;
        this.height = height;
        this.moves = moves;
        
        //создаем игровое поле
        field = new String[length][height];
        initField();
        
        //создаем игрока в координатах 0,0
        player = new Player();
        field[0][0] = "@";
        
        //создаем стайку вампиров в случайных координатах
        vamps = new ArrayList<Vampire>();
        initVamps(vampires);
        
        //через UI будем выводить инфу, рисовать поле и получать команды игрока
        ui = new Ui((Player) player, vamps, field, length, height);
        
    }
    
    public void run() {
       //основной цикл
       //условия выхода: закончились moves или все вапиры убиты
       while(moves > 0) {
           //выводим статус игрока, вампиров, рисуем поле
           ui.status(moves);
           
           //получаем команду игрока
           String command = ui.getCommand();
           
           //рассчитываем новое положение игрока и вампиров
           makeMoves(command);
           
           //теперь у игрока и какого-нибудь вампира могут совпадать координаты
           //нужно проверить
           //если совпадают, вампир мертв, убираем из списка
           checkXY();
           
           //ВНИМАНИЕ! если вампиров не осталось, выходим из подземелья
           if (vamps.isEmpty()) {
               ui.youWin();
               break;
           }
           
           //размещаем игрока и вампиров по полю
           fillTheField();
           
           moves--;
       }
       
       if (!vamps.isEmpty()) {
           ui.youLose();
       }
       
       //цикл завершен
    }


    private void initField() {
        for(int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = ".";
            }
        }
    }

    private void initVamps(int vampCount) {
        for (int i = 0; i < vampCount; i++) {
            while(true) {
                int x = new Random().nextInt(length);
                int y = new Random().nextInt(height);
                if (field[x][y].equals(".")) {
                    field[x][y] = "v";
                    Vampire v = new Vampire(x, y);
                    vamps.add(v);
                    break;
                }
            }
        }
    }

    private void makeMoves(String command) {
        //команда игрока передается в виде строки
        //в которой каждая буква указывает шаг в какую-либо сторону
        for(int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            switch(c) {
                case 'w': moveUp(player);
                break;
                case 's': moveDown(player);
                break;
                case 'a': moveLeft(player);
                break;
                case 'd': moveRight(player);
                default: break;
            }
            //игрок сделал ход, теперь двигаем вампиров
            if (vampiresMove) moveVamps();
        }
    }

    public void moveUp(Person p) {
        if(p.getY() > 0) { //есть куда шагать?
            if (p.getName().equals("@") //если это игрок, может идти
                 ||                     //или если это вапм, то...
                !field[p.getX()][p.getY() - 1].equals("v")) { //шагает только если поле не занято другим вампиром
                    p.setY(p.getY() - 1);
            }
        }
    }

    private void moveDown(Person p) {
        if(p.getY() < height - 1) {
            if (p.getName().equals("@") //если это игрок, может идти
                 ||                     //или если это вапм, то...
                !field[p.getX()][p.getY() + 1].equals("v")) { //шагает только если поле не занято другим вампиром
                    p.setY(p.getY() + 1);
            }
        }
    }

    private void moveLeft(Person p) {
        if(p.getX() > 0) { //есть куда шагать?
            if (p.getName().equals("@") //если это игрок, может идти
                 ||                     //или если это вапм, то...
                !field[p.getX() - 1][p.getY()].equals("v")) { //шагает только если поле не занято другим вампиром
                    p.setX(p.getX() - 1);
            }
        }
    }

    private void moveRight(Person p) {
        if(p.getX() < length - 1) { //есть куда шагать?
            if (p.getName().equals("@") //если это игрок, может идти
                 ||                     //или если это вапм, то...
                !field[p.getX() + 1][p.getY()].equals("v")) { //шагает только если поле не занято другим вампиром
                    p.setX(p.getX() + 1);
            }
        }
    }

    private void moveVamps() {
        //для каждого вампа алгоритм одинаковый
        for(Vampire v : vamps) {
            //случайно выбираем направление
            int moveDirection = new Random().nextInt(3);
            switch(moveDirection) {
                case 0: moveUp(v);
                break;
                case 1: moveDown(v);
                break;
                case 2: moveLeft(v);
                break;
                case 3: moveRight(v);
                break;
            }
        }
    }

    private void checkXY() {
        //готовим список смертников
        ArrayList<Vampire> toRemove = new ArrayList<Vampire>();
        //сверяем координаты каждого вампа с игроком
        for (Vampire v : vamps) {
            if (v.getX() == player.getX() && v.getY() == player.getY()) {
                toRemove.add(v);
            }
        }
        //удаляем вампиров в списке смертников из списка вампиров
        if (!toRemove.isEmpty()) {
            vamps.removeAll(toRemove);
        }
    }

    private void fillTheField() {
        //сначала зарисуем все точками
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = ".";
            }
        }
        
        //теперь отрисовываем игрока
        field[player.getX()][player.getY()] = "@";
        
        //и всех вампиров
        for (Vampire v : vamps) {
            field[v.getX()][v.getY()] = "v";
        }
    }

}
