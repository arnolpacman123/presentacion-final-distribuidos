package models;

import java.io.Serializable;

public class DataClient extends Data implements Serializable {
    String id;

    public DataClient(String id, float temperature, float humidity) {
        super(temperature, humidity);
        this.id = id;
    }

    @Override
    public float getTemperature() {
        return super.getTemperature();
    }

    @Override
    public void setTemperature(float temperature) {
        super.setTemperature(temperature);
    }

    @Override
    public float getHumidity() {
        return super.getHumidity();
    }

    @Override
    public void setHumidity(float humidity) {
        super.setHumidity(humidity);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
