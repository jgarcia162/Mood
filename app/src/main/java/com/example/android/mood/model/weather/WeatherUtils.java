package com.example.android.mood.model.weather;

import com.example.android.mood.R;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {
    private static Map<String, Integer> drawablesMap = null;

    private static void initializeMap() {
        drawablesMap = new HashMap<>();
        drawablesMap.put("am_pcloudyr", R.drawable.rain);
        drawablesMap.put("am_showers", R.drawable.showers);
        drawablesMap.put("am_showshowers", R.drawable.rainandsnow);
        drawablesMap.put("blizzardn", R.drawable.blizzard);
        drawablesMap.put("blowingsnow", R.drawable.snow);
        drawablesMap.put("blowingsnown", R.drawable.snow);
        drawablesMap.put("chancetstorm", R.drawable.tstorm);
        drawablesMap.put("chancetstormn", R.drawable.pm_tstorm);
        drawablesMap.put("clearw", R.drawable.clear);
        drawablesMap.put("clearwn", R.drawable.clearn);
        drawablesMap.put("cloudyw", R.drawable.cloudy);
        drawablesMap.put("cloudywn", R.drawable.cloudyn);
        drawablesMap.put("dust", R.drawable.fog);
        drawablesMap.put("fair", R.drawable.pcloudy);
        drawablesMap.put("fairn", R.drawable.pcloudyn);
        drawablesMap.put("fairw", R.drawable.pcloudy);
        drawablesMap.put("fairwn", R.drawable.pcloudyn);
        drawablesMap.put("fdrizzle", R.drawable.freezingrain);
        drawablesMap.put("fdrizzlen", R.drawable.freezingrain);
        drawablesMap.put("flurries", R.drawable.snow);
        drawablesMap.put("flurriesn", R.drawable.snow);
        drawablesMap.put("fogn", R.drawable.fog);
        drawablesMap.put("freezingrainn", R.drawable.freezingrain);
        drawablesMap.put("hazyn", R.drawable.hazy);
        drawablesMap.put("mcloudy", R.drawable.cloudy);
        drawablesMap.put("mcloudyn", R.drawable.cloudy);
        drawablesMap.put("mcloudyr", R.drawable.rain);
        drawablesMap.put("mcloudyrn", R.drawable.rainn);
        drawablesMap.put("mcloudyrw", R.drawable.rain);
        drawablesMap.put("mcloudyrwn", R.drawable.rain);
        drawablesMap.put("mcloudys", R.drawable.pcloudysf);
        drawablesMap.put("mcloudysfn", R.drawable.pcloudysfn);
        drawablesMap.put("mcloudysfw", R.drawable.pcloudysfn);
        drawablesMap.put("mcloudysfwn", R.drawable.pcloudysfn);
        drawablesMap.put("mcloudysn", R.drawable.pcloudysf);
        drawablesMap.put("mcloudysw", R.drawable.pcloudysf);
        drawablesMap.put("mcloudyswn", R.drawable.pcloudysf);
        drawablesMap.put("mcloudyt", R.drawable.tstorm);
        drawablesMap.put("mcloudytn", R.drawable.pm_tstorm);
        drawablesMap.put("mcloudytw", R.drawable.pm_tstorm);
        drawablesMap.put("mcloudytwn", R.drawable.pm_tstorm);
        drawablesMap.put("mcloudyw", R.drawable.pcloudy);
        drawablesMap.put("mcloudywn", R.drawable.pcloudyn);
        drawablesMap.put("pcloudyr", R.drawable.pcloudy);
        drawablesMap.put("pcloudyrn", R.drawable.pcloudyn);
        drawablesMap.put("pcloudyrw", R.drawable.pcloudy);
        drawablesMap.put("pcloudyrwn", R.drawable.pcloudyn);
        drawablesMap.put("pcloudys", R.drawable.pcloudysf);
        drawablesMap.put("pcloudysfw", R.drawable.pcloudysf);
        drawablesMap.put("pcloudysfwn", R.drawable.pcloudysfn);
        drawablesMap.put("pcloudysn", R.drawable.pcloudysfn);
        drawablesMap.put("pcloudysw", R.drawable.pcloudysf);
        drawablesMap.put("pcloudyswn", R.drawable.pcloudysfn);
        drawablesMap.put("pcloudyt", R.drawable.tstorm);
        drawablesMap.put("pcloudytn", R.drawable.pm_tstorm);
        drawablesMap.put("pcloudytw", R.drawable.tstorm);
        drawablesMap.put("pcloudytwn", R.drawable.pm_tstorm);
        drawablesMap.put("pcloudyw", R.drawable.wind);
        drawablesMap.put("pcloudywn", R.drawable.clearn);
        drawablesMap.put("pm_pcloudy", R.drawable.pcloudyn);
        drawablesMap.put("pm_pcloudyr", R.drawable.rain);
        drawablesMap.put("pm_showers", R.drawable.showersn);
        drawablesMap.put("pm_snowshowers", R.drawable.snow);
        drawablesMap.put("rainandsnown", R.drawable.rainandsnow);
        drawablesMap.put("raintosnow", R.drawable.rainandsnow);
        drawablesMap.put("raintosnown", R.drawable.rainandsnow);
        drawablesMap.put("rainw", R.drawable.rain);
        drawablesMap.put("showersw", R.drawable.showers);
        drawablesMap.put("sleet", R.drawable.freezingrain);
        drawablesMap.put("sleetn", R.drawable.freezingrain);
        drawablesMap.put("sleetsnow", R.drawable.rainandsnow);
        drawablesMap.put("smoke", R.drawable.fog);
        drawablesMap.put("smoken", R.drawable.fog);
        drawablesMap.put("snown", R.drawable.snow);
        drawablesMap.put("snownshowers", R.drawable.snow);
        drawablesMap.put("snownshowersw", R.drawable.snow);
        drawablesMap.put("snownshowerswn", R.drawable.snow);
        drawablesMap.put("snowtorain", R.drawable.rainandsnow);
        drawablesMap.put("snowtorainn", R.drawable.rainandsnow);
        drawablesMap.put("snoww", R.drawable.snow);
        drawablesMap.put("snowwn", R.drawable.snow);
        drawablesMap.put("sunny", R.drawable.clear);
        drawablesMap.put("sunnyn", R.drawable.clearn);
        drawablesMap.put("tstormn", R.drawable.pm_tstorm);
        drawablesMap.put("tstormsw", R.drawable.tstorm);
        drawablesMap.put("tstormswn", R.drawable.pm_tstorm);
        drawablesMap.put("tstormw", R.drawable.tstorm);
        drawablesMap.put("tstormwn", R.drawable.pm_tstorm);
        drawablesMap.put("wintrymix", R.drawable.rainandsnow);
        drawablesMap.put("wintrymixn", R.drawable.rainandsnow);
    }

    public static int getAlternateResourceId(String iconName) {
        iconName = iconName.replace(".png","");
        if (drawablesMap == null) {
            initializeMap();
        }

        return drawablesMap.get(iconName);
    }

}
