import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;


public class LifePanel extends JPanel implements ActionListener{

    int xPanel = 1300; int yPanel = 700;
    int size = 10;
    int xWidth = xPanel / size;
    int yHeight = yPanel / size;
    int[][] life = new int[xWidth][yHeight];
    int[][] beforeLife = new int[xWidth][yHeight];
    boolean starts = true;
    boolean pause = false;


    public  LifePanel(){
        setSize( 1300, 700);
        setLayout(null);
        setBackground(Color.BLACK);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println(x+" "+y);
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
//            for(int x = 0; x<life.length; x++){
//                for (int y = 0;y< (yHeight); y++){
//                    if((int)(Math.random()*5) == 0){
//                        beforeLife[x][y] = 1;
//                    }
//                }
//            }
//            starts  = false;
//        }
    }

    private void display(Graphics g){
        g.setColor(Color.GREEN);

        coppyArray();;

        for(int x = 0; x<life.length; x++){
            for (int y = 0;y< (yHeight); y++){
                if(life[x][y] == 1)
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

    private int check(int x, int y){
        int alive = 0;

        alive += life[(x + xWidth - 1) % xWidth][(y + yHeight -1) % yHeight];
        alive += life[(x + xWidth) % xWidth][(y + yHeight - 1) % yHeight];
        alive += life[(x + xWidth +1 ) % xWidth][(y + yHeight - 1) % yHeight];
        alive += life[(x + xWidth - 1) % xWidth][(y + yHeight) % yHeight];
        alive += life[(x + xWidth + 1) % xWidth][(y + yHeight) % yHeight];
        alive += life[(x + xWidth - 1) % xWidth][(y + yHeight + 1) % yHeight];
        alive += life[(x + xWidth) % xWidth][(y + yHeight + 1) % yHeight];
        alive += life[(x + xWidth + 1) % xWidth][(y + yHeight + 1) % yHeight];

        return alive;
    }

    public void actionPerformed(ActionEvent e){
        int alive ;

        if(!pause) {
            for (int x = 0; x < life.length; x++) {
                for (int y = 0; y < (yHeight); y++) {

                    alive = check(x, y);

                    if (alive == 3) {
                        beforeLife[x][y] = 1;
                    } else if (alive == 2 && life[x][y] == 1) {
                        beforeLife[x][y] = 1;
                    } else {
                        beforeLife[x][y] = 0;
                    }
                }
            }
        }
        repaint();
    }

    private void spawnCellAt(int x,int y){
        life[x/size][y/size] = 1;
        beforeLife[x/size][y/size] = 1;
    }

    public void putOnPause(){
        pause = true;
    }

    public void putOnContinue(){
        pause = false;
    }
}
