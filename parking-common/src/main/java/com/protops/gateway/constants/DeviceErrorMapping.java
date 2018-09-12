package com.protops.gateway.constants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

public class DeviceErrorMapping {

    private static final String FILE_NAME = "deviceError.properties";

    // A7代表未捕获异常
    private static final String DEFAULT_RESP = "A7";

    private static Map<String, DeviceError> deviceError = new HashMap<String, DeviceError>();

    public static String mapListStr = "";

    static {

        init();

    }

    private DeviceErrorMapping() {
    }

    /**
     * 始终返回值，默认01
     */
    public static DeviceError getMappingCode(String innerErrorCode) {

        DeviceError resp = deviceError.get(innerErrorCode);

//		if (StringUtils.isEmpty(resp)) {
//			resp = DEFAULT_RESP;
//		}

        return resp;
    }

    private static void init() {

        InputStream in = DeviceErrorMapping.class.getResourceAsStream("/" + FILE_NAME);

        Properties props = new Properties();
        try {
            props.load(in);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        Set<Entry<Object, Object>> entries = props.entrySet();
        for (Entry<Object, Object> entry : entries) {
            String codes = (String) entry.getValue();
            String key = (String) entry.getKey();
            String[] codesArr = codes.split(",");
            DeviceError deviceErrorUnit = new DeviceError();
            deviceErrorUnit.setCode(key);
            deviceErrorUnit.setDesc(codesArr[0]);
            deviceErrorUnit.setSolution(codesArr[1]);
            Integer i = Integer.valueOf(codesArr[2]);
            if(i == 1){
                deviceErrorUnit.setVisible(true);
            }
            deviceError.put(key, deviceErrorUnit);
            mapListStr += key+",";
        }
        mapListStr = mapListStr.substring(0,mapListStr.length()-1);
    }
}
