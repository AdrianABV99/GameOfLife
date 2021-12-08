import java.util.Objects;

public class ACell extends Cell {

    public ACell(int x, int y, int satietyDuration, int starvationLevel) {
        super(x,y,satietyDuration,starvationLevel);
        this.setType('A');
    }

    public ACell(int x, int y) {
        super(x, y);
        this.setType('A');
    }

    @Override
    public boolean canReproduce() {
        return this.getFullness() >= 10;
    }

}
