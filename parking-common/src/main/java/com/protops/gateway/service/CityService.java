package com.protops.gateway.service;

import com.protops.gateway.constants.Constants;
import com.protops.gateway.constants.enums.CityEnum;
import com.protops.gateway.domain.city.City;
import com.protops.gateway.domain.city.Division;
import com.protops.gateway.util.FileUtil;
import com.protops.gateway.util.JsonUtil;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by doubleminter on 2016/7/17.
 */
public class CityService implements InitializingBean {


    private String filePath;

    private List<City> cities = new ArrayList<City>();

    private List<Division> provinces = new ArrayList<Division>();

    private static final String fileName = "city.json";

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public City getCity(String division, String city) {
        return getCity(division, "", city);
    }


    public City getCity(String division, String province, String city) {

        if (CityEnum.city.name().equals(division)) {

            for (City c : cities) {

                if (c.getName().equals(city)) {
                    return c;
                }

            }

            return null;

        } else {

            for (Division d : provinces) {

                if (d.getCity().getName().equals(province)) {

                    List<City> cities = d.getCityList();

                    for (City c : cities) {
                        if (c.getName().equals(city)) {
                            return c;
                        }
                    }

                }

            }
        }

        return null;
    }


    public void afterPropertiesSet() throws Exception {

        String content = FileUtil.loadFileAsString(new File(getFilePath() + fileName), Constants.Default_SysEncoding);

        Map<String, Object> json = JsonUtil.fromJson(content, Map.class);

        List<City> cities = new ArrayList<City>();

        initCity("municipalities", cities, json);

        initCity("other", cities, json);

        this.cities = cities;

        initDivision("provinces", json);

    }

    private void initDivision(String key, Map<String, Object> json) {

        List<Map<String, Object>> list = (List<Map<String, Object>>) json.get(key);

        List<Division> divisions = new ArrayList<Division>();

        for (Map<String, Object> ret : list) {

            Division division = new Division(ret);

            divisions.add(division);

        }

        provinces = divisions;

    }

    private void initCity(String key, List<City> cities, Map<String, Object> json) {

        List<Map<String, String>> list = (List<Map<String, String>>) json.get(key);

        for (Map<String, String> ret : list) {

            City city = new City(ret);

            cities.add(city);
        }

    }
}
