import java.io.Serializable;

public class Sensor {
    private String name;

    public Sensor(String name) {
        this.name = name;
    }

    public Sensor() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
