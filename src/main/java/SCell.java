import java.util.Objects;

public class SCell extends Cell {

    private boolean nearPartner;

    public SCell(int x, int y, int satietyDuration, int starvationLevel) {
        super(x,y,satietyDuration,starvationLevel);
        this.setType('S');
    }

    public SCell(int x, int y) {
        super(x, y);
        this.setType('S');
    }

    public boolean isNearPartner()
    {
        return nearPartner;
    }

    public void setNearPartner(boolean x)
    {
        nearPartner = x;
    }


    @Override
    public boolean canReproduce() {
        return this.getFullness() >= 10 && nearPartner;
    }


}
