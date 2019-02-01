package com.eebbk.behavior.demo.test.function.parameter.contants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author hesn
 * @function 输入参数默认值
 * @date 16-10-10
 * @company 步步高教育电子有限公司
 */

public class InputDefaultModle {

    /**
     * 计次事件
     */
    public static final class ClickEventModle{
        /**
         * 进入的activity
         */
        public static final String activity = "";
        /**
         * 功能名称
         */
        public static final String functionName = "点击难题反馈";
        /**
         * 模块详细
         */
        public static final String moduleDetail = "难题反馈";
        /**
         * 扩展字段
         */
        public static final String extend = getJsonDemo();
    }

    /**
     * 计数事件
     */
    public static final class CountEventModle{
        /**
         * 进入的activity
         */
        public static final String activity = "";
        /**
         * 功能名称
         */
        public static final String functionName = "点击播放按钮";
        /**
         * 模块详细
         */
        public static final String moduleDetail = "孙悟空打妖精";
        /**
         * 触发本次动作的触发值，如视频播放的时长等，注：时长统一以毫秒(ms)为单位
         */
        public static final String trigValue = "10分钟";
        /**
         * 扩展字段
         */
        public static final String extend = "";
    }

    /**
     * 自定义事件
     */
    public static final class CustomEventModle{
        /**
         * 进入的activity
         */
        public static final String activity = "";
        /**
         * 功能名称
         */
        public static final String functionName = "自定义功能";
        /**
         * 模块详细
         */
        public static final String moduleDetail = "自定义功能详细说明";
        /**
         * 触发值
         */
        public static final String trigValue = "自定义10分钟";
        /**
         * 扩展字段
         */
        public static final String extend = "";
    }

    /**
     * 搜索事件
     */
    public static final class SearchEventModle{
        /**
         * 进入的activity
         */
        public static final String activity = "";
        /**
         * 功能名称
         */
        public static final String functionName = "点击智能搜学";
        /**
         * 模块详细
         */
        public static final String moduleDetail = "*";
        /**
         * 搜索关键字
         */
        public static final String keyWrod = "100";
        /**
         * 搜索结果
         */
        public static final String resultCount = "";
    }

    /**
     * 页面切换事件
     */
    public static final class PageEventModle{
        /**
         * 进入的activity
         */
        public static final String activity = "";
        /**
         * 功能名称
         */
        public static final String functionName = "难题反馈使用统计";
        /**
         * 模块详细
         */
        public static final String moduleDetail = "难题反馈";
        /**
         * 扩展字段
         */
        public static final String extend = getJsonDemo();
    }

    /**
     * 课本信息
     */
    public static final class DataAttrModle{
        /**
         * 数据ID
         */
        public static final String dataId = "";
        /**
         * 数据标题
         */
        public static final String dataTitle = "同步语文四年级";
        /**
         * 数据版本
         */
        public static final String dataEdition = "";
        /**
         * 数据类型
         */
        public static final String dataType = "";
        /**
         * 数据年级
         */
        public static final String dataGrade = "";
        /**
         * 数据科目
         */
        public static final String dataSubject = "";
        /**
         * 数据出版者
         */
        public static final String dataPublisher = "";
        /**
         * 数据扩展
         */
        public static final String dataExtend = "";
    }

    /**
     * 用户信息
     */
    public static final class UserAttrModle{
        /**
         * 用户唯一id
         */
        public static final String userId = "666";
        /**
         * 用户名
         */
        public static final String userName = "神童";
        /**
         * 性别
         */
        public static final String sex = "男";
        /**
         * 用户生日
         */
        public static final String birthday = "20161010";
        /**
         * 年级
         */
        public static final String grade = "1";
        /**
         * 用户手机号码
         */
        public static final String phoneNum = "1868888888";
        /**
         * 数据扩展
         */
        public static final String userExtend = "";
        /**
         * 年龄
         */
        public static final String age = "7";
        /**
         * 学校
         */
        public static final String school = "长安小学";
        /**
         * 年级类型
         */
        public static final String gradeType = "1";
        /**
         * 学科
         */
        public static final String subjects = "语文";
    }

    /**
     * 上报模式
     */
    public static final class ReportMode{
        /**
         * 定量上报模式
         */
        public static final class QuantifyMode{
            /**
             * 采集条数达到此阀值触发上报
             */
            public static final String quantify = "100";
        }

        /**
         * 定时上报
         */
        public static final class FixedTimeMode{
            /**
             * 时
             * <br> 24进制
             */
            public static final String hour = "10";
            /**
             * 分
             */
            public static final String minute = "25";
            /**
             * 秒
             */
            public static final String second = "49";
        }

        /**
         * 周期性上报
         */
        public static final class PeriodicityMode{
            /**
             * 在此设置时间内只会触发一次上报数据
             * <br> 单位:秒
             * <br> 区间[300, 43200]
             */
            public static final String periodicity = "300";
        }
    }

    /**
     * 缓存策略
     */
    public static final class CachePolicy{
        /**
         * 时间缓存模式
         */
        public static final class TimePolicy{
            /**
             * 在此设置时间内只会触发一次数据入库
             * <br> 单位:秒
             */
            public static final String time = "5000";
        }

        /**
         * 容量缓存模式
         */
        public static final class CapacityPolicy{
            /**
             * 采集条数达到此阀值触发数据入库
             * <br> 区间[0, 10]
             */
            public static final String capacity = "10";
        }
    }

    private static String getJsonDemo() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Andy");
            jsonObject.put("age", 20);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private InputDefaultModle(){

    }
}
