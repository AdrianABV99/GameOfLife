public class ACell {
    private boolean full;// ate 10 times
    private boolean satieted;
    private int numberOfEatingTimes;
    private int age;

    private int satietyDuration;
    private int starvationLevel;
    private int maxSatietyDuration;
    private int maxStarvationLevel;

    //coord
    private int x;
    private int y;

    public ACell(int x, int y, int satietyDuration, int starvationLevel) {
        this.x = x;
        this.y = y;

        this.full = false;
        this.satieted = false;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = satietyDuration;
        this.maxStarvationLevel = starvationLevel;

        this.satietyDuration = 0;
        this.starvationLevel = 0;
    }

    public ACell() {
        this.full = false;
        this.satieted = false;
        this.numberOfEatingTimes = 0;
        this.age = 0;

        this.maxSatietyDuration = 5;
        this.maxStarvationLevel = 8;

        this.satietyDuration = 0;
        this.starvationLevel = 0;
    }

    public int ageCell()
    {
        age++;

        if(satietyDuration > 0) {
            satietyDuration--;
            if(satietyDuration == 0)
            {
                satieted = false;
            }
        }
        else
        {
            satieted = false;
            starvationLevel++;
        }

        return age;
    }

    public int eatFood()
    {
        numberOfEatingTimes++;
        satietyDuration = maxSatietyDuration;
        starvationLevel = 0;
        if(numberOfEatingTimes >= 10) full = true;

        return numberOfEatingTimes;
    }

    public boolean isFull()
    {
        return full;
    }

    public boolean isSatieted()
    {
        return satieted;
    }





}
