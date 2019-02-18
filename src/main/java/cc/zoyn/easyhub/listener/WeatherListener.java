package cc.zoyn.easyhub.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        // toWeatherState 判断的是要Change的天气是否是雨雪
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }
}
