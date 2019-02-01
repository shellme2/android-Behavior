package com.eebbk.bfc.sdk.behavior.db.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.utils.DesUtils;
import com.eebbk.bfc.sdk.behavior.utils.FormatUtils;

public class Record{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    transient private int id;
    /**
     * 序列号
     */
    private String mId = "";
    /**
     * 系统版本
     */
    private String osVer = "";
    /**
     * 品牌
     */
    private String brand = "";
    /**
     * 设备名
     */
    private String devName = "";
    /**
     * 用户名
     */
    private String userId = "";
    /**
     * 姓名
     */
    private String userName = "";
    /**
     * 性别
     */
    private String sex = "";
    /**
     * 生日
     */
    private String birthday = "";
    /**
     * 年级
     */
    private String grade = "";
    /**
     * 电话号码
     */
    private String phoneNum = "";
    /**
     * 年龄
     */
    private String age = "";
    /**
     * 学校
     */
    private String school = "";
    /**
     * 年级类型
     */
    private String gradeType = "";
    /**
     * 学科
     */
    private String subjects = "";
    /**
     * 作为属性封装在extend字段里，字段名以“user_”开头
     */
    private String userextend = "";
    /**
     * 应用ID
     */
    private String appId = "";
    /**
     * 应用版本
     */
    private String appVer = "";
    /**
     * 应用名
     */
    private String moduleName = "";
    /**
     * 应用包名
     */
    private String packageName = "";
    /**
     * 事件名
     */
    private String eventName = "";
    /**
     * 功能名
     */
    private String functionName = "";
    /**
     * 事件类型
     */
    private int eventType = -1;
    /**
     * 触发时间
     */
    private String trigTime = "";
    /**
     * 触发值
     */
    private String trigValue = "";
    /**
     * 当前Activity
     */
    private String page = "";
    /**
     * 模块细节
     */
    private String moduleDetail = "";
    /**
     * 数据ID
     */
    private String dataId = "";
    /**
     * 数据类型
     */
    private String dataType = "";
    /**
     * 数据标题
     */
    private String dataTitle = "";
    /**
     * 数据版本
     */
    private String dataEdition = "";
    /**
     * 数据出版者
     */
    private String dataPublisher = "";
    /**
     * 数据科目
     */
    private String dataSubject = "";
    /**
     * 数据年级
     */
    private String dataGrade = "";
    /**
     * 数据扩展
     */
    private String dataExtend = "";
    /**
     * 扩展
     */
    private String sessionId = "";//用来记录App当次打开的会话唯一标识
    private String extend = "";
    /**
     * 采集Jar包的版本
     */
    private String daVer = "";
    /**
     * 所连接路由的MAC地址
     */
    private String routerMac = "";
    /**
     * 渠道ID
     */
    private String channelId = "";
    /**
     * 额外扩展上传判断条件
     */
    private String extendJudgment = "";
    /**
     * imei
     */
    private String imei = "";
    /**
     * cpuId
     */
    private String innerModel = "";
    /**
     * 机器mac地址
     */
    private String romver = "";

    public Record() {

    }

