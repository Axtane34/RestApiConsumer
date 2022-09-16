import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Consumer {
    private static final RestTemplate restTemplate = new RestTemplate();
    public static void main(String[] args) {
        postRequest();
        List<Measurement> list = getRequest();
        draw(list, 50);

    }

    private static void draw(List<Measurement> list, int pointsCount) {
        List<Double> box = list.stream().map(Measurement::getValue).toList();
        double[] yData = new double[box.size()];
        double[] xData = new double[yData.length];
        for (int i = 0; i < pointsCount; i++) {
            yData[i] = box.get(i);
            xData[i] = i;
        }
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        new SwingWrapper(chart).displayChart();
        try {
            BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<Measurement> getRequest() {
        ResponseEntity<List<Measurement>> responseEntity =
                restTemplate.exchange(
                        "http://localhost:8080/measurements/",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        return responseEntity.getBody();
    }

    private static void postRequest() {
        List<Measurement> list = createRequest(1000, "sensor", 1);
        for (Measurement measurement : list) {
            HttpEntity<Measurement> request = new HttpEntity<>(measurement);
            System.out.println(restTemplate.postForObject("http://localhost:8080/measurements/add/", request, String.class));
        }
    }

    public static List<Measurement> createRequest(int amount, String sensorName, int numbersAfterComma){
        int index;
        boolean raining;
        List<Double> list = new ArrayList<>();
        for (int i = -30; i < 31; i++) {
            list.add(round(i*(Math.random()), numbersAfterComma));
        }
        List<Measurement> resultList = new ArrayList<>();
        for (int i = 0; i <= amount; i++) {
            index = (int) (Math.random()*60);
            raining = index<45;
            resultList.add(new Measurement(list.get(index), raining, new Sensor(sensorName)));
        }
        return resultList;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
