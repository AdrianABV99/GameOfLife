import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static java.lang.Math.*;


public class LifePanel extends JPanel implements ActionListener{

    int xPanel = 1300; int yPanel = 700;
    int size = 10;
    int xWidth = xPanel / size;
    int yHeight = yPanel / size;
    int[][] life = new int[xWidth][yHeight];
    int[][] beforeLife = new int[xWidth][yHeight];

    int[][] food = new int[xWidth][yHeight];
    ArrayList<Coordinates> foodCoord = new ArrayList<>();

    boolean starts = true;
    boolean pause = false;

    ArrayList<ACell> cellA = new ArrayList<>();
    int noCells=0;

    public  LifePanel(){
        setSize( 1300, 700);
        setLayout(null);
        setBackground(Color.BLACK);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
//                System.out.println(x+" "+y);
                spawnCellAt( x, y);
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("Key typed" + e);
                if(!pause && e.getKeyChar() == 'p')
                    pause = true;
                else if (pause && e.getKeyChar() == 'c')
                    pause = false;
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        setFocusable(true);


        System.out.println("FRAME\n");

        new Timer(200, this).start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        grid(g);

        spawn(g);
        display(g);
    }

    private void grid(Graphics g){
        for (int i = 0; i<life.length; i++){
            g.drawLine(0,i*size,xPanel,i*size);
            g.drawLine(i*size,0,i*size,yPanel);
        }
    }

    private void spawn(Graphics g){
//        if (starts){
////            for(int x = 0; x<life.length; x++){
////                for (int y = 0;y< (yHeight); y++){
////                    if((int)(Math.random()*5)%14 == 1){
////                        food[x][y] = 1;
////                    }
////                }
////            }
////            starts  = false;
////        }

        if (starts){
            for(int x = 0; x<life.length; x++){
                for (int y = 0;y< (yHeight); y++){
                    if((x*y)%44==1){
                        food[x][y] = 1;
                        foodCoord.add(new Coordinates(x,y));
                    }
                }
            }
            starts  = false;
        }
    }

    private void display(Graphics g){
        g.setColor(Color.GREEN);

        coppyArray();

        for(int x = 0; x<noCells; x++){
            if(cellA.get(x).isAlive()) g.fillRect(cellA.get(x).getX()*size,cellA.get(x).getY()*size,size,size);

        }

        g.setColor(Color.BLUE);
        for(int x = 0; x<food.length; x++){
            for (int y = 0;y< (yHeight); y++){
                if(food[x][y] == 1)
                    g.fillRect(x*size,y*size,size,size);
            }
        }

    }

    private void coppyArray(){
        for(int x = 0; x<life.length; x++){
            for (int y = 0;y< (yHeight); y++){
                life[x][y] = beforeLife[x][y];
            }
        }
    }

    private int checkForFood(int x, int y)
    {
        int noFood = 0;
        int auxX, auxY;

        auxX = (x + xWidth - 1) % xWidth;
        auxY = (y + yHeight -1) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }

        auxX = (x + xWidth) % xWidth;
        auxY = (y + yHeight -1) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }

        auxX = (x + xWidth + 1) % xWidth;
        auxY = (y + yHeight -1) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }

        auxX = (x + xWidth - 1) % xWidth;
        auxY = (y + yHeight ) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }

        auxX = (x + xWidth + 1) % xWidth;
        auxY = (y + yHeight ) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }

        auxX = (x + xWidth - 1) % xWidth;
        auxY = (y + yHeight + 1) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }

        auxX = (x + xWidth ) % xWidth;
        auxY = (y + yHeight +1) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }

        auxX = (x + xWidth + 1) % xWidth;
        auxY = (y + yHeight + 1) % yHeight;
        if(food[auxX][auxY]==1) {
            noFood += food[auxX][auxY];
            food[auxX][auxY] = 0;
            foodCoord.remove(new Coordinates(auxX,auxY));
        }


        return noFood;
    }

    private boolean check(int i){
        int x = cellA.get(i).getX();
        int y = cellA.get(i).getY();

        cellA.get(i).eatFood(checkForFood(x,y));

        return cellA.get(i).isAlive();
    }

    public void actionPerformed(ActionEvent e){
        boolean alive ;

        if(!pause) {


            for(int i = 0; i<noCells; i++){

                int cellX = cellA.get(i).getX();
                int cellY = cellA.get(i).getY();

                alive = check(i);
                System.out.println("Cell = " + cellA.get(i).getCoord().toString());
                System.out.println("Food = " +  closestFood(cellA.get(i).getCoord()).toString());
                if (alive) {
                    Coordinates foodC = closestFood(cellA.get(i).getCoord());
                    System.out.println(foodC.getX() + " " + foodC.getY() );

                    int directionX = (int) signum(foodC.getX() - cellX);
                    int directionY = (int) signum(foodC.getY() - cellY);
                    System.out.println(directionX + " " + directionY + '\n');

                    cellA.get(i).moveCell(cellX + directionX, cellY + directionY);
                }
                else {
                    noCells--;
                    cellA.remove(cellA.get(i));
                }

            }

        }

        repaint();
    }

    private void spawnCellAt(int x,int y){
        life[x/size][y/size] = 1;
        beforeLife[x/size][y/size] = 1;
        cellA.add(new ACell(x/size, y/size));
        noCells++;
    }

    public void putOnPause(){
        pause = true;
    }

    public void putOnContinue(){
        pause = false;
    }

    private Coordinates closestFood(Coordinates cellCoord)
    {
        Coordinates closestFood;
        Coordinates auxCoord;

        int cellX = cellCoord.getX();
        int cellY = cellCoord.getY();

        double closestDist;
        double auxDist;

        closestFood = foodCoord.get(0);
        closestDist = Math.max(abs(closestFood.getX() - cellX), abs(closestFood.getY() - cellY));

        int i;
        for(i=1; i<foodCoord.size(); i++)
        {
            auxCoord=foodCoord.get(i);
            auxDist = Math.max(abs(auxCoord.getX() - cellX), abs(auxCoord.getY() - cellY));

            if(auxDist < closestDist) {
                closestFood = auxCoord;
                closestDist = auxDist;
            }

        }

        return closestFood;
    }

}
