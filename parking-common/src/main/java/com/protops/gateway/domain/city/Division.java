package com.protops.gateway.domain.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by doubleminter on 2016/7/17.
 */
public class Division {

    private City city = new City();
    private List<City> cityList = new ArrayList<City>();

    public Division(Map<String, Object> ret) {

        city.setName((String) ret.get("n"));
        String g = (String) ret.get("g");
        city.setZoom(g.split("\\|")[1]);
        city.setLongitude(g.split("\\|")[0].split(",")[0]);
        city.setLatitude(g.split("\\|")[0].split(",")[1]);

        List<Map<String, String>> list = (List<Map<String, String>>) ret.get("cities");

        for (Map<String, String> c : list) {

            City city = new City(c);

            cityList.add(city);

        }

    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
