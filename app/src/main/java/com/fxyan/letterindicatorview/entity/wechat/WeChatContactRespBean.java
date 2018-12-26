package com.fxyan.letterindicatorview.entity.wechat;

import java.util.List;
import java.util.Map;

/**
 * @author fxYan
 */
public final class WeChatContactRespBean {
    private Map<String, List<WeChatContactItem>> data;

    public Map<String, List<WeChatContactItem>> getData() {
        return data;
    }

    public void setData(Map<String, List<WeChatContactItem>> data) {
        this.data = data;
    }
}
