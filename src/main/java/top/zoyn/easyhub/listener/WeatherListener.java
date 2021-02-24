package top.zoyn.easyhub.listener;

import top.zoyn.easyhub.EasyHub;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (EasyHub.getInstance().getNoRainWorlds().contains(event.getWorld().getName())) {
            // toWeatherState 判断的是要Change的天气是否是雨雪
            if (event.toWeatherState()) {
                event.setCancelled(true);
            }
        }
    }
}
