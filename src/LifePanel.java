import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.*;


public class LifePanel extends JPanel implements ActionListener{

    enum ClickType{
        food,
        cell
    };

    JButton buttonCell = new JButton();
    JButton buttonFood = new JButton();
    JButton pauseButton = new JButton();

    int xPanel = 1300; int yPanel = 700;
    int size = 10;
    int xWidth = xPanel / size;
    int yHeight = yPanel / size;
    int[][] life = new int[xWidth][yHeight];

    boolean starts = true;
    boolean pause = false;
    ClickType spawn = ClickType.cell;


    ArrayList<Coordinates> foodCoord = new ArrayList<>();
    ArrayList<Cell> cellArray = new ArrayList<>();
    int noCells=0;

    public  LifePanel(){

        // type spawn buttons pannel
        buttonCell.setBounds(xPanel - 130,10,100,50);
        buttonCell.addActionListener(this);
        buttonCell.setText("SET CELL");
        buttonCell.setFocusable(false);
        buttonCell.setBackground(new Color(30,177,0));
        buttonCell.setForeground(new Color(123,1,23));
        this.add(buttonCell);
        buttonFood.setBounds(xPanel - 130,70,100,50);
        buttonFood.addActionListener(this);
        buttonFood.setText("SET FOOD");
        buttonFood.setFocusable(false);
        buttonFood.setBackground(new Color(43,0,255));
        buttonFood.setForeground(new Color(255,255,255));
        this.add(buttonFood);

        pauseButton.setBounds(xPanel - 130,130,100,50);
        pauseButton.addActionListener(this);
        pauseButton.setText("PAUSE");
        pauseButton.setFocusable(false);
        pauseButton.setBackground(Color.YELLOW);
        pauseButton.setForeground(Color.BLACK);
        this.add(pauseButton);


        //--------------------------


        setSize( 1300, 700);
        setLayout(null);
        setBackground(Color.BLACK);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX()/size;
                int y = e.getY()/size;
                if(spawn == ClickType.cell){
                    spawnCellAt(x, y, 's');
                }
                if(spawn == ClickType.food){
                    spawnFoodAt( x, y);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
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
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        setFocusable(true);
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


        for(int x = 0; x<noCells; x++){
            if(cellArray.get(x).isAlive()) {
                if(cellArray.get(x).getType() == 'S') g.setColor(Color.RED);
                else g.setColor(Color.GREEN);
                g.fillRect(cellArray.get(x).getX() * size, cellArray.get(x).getY() * size, size, size);
            }

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
        int x = cellArray.get(i).getX();
        int y = cellArray.get(i).getY();

        int food;
        if((food = checkForFood(x,y))!=0) {
            cellArray.get(i).eatFood(food);
        }


        return cellArray.get(i).isAlive();
    }


    public void actionPerformed(ActionEvent e){
        boolean alive ;


        if(e.getSource() == buttonCell){
            spawn = ClickType.cell;
            System.out.println("push the cell button");
            buttonFood.setForeground(Color.WHITE);
            buttonCell.setForeground(Color.RED);
        }
        if(e.getSource() == buttonFood){
            spawn = ClickType.food;
            System.out.println("push the food button");
            buttonCell.setForeground(Color.WHITE);
            buttonFood.setForeground(Color.RED);
        }
        if(e.getSource() == pauseButton){

            if(!pause){
                pauseButton.setBackground(Color.RED);
                pauseButton.setForeground(Color.BLACK);
                pauseButton.setText("CONTINUE");
                pause = true;
            }else{
                pauseButton.setForeground(Color.BLACK);
                pauseButton.setBackground(Color.YELLOW);
                pauseButton.setText("PAUSE");
                pause = false;
            }

        }

        if(!pause) {
            for(int i = 0; i<noCells; i++){

                int cellX = cellArray.get(i).getX();
                int cellY = cellArray.get(i).getY();

                alive = checkCell(i);
                if (alive) {

                    if(cellArray.get(i).isFull() && cellArray.get(i).getType() == 'S'){

                        Coordinates partnerC = closestPartner(cellArray.get(i).getCoord());



                        if(partnerC == null){
                            if(cellArray.get(i).getSatiationLevel()>0) cellArray.get(i).ageCell();
                            else {

                                Coordinates foodC = closestFood(cellArray.get(i).getCoord());
                                if(foodC == null) cellArray.get(i).ageCell();
                                else {//move cell
                                    int directionX = (int) signum(foodC.getX() - cellX);
                                    int directionY = (int) signum(foodC.getY() - cellY);
                                    if(isEmptySpace(new Coordinates(cellX + directionX,cellY + directionY)))
                                        cellArray.get(i).moveCell(cellX + directionX, cellY + directionY);

                                }
                            }
                        }


                        else {//move cell
                            int distToPartener = Math.max(abs(partnerC.getX() - cellX), abs(partnerC.getY() - cellY));

                            if(distToPartener <= 1)
                            {
                                int otherCelIndex = cellArray.indexOf(new SCell(partnerC.getX(),partnerC.getY()));

                                spawnCellAround(cellArray.get(i).getCoord(),cellArray.get(i).getType());
                                cellArray.get(i).resetFullness();
                                cellArray.get(otherCelIndex).resetFullness();

                            }
                            else {
                                int directionX = (int) signum(partnerC.getX() - cellX);
                                int directionY = (int) signum(partnerC.getY() - cellY);
                                if (isEmptySpace(new Coordinates(cellX + directionX, cellY + directionY)))
                                    cellArray.get(i).moveCell(cellX + directionX, cellY + directionY);
                            }

                        }
                    }
                    else if( cellArray.get(i).isFull() && cellArray.get(i).getType() == 'A')
                    {
                        spawnCellAround(cellArray.get(i).getCoord(),cellArray.get(i).getType());
                        cellArray.get(i).resetFullness();
                        cellArray.get(i).resetSatiety();
                    }
                    else if(cellArray.get(i).getSatiationLevel()>0) cellArray.get(i).ageCell();
                    else {

                        Coordinates foodC = closestFood(cellArray.get(i).getCoord());
                        if(foodC == null) cellArray.get(i).ageCell();
                        else {//move cell
                            int directionX = (int) signum(foodC.getX() - cellX);
                            int directionY = (int) signum(foodC.getY() - cellY);
                            if(isEmptySpace(new Coordinates(cellX + directionX,cellY + directionY)))
                                cellArray.get(i).moveCell(cellX + directionX, cellY + directionY);

                        }
                    }
                }
                //died
                else {
                    noCells--;
                    Random random = new Random();
                    int randomNumber = random.nextInt(6 - 1) + 1;
                    Coordinates deadCoord = cellArray.get(i).getCoord();
                    cellArray.remove(cellArray.get(i));

                    spawnFoodAround(deadCoord,randomNumber);

                }

            }

        }


        repaint();
    }

    private void spawnFoodAt(int x, int y){
        foodCoord.add(new Coordinates(x, y));
    }

    private void spawnCellAt(int x, int y,char cellType){
        if(cellType == 'A' || cellType == 'a') cellArray.add(new ACell(x, y));
        else if(cellType == 'S' || cellType == 's') cellArray.add(new SCell(x, y));
        else return;
        noCells++;
    }

    private void spawnCellAround(Coordinates coord, char type)
    {
        ArrayList<Coordinates> freeSpace = checkSpaceAround(coord);
        Collections.shuffle(freeSpace);
        int auxX = freeSpace.get(0).getX();
        int auxY =freeSpace.get(0).getY();
        if(type == 'S') spawnCellAt(auxX,auxY,'s');
        else spawnCellAt(auxX,auxY,'a');
        cellArray.get(noCells-1).resetSatiety();
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
                    if(isEmptySpace(auxCoord))
                    {
                        freeSpace.add(auxCoord);
                    }
                }
            }

        return freeSpace;

    }

