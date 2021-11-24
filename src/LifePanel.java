import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.*;


public class LifePanel extends JPanel implements ActionListener{

    int xPanel = 1300; int yPanel = 700;
    int size = 10;
    int xWidth = xPanel / size;
    int yHeight = yPanel / size;
    int[][] life = new int[xWidth][yHeight];




    boolean starts = true;
    boolean pause = false;

    ArrayList<Coordinates> foodCoord = new ArrayList<>();
    ArrayList<Cell> cellA = new ArrayList<>();
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


        if (starts){
            for(int x = 0; x<life.length/2; x++){
                for (int y = 0;y< (yHeight)/2; y++){
                    if((x*y)%44==1){
                        foodCoord.add(new Coordinates(x,y));
                    }
                }
            }
            starts  = false;
        }
    }

    private void display(Graphics g){
        g.setColor(Color.GREEN);


        for(int x = 0; x<noCells; x++){
            if(cellA.get(x).isAlive()) g.fillRect(cellA.get(x).getX()*size,cellA.get(x).getY()*size,size,size);

        }

        g.setColor(Color.BLUE);
        for (Coordinates coordinates : foodCoord) {
            g.fillRect(coordinates.getX() * size, coordinates.getY() * size, size, size);

        }

    }

    private int checkForFood(int x, int y)
    {
        int noFood = 0;
        int auxX, auxY;

        for(int i=-1; i<=1; i++)
            for(int j=-1;j<=1;j++)
            {
                if(!(i==0 && j==0))
                {
                    auxX = (x + xWidth - i) % xWidth;
                    auxY = (y + yHeight - j) % yHeight;
                    if(foodCoord.contains(new Coordinates(auxX,auxY))) {
                        noFood += 1;
                        foodCoord.remove(new Coordinates(auxX,auxY));
                    }
                }
            }


        return noFood;
    }

    private boolean checkCell(int i){
        int x = cellA.get(i).getX();
        int y = cellA.get(i).getY();

        int food;
        if((food = checkForFood(x,y))!=0) {
            cellA.get(i).eatFood(food);
        }


        if( cellA.get(i).canReproduce() )
        {
            spawnCellAround(cellA.get(i).getCoord());
            cellA.get(i).resetFullness();
            cellA.get(i).resetSatiety();
        }

        return cellA.get(i).isAlive();
    }

    private ArrayList<Coordinates> checkSpaceAround(Coordinates coord)
    {
        ArrayList<Coordinates> freeSpace = new ArrayList<>();

        for(int i=-1; i<=1; i++)
            for(int j=-1;j<=1;j++)
            {
                if(!(i==0 && j==0))
                {
                    int auxX = coord.getX() +i;
                    int auxY = coord.getY() +j;
                    Coordinates auxCoord = new Coordinates(auxX,auxY);
                    if(!(foodCoord.contains(auxCoord)
                        ||  cellA.contains(auxCoord) ))
                    {
                        freeSpace.add(auxCoord);
                    }
                }
            }

        return freeSpace;

    }



    public void actionPerformed(ActionEvent e){
        boolean alive ;

        if(!pause) {


            for(int i = 0; i<noCells; i++){

                int cellX = cellA.get(i).getX();
                int cellY = cellA.get(i).getY();

                alive = checkCell(i);
                if (alive) {

                    if(cellA.get(i).getSatiationLevel()>0) cellA.get(i).ageCell();
                    else {

                        Coordinates foodC = closestFood(cellA.get(i).getCoord());
                        if(foodC == null) cellA.get(i).ageCell();
                        else {//move cell
                            int directionX = (int) signum(foodC.getX() - cellX);
                            int directionY = (int) signum(foodC.getY() - cellY);
                            cellA.get(i).moveCell(cellX + directionX, cellY + directionY);
                        }
                    }
                }
                //died
                else {
                    noCells--;
                    Random random = new Random();
                    int randomNumber = random.nextInt(6 - 1) + 1;
                    Coordinates deadCoord = cellA.get(i).getCoord();
                    cellA.remove(cellA.get(i));

                    spawnFoodAround(deadCoord,randomNumber);

                }

            }

        }

        repaint();
    }

    private void spawnCellAt(int x,int y){
        cellA.add(new ACell('A',x/size, y/size));
        noCells++;
    }

    private void spawnCellAt2(int x,int y){
        cellA.add(new ACell('A',x, y));
        noCells++;
    }

    private void spawnCellAround(Coordinates coord)
    {
        ArrayList<Coordinates> freeSpace = checkSpaceAround(coord);
        Collections.shuffle(freeSpace);
        int auxX = freeSpace.get(0).getX();
        int auxY =freeSpace.get(0).getY();
        spawnCellAt2(auxX,auxY);
        cellA.get(noCells-1).resetSatiety();
    }

    private void spawnFoodAround(Coordinates coord, int noFood)
    {
        ArrayList<Coordinates> freeSpace = checkSpaceAround(coord);
        freeSpace.add(coord);
        Collections.shuffle(freeSpace);
        for(int i=-0;i<noFood;i++)
        {
            foodCoord.add(freeSpace.get(i));
        }
    }

    public void putOnPause(){
        pause = true;
    }

    public void putOnContinue(){
        pause = false;
    }

    private Coordinates closestFood(Coordinates cellCoord)
    {
        if(foodCoord.isEmpty()) return null;

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
