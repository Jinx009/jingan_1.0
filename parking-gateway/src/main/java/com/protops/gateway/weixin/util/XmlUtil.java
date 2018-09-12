package com.protops.gateway.weixin.util;

import com.protops.gateway.weixin.vo.push.Push;
import com.protops.gateway.weixin.vo.reply.Reply;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml <-> object
 *
 * @author zzh
 */
public class XmlUtil {
    /**
     * xml -> object, hanle push
     *
     * @param message
     * @param childClass
     * @return
     */
    public static Push unmarshal(String message, Class<? extends Push> childClass) {
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(Push.class, childClass);
            Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();

            return (Push) unmarshaller.unmarshal(new StringReader(message));
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * object -> xml, handle reply
     *
     * @param reply
     */
    public static String marshal(Reply reply) {
        if (reply == null) {
            return null;
        }

        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(reply.getClass());

            Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            marshaller.marshal(reply, sw);

            return sw.toString();
        } catch (Exception e) {
        }

        return null;
    }
}
