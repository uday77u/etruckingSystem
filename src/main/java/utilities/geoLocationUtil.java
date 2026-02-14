package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

import java.util.HashMap;
import java.util.Map;

public class geoLocationUtil {

    private static final Map<String, double[]> COUNTRY_COORDINATES = new HashMap<>();

    static {
        COUNTRY_COORDINATES.put("UK", new double[]{51.5074, -0.1278});
        COUNTRY_COORDINATES.put("USA", new double[]{37.0902, -95.7129});
        COUNTRY_COORDINATES.put("INDIA", new double[]{28.6139, 77.2090});
    }

    public static DevTools setGeoLocation(WebDriver driver, String country) {

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        double[] coordinates = COUNTRY_COORDINATES.get(country.toUpperCase());

        Map<String, Object> params = new HashMap<>();
        params.put("latitude", coordinates[0]);
        params.put("longitude", coordinates[1]);
        params.put("accuracy", 100);

        devTools.send(new org.openqa.selenium.devtools.Command<>("Emulation.setGeolocationOverride", params));

        return devTools;
    }
}