    public Record(Cursor cursor) {
        if (cursor == null) {
            return;
        }
        id = cursor.getInt(cursor.getColumnIndexOrThrow(BFCColumns._ID));

        mId = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_MA_MID));
        devName = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_MA_DEVICE));
        osVer = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_MA_OSVERSION));
        brand = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_MA_BRAND));

        userId = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_USERID));
        userName = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_USERNAME));
        birthday = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_BIRTHDAY));
        grade = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_GRADE));
        phoneNum = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_PHONENUM));
        sex = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_SEX));
        userextend = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_USEREXTEND));
        age = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_AGE));
        school = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_SCHOOL));
        gradeType = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_GRADETYPE));
        subjects = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_UA_SUBJECTS));

        appId = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_AA_APPID));
        appVer = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_AA_APPVER));
        moduleName = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_AA_MODULENAME));
        packageName = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_AA_PACKAGENAME));

        page = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_PAGE));
        eventName = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_EVENTNAME));
        eventType = cursor.getInt(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_EVENTTYPE));
        functionName = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_FUNCTIONNAME));
        moduleDetail = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_MODULEDETAIL));
        trigTime = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_TTIME));
        trigValue = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_TVALUE));
        formatExceptionLog();
        if (EType.FUNCTION_MONITOR_URL.equals(functionName)) {//解密URL
            try {
                moduleDetail = new DesUtils().decrypt(moduleDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        dataEdition = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATAEDITION));
        dataExtend = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATAEXTEND));
        dataGrade = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATAGRADE));
        dataId = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATAID));
        dataPublisher = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATAPUBLISHER));
        dataSubject = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATASUBJECT));
        dataTitle = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATATITLE));
        dataType = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_DA_DATATYPE));

        sessionId = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_EA_SESSIONID));
        extend = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_EXTEND));
        daVer = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_DAVER));
        routerMac = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_ROUTERMAC));
        channelId = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_CHANNELID));
        extendJudgment = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_EXTENDJUDGMENT));
        imei = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_IMEI));
        innerModel = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_INNER_MODEL));
        romver = cursor.getString(cursor.getColumnIndexOrThrow(BFCColumns.COLUMN_OA_ROMVER));
    }

    public Record(ContentValues value) {
        if (value == null) {
            return;
        }
        mId = value.getAsString(BFCColumns.COLUMN_MA_MID);
        devName = value.getAsString(BFCColumns.COLUMN_MA_DEVICE);
        osVer = value.getAsString(BFCColumns.COLUMN_MA_OSVERSION);

        brand = value.getAsString(BFCColumns.COLUMN_MA_BRAND);

        userId = value.getAsString(BFCColumns.COLUMN_UA_USERID);
        userName = value.getAsString(BFCColumns.COLUMN_UA_USERNAME);
        birthday = value.getAsString(BFCColumns.COLUMN_UA_BIRTHDAY);
        grade = value.getAsString(BFCColumns.COLUMN_UA_GRADE);
        phoneNum = value.getAsString(BFCColumns.COLUMN_UA_PHONENUM);
        sex = value.getAsString(BFCColumns.COLUMN_UA_SEX);
        userextend = value.getAsString(BFCColumns.COLUMN_UA_USEREXTEND);
        age = value.getAsString(BFCColumns.COLUMN_UA_AGE);
        school = value.getAsString(BFCColumns.COLUMN_UA_SCHOOL);
        gradeType = value.getAsString(BFCColumns.COLUMN_UA_GRADETYPE);
        subjects = value.getAsString(BFCColumns.COLUMN_UA_SUBJECTS);

        appId = value.getAsString(BFCColumns.COLUMN_AA_APPID);
        appVer = value.getAsString(BFCColumns.COLUMN_AA_APPVER);
        moduleName = value.getAsString(BFCColumns.COLUMN_AA_MODULENAME);
        packageName = value.getAsString(BFCColumns.COLUMN_AA_PACKAGENAME);

        page = value.getAsString(BFCColumns.COLUMN_EA_PAGE);
        eventName = value.getAsString(BFCColumns.COLUMN_EA_EVENTNAME);
        eventType = value.getAsInteger(BFCColumns.COLUMN_EA_EVENTTYPE);
        functionName = value.getAsString(BFCColumns.COLUMN_EA_FUNCTIONNAME);
        moduleDetail = value.getAsString(BFCColumns.COLUMN_EA_MODULEDETAIL);
        if (EType.FUNCTION_MONITOR_URL.equals(functionName)) {//加密URL
            try {
                moduleDetail = new DesUtils().decrypt(moduleDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        trigTime = value.getAsString(BFCColumns.COLUMN_EA_TTIME);
        trigValue = value.getAsString(BFCColumns.COLUMN_EA_TVALUE);
        formatExceptionLog();

        dataEdition = value.getAsString(BFCColumns.COLUMN_DA_DATAEDITION);
        dataExtend = value.getAsString(BFCColumns.COLUMN_DA_DATAEXTEND);
        dataGrade = value.getAsString(BFCColumns.COLUMN_DA_DATAGRADE);
        dataId = value.getAsString(BFCColumns.COLUMN_DA_DATAID);
        dataPublisher = value.getAsString(BFCColumns.COLUMN_DA_DATAPUBLISHER);
        dataSubject = value.getAsString(BFCColumns.COLUMN_DA_DATASUBJECT);
        dataTitle = value.getAsString(BFCColumns.COLUMN_DA_DATATITLE);
        dataType = value.getAsString(BFCColumns.COLUMN_DA_DATATYPE);

        sessionId = value.getAsString(BFCColumns.COLUMN_EA_SESSIONID);
        extend = value.getAsString(BFCColumns.COLUMN_OA_EXTEND);
        daVer = value.getAsString(BFCColumns.COLUMN_OA_DAVER);
        routerMac = value.getAsString(BFCColumns.COLUMN_OA_ROUTERMAC);
        channelId = value.getAsString(BFCColumns.COLUMN_OA_CHANNELID);
        extendJudgment = value.getAsString(BFCColumns.COLUMN_OA_EXTENDJUDGMENT);
        imei = value.getAsString(BFCColumns.COLUMN_OA_IMEI);
        innerModel = value.getAsString(BFCColumns.COLUMN_OA_INNER_MODEL);
        romver = value.getAsString(BFCColumns.COLUMN_OA_ROMVER);
    }

    public ContentValues getContentValues() {
        ContentValues value = new ContentValues();

        value.put(BFCColumns.COLUMN_MA_MID,mId);
        value.put(BFCColumns.COLUMN_MA_DEVICE,devName);
        value.put(BFCColumns.COLUMN_MA_OSVERSION,osVer);

        value.put(BFCColumns.COLUMN_MA_BRAND,brand);

        value.put(BFCColumns.COLUMN_UA_USERID,userId);
        value.put(BFCColumns.COLUMN_UA_USERNAME,userName);
        value.put(BFCColumns.COLUMN_UA_BIRTHDAY,birthday);
        value.put(BFCColumns.COLUMN_UA_GRADE,grade);
        value.put(BFCColumns.COLUMN_UA_PHONENUM,phoneNum);
        value.put(BFCColumns.COLUMN_UA_SEX,sex);
        value.put(BFCColumns.COLUMN_UA_USEREXTEND,userextend);
        value.put(BFCColumns.COLUMN_UA_AGE, age);
        value.put(BFCColumns.COLUMN_UA_SCHOOL, school);
        value.put(BFCColumns.COLUMN_UA_SUBJECTS, subjects);
        value.put(BFCColumns.COLUMN_UA_GRADETYPE, gradeType);

        value.put(BFCColumns.COLUMN_AA_APPID,appId);
        value.put(BFCColumns.COLUMN_AA_APPVER,appVer);
        value.put(BFCColumns.COLUMN_AA_MODULENAME,moduleName);
        value.put(BFCColumns.COLUMN_AA_PACKAGENAME,packageName);

        value.put(BFCColumns.COLUMN_EA_PAGE,page);
        value.put(BFCColumns.COLUMN_EA_EVENTNAME,eventName);
        value.put(BFCColumns.COLUMN_EA_EVENTTYPE,eventType);
        value.put(BFCColumns.COLUMN_EA_FUNCTIONNAME,functionName);
        value.put(BFCColumns.COLUMN_EA_TTIME,trigTime);
        value.put(BFCColumns.COLUMN_EA_TVALUE,trigValue);
        if (EType.FUNCTION_MONITOR_URL.equals(functionName)) {//解密URL
            try {
                moduleDetail = new DesUtils().encrypt(moduleDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        value.put(BFCColumns.COLUMN_EA_MODULEDETAIL,moduleDetail);

        value.put(BFCColumns.COLUMN_DA_DATAEDITION,dataEdition);
        value.put(BFCColumns.COLUMN_DA_DATAEXTEND,dataExtend);
        value.put(BFCColumns.COLUMN_DA_DATAGRADE,dataGrade);
        value.put(BFCColumns.COLUMN_DA_DATAID,dataId);
        value.put(BFCColumns.COLUMN_DA_DATAPUBLISHER,dataPublisher);
        value.put(BFCColumns.COLUMN_DA_DATASUBJECT,dataSubject);
        value.put(BFCColumns.COLUMN_DA_DATATITLE,dataTitle);
        value.put(BFCColumns.COLUMN_DA_DATATYPE,dataType);

        value.put(BFCColumns.COLUMN_EA_SESSIONID,sessionId);
        value.put(BFCColumns.COLUMN_OA_EXTEND,extend);
        value.put(BFCColumns.COLUMN_OA_DAVER,daVer);
        value.put(BFCColumns.COLUMN_OA_ROUTERMAC,routerMac);
        value.put(BFCColumns.COLUMN_OA_CHANNELID,channelId);
        value.put(BFCColumns.COLUMN_OA_EXTENDJUDGMENT,extendJudgment);
        value.put(BFCColumns.COLUMN_OA_IMEI,imei);
        value.put(BFCColumns.COLUMN_OA_INNER_MODEL,innerModel);
        value.put(BFCColumns.COLUMN_OA_ROMVER, romver);
        return value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getUserextend() {
        return userextend;
    }

    public void setUserextend(String userextend) {
        this.userextend = userextend;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getTrigTime() {
        return trigTime;
    }

    public void setTrigTime(String trigTime) {
        this.trigTime = trigTime;
    }

    public String getTrigValue() {
        return trigValue;
    }

    public void setTrigValue(String trigValue) {
        this.trigValue = trigValue;
    }

    public String getModuleDetail() {
        return moduleDetail;
    }

    public void setModuleDetail(String moduleDetail) {
        this.moduleDetail = moduleDetail;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataEdition() {
        return dataEdition;
    }

    public void setDataEdition(String dataEdition) {
        this.dataEdition = dataEdition;
    }

    public String getDataPublisher() {
        return dataPublisher;
    }

    public void setDataPublisher(String dataPublisher) {
        this.dataPublisher = dataPublisher;
    }

    public String getDataSubject() {
        return dataSubject;
    }

    public void setDataSubject(String dataSubject) {
        this.dataSubject = dataSubject;
    }

    public String getDataGrade() {
        return dataGrade;
    }

    public void setDataGrade(String dataGrade) {
        this.dataGrade = dataGrade;
    }

    public String getDataExtend() {
        return dataExtend;
    }

    public void setDataExtend(String dataExtend) {
        this.dataExtend = dataExtend;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getDaVer() {
        return daVer;
    }

    public void setDaVer(String daVer) {
        this.daVer = daVer;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getExtendJudgment() {
        return extendJudgment;
    }

    public void setExtendJudgment(String extendJudgment) {
        this.extendJudgment = extendJudgment;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getInnerModel() {
        return innerModel;
    }

    public void setInnerModel(String innerModel) {
        this.innerModel = innerModel;
    }

    public String getRomver() {
        return romver;
    }

    public void setRomver(String romver) {
        this.romver = romver;
    }

    //格式化异常日志
    private void formatExceptionLog(){
        if(eventType != EType.TYPE_EXCEPTION){
            return;
        }
        trigValue = FormatUtils.formatExceptionLog(trigValue);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", mId='" + mId + '\'' +
                ", osVer='" + osVer + '\'' +
                ", brand='" + brand + '\'' +
                ", devName='" + devName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", grade='" + grade + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", age='" + age + '\'' +
                ", school='" + school + '\'' +
                ", gradeType='" + gradeType + '\'' +
                ", subjects='" + subjects + '\'' +
                ", userextend='" + userextend + '\'' +
                ", appId='" + appId + '\'' +
                ", appVer='" + appVer + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", functionName='" + functionName + '\'' +
                ", eventType=" + eventType +
                ", trigTime='" + trigTime + '\'' +
                ", trigValue='" + trigValue + '\'' +
                ", page='" + page + '\'' +
                ", moduleDetail='" + moduleDetail + '\'' +
                ", dataId='" + dataId + '\'' +
                ", dataType='" + dataType + '\'' +
                ", dataTitle='" + dataTitle + '\'' +
                ", dataEdition='" + dataEdition + '\'' +
                ", dataPublisher='" + dataPublisher + '\'' +
                ", dataSubject='" + dataSubject + '\'' +
                ", dataGrade='" + dataGrade + '\'' +
                ", dataExtend='" + dataExtend + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", extend='" + extend + '\'' +
                ", daVer='" + daVer + '\'' +
                ", routerMac='" + routerMac + '\'' +
                ", channelId='" + channelId + '\'' +
                ", extendJudgment='" + extendJudgment + '\'' +
                ", imei='" + imei + '\'' +
                ", innerModel='" + innerModel + '\'' +
                ", romver='" + romver + '\'' +
                '}';
    }
}
