package com.eebbk.behavior.demo.test.function.parameter;

import android.text.TextUtils;

import com.eebbk.behavior.demo.test.function.parameter.interfaces.IEventModle;
import com.eebbk.behavior.demo.test.function.parameter.modle.DataAttrModle;
import com.eebbk.behavior.demo.test.function.parameter.modle.UserAttrModle;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.UserAttr;

/**
 * @author hesn
 * @function
 * @date 16-8-31
 * @company 步步高教育电子有限公司
 */

public class ParameterPresenter {

    //事件类型显示模型
    private IEventModle mEventModle;

    private DataAttrModle mDataAttrModle;
    private UserAttrModle mUserAttrModle;

    public ParameterPresenter(){
        super();
        mDataAttrModle = new DataAttrModle();
        mUserAttrModle = new UserAttrModle();
    }

    public void createModle(int eventType){
        mEventModle = EventModleFactory.createModle(eventType);
    }

    public String title() {
        return "参数测试：";
    }

    public String activityEtTip(String pakName) {
        return TextUtils.isEmpty(mEventModle.activityEtTip()) ? pakName : mEventModle.activityEtTip();
    }

    public String functionNameEtTip() {
        return mEventModle.functionNameEtTip();
    }

    public String moduleDetailEtTip() {
        return mEventModle.moduleDetailEtTip();
    }

    public String trigValueEtTip() {
        return mEventModle.trigValueEtTip();
    }

    public String activityTvTip() {
        return formatTvTip(mEventModle.activityTvTip());
    }

    public String functionNameTvTip() {
        return formatTvTip(mEventModle.functionNameTvTip());
    }

    public String moduleDetailTvTip() {
        return formatTvTip(mEventModle.moduleDetailTvTip());
    }

    public String trigValueTvTip() {
        return formatTvTip(mEventModle.trigValueTvTip());
    }

    public String extendTvTip() {
        return formatTvTip(mEventModle.extendTvTip());
    }

    public String extendEtTip() {
        return mEventModle.extendEtTip();
    }

    public boolean showTrigValue() {
        return mEventModle.showTrigValue();
    }

    //课本信息

    public String dataId(){
        return mDataAttrModle.getDataId();
    }

    public String dataIdTip() {
        return formatTvTip(mDataAttrModle.getDataIdTip());
    }

    public String dataTitle() {
        return mDataAttrModle.getDataTitle();
    }

    public String dataTitleTip() {
        return formatTvTip(mDataAttrModle.getDataTitleTip());
    }

    public String dataEdition() {
        return mDataAttrModle.getDataEdition();
    }

    public String dataEditionTip() {
        return formatTvTip(mDataAttrModle.getDataEditionTip());
    }

    public String dataType() {
        return mDataAttrModle.getDataType();
    }

    public String dataTypeTip() {
        return formatTvTip(mDataAttrModle.getDataTypeTip());
    }

    public String dataGrade() {
        return mDataAttrModle.getDataGrade();
    }

    public String dataGradeTip() {
        return formatTvTip(mDataAttrModle.getDataGradeTip());
    }

    public String dataSubject() {
        return mDataAttrModle.getDataSubject();
    }

    public String dataSubjectTip() {
        return formatTvTip(mDataAttrModle.getDataSubjectTip());
    }

    public String dataPublisher() {
        return mDataAttrModle.getDataPublisher();
    }

    public String dataPublisherTip() {
        return formatTvTip(mDataAttrModle.getDataPublisherTip());
    }

    public String dataExtend() {
        return mDataAttrModle.getDataExtend();
    }

    public String dataExtendTip() {
        return formatTvTip(mDataAttrModle.getDataExtendTip());
    }

    //用户信息

    public String userId() {
        return mUserAttrModle.getUserId();
    }

    public String userIdTip() {
        return formatTvTip(mUserAttrModle.getUserIdTip());
    }

    public String userName() {
        return mUserAttrModle.getUserName();
    }

    public String userNameTip() {
        return formatTvTip(mUserAttrModle.getUserNameTip());
    }

    public String userSex() {
        return mUserAttrModle.getSex();
    }

    public String userSexTip() {
        return formatTvTip(mUserAttrModle.getSexTip());
    }

    public String userBirthday() {
        return mUserAttrModle.getBirthday();
    }

    public String userBirthdayTip() {
        return formatTvTip(mUserAttrModle.getBirthdayTip());
    }

    public String userGrade() {
        return mUserAttrModle.getGrade();
    }

    public String userGradeTip() {
        return formatTvTip(mUserAttrModle.getGradeTip());
    }

    public String userPhoneNum() {
        return mUserAttrModle.getPhoneNum();
    }

    public String userPhoneNumTip() {
        return formatTvTip(mUserAttrModle.getPhoneNumTip());
    }

    public String userExtend() {
        return mUserAttrModle.getUserExtend();
    }

    public String userExtendTip() {
        return formatTvTip(mUserAttrModle.getUserExtendTip());
    }

    public String userAge() {
        return mUserAttrModle.getUserAge();
    }

    public String userAgeTip() {
        return formatTvTip(mUserAttrModle.getUserAgeTip());
    }

    public String userSchool() {
        return mUserAttrModle.getUserSchool();
    }

    public String userSchoolTip() {
        return formatTvTip(mUserAttrModle.getUserSchoolTip());
    }

    public String userGradeType() {
        return mUserAttrModle.getUserGradeType();
    }

    public String userGradeTypeTip() {
        return formatTvTip(mUserAttrModle.getUserGradeTypeTip());
    }

    public String userSubjects() {
        return mUserAttrModle.getUserSubjects();
    }

    public String userSubjectsTip() {
        return formatTvTip(mUserAttrModle.getUserSubjectsTip());
    }

    public void save(boolean isAidlBehavior, DataAttr dataAttr, String... values) {
        if(isModleEmpty()){
            return;
        }
        mEventModle.save(isAidlBehavior, dataAttr, values);
    }

    public void saveUserAttr(String... values) {
        BehaviorCollector.getInstance().initUserInfo(new UserAttr()
                .setBirthday(values[0])
                .setGrade(values[1])
                .setPhoneNum(values[2])
                .setSex(values[3])
                .setUserExtend(values[4])
                .setUserId(values[5])
                .setUserName(values[6])
                .setAge(values[7])
                .setSchool(values[8])
                .setGradeType(values[9])
                .setSubjects(values[10])
        );
    }

    /**
     * 模型是否为空
     * @return
     */
    private boolean isModleEmpty(){
        return mEventModle == null;
    }

    /**
     * 提示格式
     * @param text
     * @return
     */
    private String formatTvTip(String text){
        if(TextUtils.isEmpty(text)){
            return text;
        }
        return text + " : ";
    }
}
