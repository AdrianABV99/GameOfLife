import java.util.Objects;

abstract public class Cell {
    private char type;
    private int fullness;// ate 10 times
    private int numberOfEatingTimes;
    private int age;

    private int satietyDuration;
    private int starvationLevel;
    private final int maxSatietyDuration;
    private final int maxStarvationLevel;

    private Coordinates coord;

    public Cell(int x, int y, int satietyDuration, int starvationLevel) {

        coord = new Coordinates(x,y);

        this.fullness = 0;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = satietyDuration;
        this.maxStarvationLevel = starvationLevel;

        this.satietyDuration = satietyDuration;
        this.starvationLevel = 0;
    }

    public Cell(int x, int y) {


        coord = new Coordinates(x,y);
        this.fullness = 0;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = 3;
        this.maxStarvationLevel = 18;

        this.satietyDuration = maxSatietyDuration;
        this.starvationLevel = 0;
    }

    public void setType(char t)
    {
        type = t;
    }

    public int ageCell()
    {
        age++;

        if(satietyDuration > 0) {
            satietyDuration--;
        }
        else
        {
            starvationLevel++;
        }

        return age;
    }

    public int moveCell(int x, int y)
    {
        coord.changeCoord(x,y);
        return ageCell();
    }

    public Coordinates getCoord(){return coord;}

    public int getX()
    {
        return coord.getX();
    }

    public int getY()
    {
        return coord.getY();
    }

    public int eatFood(int food)
    {
        if(food>0) {
            numberOfEatingTimes += food;
            fullness +=food;
            satietyDuration = maxSatietyDuration;
            starvationLevel = 0;


            return numberOfEatingTimes;
        }
        else
        {
            return 0;
        }
    }

    public boolean isFull()
    {
        return fullness>=10;
    }

    public int getFullness()
    {
        return fullness;
    }

    public void resetFullness()
    {
        fullness=0;
    }

    public boolean isAlive()
    {
        return starvationLevel != maxStarvationLevel;
    }

    public int getStarvationLevel() {
        return starvationLevel;
    }

    public int getSatiationLevel() { return satietyDuration; }

    public void resetSatiety()
    {
        satietyDuration=0;
    }

    public char getType() {
        return type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return coord.equals(cell.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord);
    }

    public abstract  boolean canReproduce();

    public String toString() {
        return this.type + " " + this.hashCode() + " " + this.numberOfEatingTimes;
    }
}

