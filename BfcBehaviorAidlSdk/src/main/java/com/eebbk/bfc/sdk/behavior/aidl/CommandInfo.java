package com.eebbk.bfc.sdk.behavior.aidl;

import java.util.Map;

/**
 * @author hesn
 * @function 缓存埋点命令数据
 * @date 17-5-9
 * @company 步步高教育电子有限公司
 */

class CommandInfo {

    private int type;

    private String page;

    private Map<String, String> map;

    CommandInfo(int type) {
        this(type, null);
    }

    CommandInfo(int type, String page) {
        this.type = type;
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public CommandInfo setType(int type) {
        this.type = type;
        return this;
    }

    public String getPage() {
        return page;
    }

    public CommandInfo setPage(String page) {
        this.page = page;
        return this;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public CommandInfo setMap(Map<String, String> map) {
        this.map = map;
        return this;
    }
}
