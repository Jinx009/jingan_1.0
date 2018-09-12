package com.protops.gateway.filter; /**
 * Created by damen on 2014/12/23.
 */


import com.protops.gateway.constants.Constants;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class ApplicationListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        //Set application real path
        Constants.APP_REAL_PATH = event.getServletContext().getRealPath("");
        if (Constants.APP_REAL_PATH != null) {
            if (Constants.APP_REAL_PATH.charAt(Constants.APP_REAL_PATH.length() - 1) != java.io.File.separatorChar) {
                Constants.APP_REAL_PATH += java.io.File.separatorChar;
            }
        }


    }
}
