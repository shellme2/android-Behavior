package com.eebbk.bfc.sdk.behavior;

/**
 * @author hesn
 * 2018/7/11
 */
public interface Constant {

    interface Remote {

        interface Key{
            String VERSION_CODE = "versionCode";
            String INIT = "init";
        }

        interface Command{
            int CONFIG = 1;
        }
    }

    interface Exception{
        String FLAG_ANR_LOG = "[[TRUNCATED]]";
    }

    interface Report{
        String CACHE_DIR_NAME = ".UserBahavior";
    }
}
