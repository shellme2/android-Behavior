package com.eebbk.behavior.demo.test.function.parameter;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.AEventSpinnerActivity;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;

import java.util.List;

/**
 * @author hesn
 * @function 采集自定义参数界面
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class ParameterActivity extends AEventSpinnerActivity {

    private TextView mTitleTv;
    private TextView mActivityTv;
    private TextView mFunctionNameTv;
    private TextView mModuleDetailTv;
    private TextView mTrigValueTv;
    private TextView mExtendTv;

    private EditText mActivityEt;
    private EditText mFunctionNameEt;
    private EditText mModuleDetailEt;
    private EditText mTrigValueEt;
    private EditText mExtendEt;

    private ParameterPresenter mPresenter;
    private TextView mDataIdTv;
    private EditText mDataIdEt;
    private TextView mDataTitleTv;
    private EditText mDataTitleEt;
    private TextView mDataEditionTv;
    private EditText mDataEditionEt;
    private TextView mDataTypeTv;
    private EditText mDataTypeEt;
    private TextView mDataGradeTv;
    private EditText mDataGradeEt;
    private TextView mDataSubjectTv;
    private EditText mDataSubjectEt;
    private TextView mDataPublisherTv;
    private EditText mDataPublisherEt;
    private TextView mDataExtendTv;
    private EditText mDataExtendEt;
    private TextView mUserIdTv;
    private EditText mUserIdEt;
    private TextView mUserNameTv;
    private EditText mUserNameEt;
    private TextView mUserSexTv;
    private EditText mUserSexEt;
    private TextView mUserBirthdayTv;
    private EditText mUserBirthdayEt;
    private TextView mUserGradeTv;
    private EditText mUserGradeEt;
    private TextView mUserPhoneNumTv;
    private EditText mUserPhoneNumEt;
    private TextView mUserExtendTv;
    private EditText mUserExtendEt;
    private TextView mUserAgeTv;
    private EditText mUserAgeEt;
    private TextView mUserGradeTypeTv;
    private EditText mUserGradeTypeEt;
    private TextView mUserSchoolTv;
    private EditText mUserSchoolEt;
    private TextView mUserSubjectsTv;
    private EditText mUserSubjectsEt;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test_event_layout;
    }

    @Override
    protected void initView() {
        mTitleTv = findView(R.id.titleTvId);
        mActivityTv = findView(R.id.activityTvId);
        mFunctionNameTv = findView(R.id.functionNameTvId);
        mModuleDetailTv = findView(R.id.moduleDetailTvId);
        mTrigValueTv = findView(R.id.trigValueTvId);
        mExtendTv = findView(R.id.extendTvId);

        mActivityEt = findView(R.id.activityEtId);
        mFunctionNameEt = findView(R.id.functionNameEtId);
        mModuleDetailEt = findView(R.id.moduleDetailEtId);
        mTrigValueEt = findView(R.id.trigValueEtId);
        mExtendEt = findView(R.id.extendEtId);

        mDataIdTv = (TextView) findViewById(R.id.dataIdTv);
        mDataIdEt = (EditText) findViewById(R.id.dataIdEt);
        mDataTitleTv = (TextView) findViewById(R.id.dataTitleTv);
        mDataTitleEt = (EditText) findViewById(R.id.dataTitleEt);
        mDataEditionTv = (TextView) findViewById(R.id.dataEditionTv);
        mDataEditionEt = (EditText) findViewById(R.id.dataEditionEt);
        mDataTypeTv = (TextView) findViewById(R.id.dataTypeTv);
        mDataTypeEt = (EditText) findViewById(R.id.dataTypeEt);
        mDataGradeTv = (TextView) findViewById(R.id.dataGradeTv);
        mDataGradeEt = (EditText) findViewById(R.id.dataGradeEt);
        mDataSubjectTv = (TextView) findViewById(R.id.dataSubjectTv);
        mDataSubjectEt = (EditText) findViewById(R.id.dataSubjectEt);
        mDataPublisherTv = (TextView) findViewById(R.id.dataPublisherTv);
        mDataPublisherEt = (EditText) findViewById(R.id.dataPublisherEt);
        mDataExtendTv = (TextView) findViewById(R.id.dataExtendTv);
        mDataExtendEt = (EditText) findViewById(R.id.dataExtendEt);
        mUserIdTv = (TextView) findViewById(R.id.userIdTv);
        mUserIdEt = (EditText) findViewById(R.id.userIdEt);
        mUserNameTv = (TextView) findViewById(R.id.userNameTv);
        mUserNameEt = (EditText) findViewById(R.id.userNameEt);
        mUserSexTv = (TextView) findViewById(R.id.sexTv);
        mUserSexEt = (EditText) findViewById(R.id.sexEt);
        mUserBirthdayTv = (TextView) findViewById(R.id.birthdayTv);
        mUserBirthdayEt = (EditText) findViewById(R.id.birthdayEt);
        mUserGradeTv = (TextView) findViewById(R.id.gradeTv);
        mUserGradeEt = (EditText) findViewById(R.id.gradeEt);
        mUserPhoneNumTv = (TextView) findViewById(R.id.phoneNumTv);
        mUserPhoneNumEt = (EditText) findViewById(R.id.phoneNumEt);
        mUserExtendTv = (TextView) findViewById(R.id.userExtendTv);
        mUserExtendEt = (EditText) findViewById(R.id.userExtendEt);
        mUserAgeTv = findView(R.id.ageTv);
        mUserAgeEt = findView(R.id.ageEt);
        mUserSchoolTv = findView(R.id.schoolTv);
        mUserSchoolEt = findView(R.id.schoolEt);
        mUserGradeTypeTv = findView(R.id.gradeTypeTv);
        mUserGradeTypeEt = findView(R.id.gradeTypeEt);
        mUserSubjectsTv = findView(R.id.subjectsTv);
        mUserSubjectsEt = findView(R.id.subjectsEt);
    }

    @Override
    protected void initData() {
        mPresenter = new ParameterPresenter();
    }

    @Override
    protected boolean isShowLog() {
        return true;
    }

    @Override
    protected int setSpinnerId() {
        return R.id.spinner;
    }

    @Override
    protected List<String> setSpinnerList() {
        return null;
    }

    @Override
    protected void initSpinnerData() {
        initModle(getEventTypeBySpinnerIndex(0));
    }

    @Override
    protected void onSelectedEventType(int position, int eventType) {
        initModle(eventType);
    }

    @Override
    protected boolean isShowAidlMode() {
        return true;
    }

    /**
     * 点击保存
     *
     * @param view
     */
    public synchronized void onSave(View view) {
        saveUserInfo();
        mPresenter.save(isCheckedAidlMode()
                , getDataAttr()
                , getTextFromEt(mActivityEt)
                , getTextFromEt(mFunctionNameEt)
                , getTextFromEt(mModuleDetailEt)
                , getTextFromEt(mTrigValueEt)
                , getTextFromEt(mExtendEt)
                , getTextFromEt(mDataIdEt)
                , getTextFromEt(mDataTitleEt)
                , getTextFromEt(mDataEditionEt)
                , getTextFromEt(mDataTypeEt)
                , getTextFromEt(mDataSubjectEt)
                , getTextFromEt(mDataGradeEt)
                , getTextFromEt(mDataPublisherEt)
                , getTextFromEt(mDataExtendEt)
        );
    }

    /**
     * 马上上传所有数据
     *
     * @param view
     */
    public void onUpload(View view) {
        if(isCheckedAidlMode()){
            DADemoUtils.realTime2UploadAidl();
        }else {
            BehaviorCollector.getInstance().realTime2Upload();
        }
    }

    /**
     * 获取课本信息
     *
     * @return
     */
    private DataAttr getDataAttr() {
        DataAttr dataAttr = new DataAttr();
        dataAttr.setDataId(getTextFromEt(mDataIdEt));
        dataAttr.setDataTitle(getTextFromEt(mDataTitleEt));
        dataAttr.setDataEdition(getTextFromEt(mDataEditionEt));
        dataAttr.setDataType(getTextFromEt(mDataTypeEt));
        dataAttr.setDataSubject(getTextFromEt(mDataSubjectEt));
        dataAttr.setDataGrade(getTextFromEt(mDataGradeEt));
        dataAttr.setDataPublisher(getTextFromEt(mDataPublisherEt));
        dataAttr.setDataExtend(getTextFromEt(mDataExtendEt));
        return dataAttr;
    }

    /**
     * 保存全局用户信息
     */
    private void saveUserInfo() {
        mPresenter.saveUserAttr(
                getTextFromEt(mUserBirthdayEt),
                getTextFromEt(mUserGradeEt),
                getTextFromEt(mUserPhoneNumEt),
                getTextFromEt(mUserSexEt),
                getTextFromEt(mUserExtendEt),
                getTextFromEt(mUserIdEt),
                getTextFromEt(mUserNameEt),
                getTextFromEt(mUserAgeEt),
                getTextFromEt(mUserSchoolEt),
                getTextFromEt(mUserGradeTypeEt),
                getTextFromEt(mUserSubjectsEt)
        );
    }

    /**
     * 初始化模型
     */
    private void initModle(int eventType) {
        mPresenter.createModle(eventType);
        initTvTip();
        initEtTip();
    }

    /**
     * 初始化小标题提示语
     */
    private void initTvTip() {
        mTitleTv.setText(mPresenter.title());
        mActivityTv.setText(mPresenter.activityTvTip());
        mFunctionNameTv.setText(mPresenter.functionNameTvTip());
        mModuleDetailTv.setText(mPresenter.moduleDetailTvTip());
        mTrigValueTv.setText(mPresenter.trigValueTvTip());
        mExtendTv.setText(mPresenter.extendTvTip());

        //课本
        mDataIdTv.setText(mPresenter.dataIdTip());
        mDataEditionTv.setText(mPresenter.dataEditionTip());
        mDataExtendTv.setText(mPresenter.dataExtendTip());
        mDataGradeTv.setText(mPresenter.dataGradeTip());
        mDataPublisherTv.setText(mPresenter.dataPublisherTip());
        mDataSubjectTv.setText(mPresenter.dataSubjectTip());
        mDataTitleTv.setText(mPresenter.dataTitleTip());
        mDataTypeTv.setText(mPresenter.dataTypeTip());

        //用户信息
        mUserBirthdayTv.setText(mPresenter.userBirthdayTip());
        mUserExtendTv.setText(mPresenter.userExtendTip());
        mUserGradeTv.setText(mPresenter.userGradeTip());
        mUserIdTv.setText(mPresenter.userIdTip());
        mUserNameTv.setText(mPresenter.userNameTip());
        mUserSexTv.setText(mPresenter.userSexTip());
        mUserPhoneNumTv.setText(mPresenter.userPhoneNumTip());
        mUserAgeTv.setText(mPresenter.userAgeTip());
        mUserSchoolTv.setText(mPresenter.userSchoolTip());
        mUserGradeTypeTv.setText(mPresenter.userGradeTypeTip());
        mUserSubjectsTv.setText(mPresenter.userSubjectsTip());

        mTrigValueTv.setVisibility(mPresenter.showTrigValue() ? View.VISIBLE : View.GONE);
    }

    /**
     * 初始化输入框提示
     */
    private void initEtTip() {
        mActivityEt.setText(mPresenter.activityEtTip(ParameterActivity.class.getName()));
        mFunctionNameEt.setText(mPresenter.functionNameEtTip());
        mModuleDetailEt.setText(mPresenter.moduleDetailEtTip());
        mTrigValueEt.setText(mPresenter.trigValueEtTip());
        mExtendEt.setText(mPresenter.extendEtTip());

        //课本
        mDataIdEt.setText(mPresenter.dataId());
        mDataEditionEt.setText(mPresenter.dataEdition());
        mDataExtendEt.setText(mPresenter.dataExtend());
        mDataGradeEt.setText(mPresenter.dataGrade());
        mDataPublisherEt.setText(mPresenter.dataPublisher());
        mDataSubjectEt.setText(mPresenter.dataSubject());
        mDataTitleEt.setText(mPresenter.dataTitle());
        mDataTypeEt.setText(mPresenter.dataType());

        //用户信息
        mUserBirthdayEt.setText(mPresenter.userBirthday());
        mUserExtendEt.setText(mPresenter.userExtend());
        mUserGradeEt.setText(mPresenter.userGrade());
        mUserIdEt.setText(mPresenter.userId());
        mUserNameEt.setText(mPresenter.userName());
        mUserSexEt.setText(mPresenter.userSex());
        mUserPhoneNumEt.setText(mPresenter.userPhoneNum());
        mUserAgeEt.setText(mPresenter.userAge());
        mUserSchoolEt.setText(mPresenter.userSchool());
        mUserGradeTypeEt.setText(mPresenter.userGradeType());
        mUserSubjectsEt.setText(mPresenter.userSubjects());

        mTrigValueEt.setVisibility(mPresenter.showTrigValue() ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取输入内容
     *
     * @param editText
     * @return
     */
    private String getTextFromEt(EditText editText) {
        return editText.getText().toString();
    }

}
