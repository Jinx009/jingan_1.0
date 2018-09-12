package com.protops.gateway.domain.city;

import com.protops.gateway.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by doubleminter on 2016/7/17.
 */
public class City {

    private Integer id;
    private String name;
    private String longitude;
    private String latitude;
    private String zoom;
    private List<Integer> ids = new ArrayList<Integer>();

    /**
     * {
     * "n": "马鞍山",
     * "g": "118.515882,31.688528|13",
     * "t": "1|2|3"
     * },
     *
     * @param ret
     */
    public City(Map<String, String> ret) {

        name = ret.get("n");
        String data = ret.get("g");
        String[] dataSplit = data.split("\\|");
        longitude = dataSplit[0].split(",")[0];
        latitude = dataSplit[0].split(",")[1];
        zoom = dataSplit[1];
        String t = ret.get("t");
        if (StringUtils.isNotBlank(t)) {
            String[] list = t.split("\\|");
            for(String id : list){
                ids.add(Integer.valueOf(id));
            }
        }

    }

    public City() {


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
