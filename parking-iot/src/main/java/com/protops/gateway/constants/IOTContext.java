package com.protops.gateway.constants;

import com.protops.gateway.constants.enums.Version;
import com.protops.gateway.domain.iot.Router;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by damen on 2016/3/23.
 */
public class IOTContext {

    private static final String DEFAULT_SECRET = "zhanway2016";

    private Router router;

    private Version version;

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public String getSecret() {

        if (getRouter() == null || StringUtils.isBlank(getRouter().getSecret())) {
            return DEFAULT_SECRET;
        }

        return getRouter().getSecret();


    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
