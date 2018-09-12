package com.protops.gateway.weixin.factory;


import com.protops.gateway.weixin.factory.processor.EventProcessor;
import com.protops.gateway.weixin.factory.processor.MessageProcessor;
import com.protops.gateway.weixin.factory.processor.TextProcessor;
import com.protops.gateway.weixin.vo.push.EventPush;
import com.protops.gateway.weixin.vo.push.TextPush;

/**
 * @author zzh
 */
public enum PushProcessorFactory {
    TEXT {
        @Override
        public MessageProcessor createProcessor(String message) {
            return new TextProcessor(message, TextPush.class);
        }

    },
    EVENT {
        @Override
        public MessageProcessor createProcessor(String message) {
            return new EventProcessor(message, EventPush.class);
        }
    };
//    IMAGE {
//        @Override
//        protected Class<? extends Push> getPushClass() {
//            return ImagePush.class;
//        }
//
//        @Override
//        protected PushParser createProcessor() {
//            return new ImagePushParser();
//        }
//    },
//    LINK {
//        @Override
//        protected Class<? extends Push> getPushClass() {
//            return LinkPush.class;
//        }
//
//        @Override
//        protected PushParser createProcessor() {
//            return new LinkPushParser();
//        }
//    },
//    LOCATION {
//        @Override
//        protected Class<? extends Push> getPushClass() {
//            return LocationPush.class;
//        }
//
//        @Override
//        protected PushParser createProcessor() {
//            return new LocationPushParser();
//        }
//    },
//    VOICE { // not open
//
//        @Override
//        protected Class<? extends Push> getPushClass() {
//            return VoicePush.class;
//        }
//
//        @Override
//        protected PushParser createProcessor() {
//            return new VoicePushParser();
//        }
//    },
//    VIDEO { // not open
//
//        @Override
//        protected Class<? extends Push> getPushClass() {
//            return VideoPush.class;
//        }
//
//        @Override
//        protected PushParser createProcessor() {
//            return new VideoPushParser();
//        }
//
//    };

    public abstract MessageProcessor createProcessor(String message);

}
