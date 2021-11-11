public class ACell {
    private boolean full;// ate 10 times
    private int numberOfEatingTimes;
    private int age;

    private int satietyDuration;
    private int starvationLevel;
    private int maxSatietyDuration;
    private int maxStarvationLevel;

    private Coordinates coord;

    public ACell(int x, int y, int satietyDuration, int starvationLevel) {
        coord = new Coordinates(x,y);

        this.full = false;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = satietyDuration;
        this.maxStarvationLevel = starvationLevel;

        this.satietyDuration = satietyDuration;
        this.starvationLevel = 0;
    }

    public ACell(int x, int y) {
        coord = new Coordinates(x,y);
        this.full = false;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = 5;
        this.maxStarvationLevel = 13;

        this.satietyDuration = 5;
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
            satietyDuration = maxSatietyDuration;
            starvationLevel = 0;
            if (numberOfEatingTimes >= 10) full = true;

            return numberOfEatingTimes;
        }
        else
        {
            return 0;
        }
    }

    public boolean isFull()
    {
        return full;
    }

    public boolean isSatiated()
    {
        return satietyDuration > 0;
    }


    public boolean isAlive()
    {
        if(starvationLevel == maxStarvationLevel) return false;
        else return true;
    }

    public int getStarvationLevel() {
        return starvationLevel;
    }
}