    private boolean isEmptySpace(Coordinates coord)
    {
        return !(foodCoord.contains(coord)
                ||  cellArray.contains(new SCell(coord.getX(),coord.getX()))
                ||  cellArray.contains(new ACell(coord.getX(),coord.getX()))
                );
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


    private Coordinates closestPartner(Coordinates cellCoord)
    {
        ArrayList <Coordinates> possiblePartnerCoord = new ArrayList<>();
        for(Cell c : cellArray)
        {
            if(c.getType()=='S' &&
                    c.isFull() &&
                    c.getCoord() != cellCoord)
            {
                possiblePartnerCoord.add(c.getCoord());
            }
        }

        if(possiblePartnerCoord.isEmpty()) return null;

        Coordinates closestPartner;
        Coordinates auxCoord;

        int cellX = cellCoord.getX();
        int cellY = cellCoord.getY();

        double closestDist;
        double auxDist;

        closestPartner = possiblePartnerCoord.get(0);
        closestDist = Math.max(abs(closestPartner.getX() - cellX), abs(closestPartner.getY() - cellY));

        int i;
        for(i=1; i<possiblePartnerCoord.size(); i++)
        {
            auxCoord=possiblePartnerCoord.get(i);
            auxDist = Math.max(abs(auxCoord.getX() - cellX), abs(auxCoord.getY() - cellY));

            if(auxDist < closestDist) {
                closestPartner = auxCoord;
                closestDist = auxDist;
            }

        }

        return closestPartner;
    }

}
