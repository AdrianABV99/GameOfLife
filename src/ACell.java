import java.util.Objects;

public class ACell extends Cell {

    public ACell(char t, int x, int y, int satietyDuration, int starvationLevel) {
        super(t, x,y,satietyDuration,starvationLevel);
    }

    public ACell(char t, int x, int y) {
        super(t, x, y);
    }

    @Override
    public boolean canReproduce() {
        return this.fullness >= 10;
    }

}
