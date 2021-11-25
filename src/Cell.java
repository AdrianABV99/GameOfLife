import java.util.Objects;

abstract public class Cell {
    protected char type;
    protected int fullness;// ate 10 times
    protected int numberOfEatingTimes;
    protected int age;

    protected int satietyDuration;
    protected int starvationLevel;
    protected final int maxSatietyDuration;
    protected final int maxStarvationLevel;

    protected Coordinates coord;

    public Cell(char t, int x, int y, int satietyDuration, int starvationLevel) {

        if(t!='A' || t!='S') t = 'A';
        this.type = t;

        coord = new Coordinates(x,y);

        this.fullness = 0;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = satietyDuration;
        this.maxStarvationLevel = starvationLevel;

        this.satietyDuration = satietyDuration;
        this.starvationLevel = 0;
    }

    public Cell(char t, int x, int y) {

        if(t!='A' || t!='S') t = 'A';
        this.type = t;

        coord = new Coordinates(x,y);
        this.fullness = 0;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = 3;
        this.maxStarvationLevel = 18;

        this.satietyDuration = maxSatietyDuration;
        this.starvationLevel = 0;
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
}

