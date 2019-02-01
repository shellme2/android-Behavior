package com.eebbk.behavior.demo.test.function.query;

import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

public interface IConfigRefreshUI {
    void onRefreshUI(List<QueryConfigInfo> configList);

    void onError(String tip);

    void onProgress(int total, int finished, String msg, boolean end);
}
