import java.time.LocalDateTime;


public class Measurement {
    private Double value;
    private Boolean raining;
    private Sensor sensor;

    public Measurement(Double value, Boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public Measurement() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor.getName() +
                '}';
    }
}
