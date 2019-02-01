# bfc-behavior-aidl (BFC内部使用)
中间件项目：行为采集aidl库

# 关于
- 版本：V1.0
- API >= 15

# 说明
## 由来
为什么会有行为采集aidl库?为了尽量做到BFC库之间解藕.目前这是个BFC内部使用的库.aidl埋点解决bfc模块间埋点调用,但是不想相互依赖的问题.如:bfc-Http库想要埋点,但是不想相互间直接依赖耦合,就可以使用aidl埋点.<br>
除此,其实还有另外一个重要原理,行为采集库的初始化.由于需要自动采集界面时长,所以行为采集库初始化时要传application,但是其他库一般都是只有Context,改动起来也很麻烦,而且这改动也影响了上层的调用修改.

## bfc-behavior 与 bfc-behavior-aidl 关系
- 1.bfc-behavior由app引入,bfc-behavior-aidl由需要埋点的BFC库来引入;app无需关心bfc-behavior-aidl,需要埋点的BFC库也无需关心bfc-behavior.
- 2.app引用bfc-behavior,并且需要埋点的BFC库引入bfc-behavior-aidl库,则双方都可以正常使用埋点.
- 3.app只需引用bfc-behavior,则app就可以正常使用埋点.
- 4.需要埋点的BFC库引入bfc-behavior-aidl,但是app没有引用bfc-behavior时,不会导致崩溃,但是需要埋点的BFC库不能实现埋点功能.
- 5.bfc-behavior-aidl通过aidl,调用bfc-behavior的BehaviorRemoteService.java,最终和app调用同一套接口和逻辑,可以方便管理.
- 6.bfc-behavior-aidl不需要关心bfc-behavior的初始化和设置.所有的设置和初始化等最高权限由上层app设置bfc-behavior决定,此可避免设置冲突.
        eg:app关闭埋点开关,aidl也无法埋点.
- 7.bfc-behavior 所有设置作用域 **包括** bfc-behavior-aidl;bfc-behavior-aidl 所有设置作用域仅限于当前的bfc-behavior-aidl使用,**不影响** bfc-behavior.

>非BFC模块可无视上面说明.

# 功能列表
- 1.计次事件
- 2.计数事件
- 3.页面切换事件
- 4.自定义事件
- 5.搜索事件
- 6.马上上传所有数据
- 7.获取版本号
- 8.异常捕获
- 9.自动采集activity页面切换事件

# 配置

## aar
请根据BFC公共配置文档进行配置：

[BFC公共配置文档地址](http://172.28.2.93/bfc/Bfc/blob/master/public-config/%E4%BE%9D%E8%B5%96%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md "配置文件地址")

	    // aidl行为采集的依赖
	    compile bfcBuildConfig.deps.'bfc-behavior-aidl'

## jar
jar包请到一下地址下载:
[jar下载地址](http://172.28.1.147:8081/nexus/content/repositories/thirdparty/com/eebbk/bfc/bfc-behavior-aidl/ "jar下载地址")

>下载最新版本的bfc-behavior-aidl-xxx.jar,不要下bfc-behavior-aidl-xxx-sources.jar 和 bfc-behavior-aidl-xxx-bugfix-javadoc.jar

# 使用

## 权限声明

        <!-- 允许程序写入外部存储，如SD卡上写文件 -->
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
        <!-- 允许程序写入外部存储，如SD卡上读文件 -->
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

## 初始化
### 简单初始化配置
最基本的初始化方法，所有设置都是默认设置：

		BfcBehaviorAidl bfcBehaviorAidl = new BfcBehaviorAidl.Builder().build(application);

### 带参初始化配置
如果需要附加其他设置的，可以调用下面的代码：

        BfcBehaviorAidl bfcBehaviorAidl = new BfcBehaviorAidl.Builder()
                      .enable(true)                         // aidl采集库开关,此开关不影响 bfc-behavior 库中的开关,默认 true 打开
                      .debugMode(false)                     // 调试模式,默认false
                      .sessionTimeout(30 * 1000)            // (单位毫秒) app在后台sessionTimeout后，再进入前台则调用app启动事件,默认30秒
                      .openActivityDurationTrack(true)      // 自动采集activity使用时长,默认true
                      .setOnServiceConnectionListener(l)    // aidl服务绑定成功和意外断连回调监听接口
                      .build(application);

### 初始化配置参数说明：
- enable(boolean enable)
<br> 开启/关闭 aidl采集开关,此开关不影响 bfc-behavior 库中的开关,默认 true 打开
- crashEnable(boolean enable)
<br> 开启/关闭 异常捕获,默认 true 打开
- setCrashToast(boolean enable)
<br> 开启/关闭 捕获到异常信息后，弹出异常提示toast, 默认true
- crashAnrEnable(boolean enable)
<br> 开启/关闭 anr采集开关,默认 true 打开
- autoFilterAnr(boolean enable)
<br> 开启/关闭 自动过滤信息不全的anr异常,默认 true 过滤
- crashStrictModeEnable(boolean enable)
<br> 开启/关闭 严苛模式采集开关,默认 false 关闭
- crashNativeEnable(boolean enable)
<br> 开启/关闭 native异常采集开关,默认 true 打开
- ignoreBeforeCrash(String type, long timeMillis)
<br> 忽略设置的时间之前的异常信息
- debugMode(boolean isDebugMode)
<br> 调试模式,默认false
- sessionTimeout(long sessionTimeout)
<br> (单位毫秒) app在后台sessionTimeout后，再进入前台则调用app启动事件,默认30秒
- openActivityDurationTrack(boolean isOpen)
<br> 自动采集activity使用时长,默认true
- enableCollectLaunch(boolean enable)
<br> 自动采集app启动事件, 默认true
- setOnServiceConnectionListener(OnServiceConnectionListener l)
<br> aidl服务绑定成功和意外断连回调监听接口

#### debugMode 效果
- 显示所有打印.非debugMode只显示Log.INFO级别(包括Log.INFO)以上的log日志打印.

#### 严苛模式异常采集
需要采集严苛模式异常的,需要自行初始化严苛模式并要加上 **penaltyDropBox()** ,如:

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .penaltyDialog()
                .penaltyDropBox()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDropBox()
                .build());

#### ignoreBeforeCrash(String type, long timeMillis) 忽略设置的时间之前的异常信息
- 作用于:anr异常采集,严苛模式异常采集,native异常采集.
- 原因:这几种采集基本都是通过获取dropbox中保存的信息.dropbox中是基本永久保持,所以如果初次集成这些采集,或者app数据被清除,会出现把以前旧数据从新上报的现象,导致当前版本异常数据上涨.
预留此接口,app根据自己需求选择需不需要忽略某个时间之前的异常信息.

## 使用前
埋点前, **必须先绑定服务,否则无法使用**.不需要时,请解绑服务.

### 绑定服务:

        /**
         * 绑定服务
         * <p>
         * 注:使用此aidl库埋点前,必须先绑定
         * <p/>
         *
         * @param context
         */
        bfcBehaviorAidl.bindService(@NonNull Context context);

>非gradle编译,无引用bfc-behavior库的,调用上面的方法无法成功绑定服务.请使用下面的方法绑定.

<br>

        /**
         * 绑定默认系统服务
         * <p>
         * 注:使用此aidl库埋点前,必须先绑定
         * <p/>
         *
         * @param context
         */
        bfcBehaviorAidl.bindDefaultSystemService(@NonNull Context context);
<br>

        /**
         * 绑定服务
         * <p>
         * 注:使用此aidl库埋点前,必须先绑定
         * <p/>
         *
         * @param context
         * @param appPackageName
         */
        bfcBehaviorAidl.bindService(@NonNull Context context, @NonNull String appPackageName);

>aidl服务绑定需要时间(≈1s),所以调用绑定服务后马上调用其他接口,有可能会失败.

### 解绑服务:

        /**
         * 解绑服务
         *
         * @param context
         */
        bfcBehaviorAidl.unbindService(@NonNull Context context);

### 服务是否已经绑定好:

        /**
         * 服务是否已经绑定好
         * @return
         */
        bfcBehaviorAidl.isConnectionService();

### aidl服务绑定监听接口

        /**
         * aidl服务绑定成功和意外断连回调监听接口
         *
         * @param l
         */
        bfcBehaviorAidl.setOnServiceConnectionListener(OnServiceConnectionListener l);

## 使用：

### 计次事件:

        /**
         * 计次事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param extend          扩展信息
         */
        bfcBehaviorAidl.clickEvent(String curActivityName, String functionName, String moduleDetail, String extend);
<br>

        /**
         * 计次事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param extend          扩展信息
         * @param dataId          数据ID
         * @param dataTitle       数据标题
         * @param dataEdition     数据版本
         * @param dataType        数据类型
         * @param dataGrade       数据年级
         * @param dataSubject     数据科目
         * @param dataPublisher   数据出版者
         * @param dataExtend      数据扩展
         */
        bfcBehaviorAidl.clickEvent(String curActivityName, String functionName, String moduleDetail, String extend
                , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
                , String dataSubject, String dataPublisher, String dataExtend);

### 计数事件:

        /**
         * 计数事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param trigValue       触发本次动作的触发值，如视频播放的时长等，注：时长统一以毫秒(ms)为单位
         * @param extend          扩展信息
         */
        bfcBehaviorAidl.countEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend);
<br>

        /**
         * 计数事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param trigValue       触发本次动作的触发值，如视频播放的时长等，注：时长统一以毫秒(ms)为单位
         * @param extend          扩展信息
         * @param dataId          数据ID
         * @param dataTitle       数据标题
         * @param dataEdition     数据版本
         * @param dataType        数据类型
         * @param dataGrade       数据年级
         * @param dataSubject     数据科目
         * @param dataPublisher   数据出版者
         * @param dataExtend      数据扩展
         */
        bfcBehaviorAidl.countEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend
                , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
                , String dataSubject, String dataPublisher, String dataExtend);

### 自定义事件:

        /**
         * 自定义事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param trigValue       触发值
         * @param extend          扩展信息
         */
        bfcBehaviorAidl.customEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend);
<br>

        /**
         * 自定义事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param trigValue       触发值
         * @param extend          扩展信息
         * @param dataId          数据ID
         * @param dataTitle       数据标题
         * @param dataEdition     数据版本
         * @param dataType        数据类型
         * @param dataGrade       数据年级
         * @param dataSubject     数据科目
         * @param dataPublisher   数据出版者
         * @param dataExtend      数据扩展
         */
        bfcBehaviorAidl.customEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend
                , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
                , String dataSubject, String dataPublisher, String dataExtend);

### 搜索事件:

        /**
         * 搜索事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param keyWord         搜索的关键字
         * @param resultCount     搜索结果
         */
        bfcBehaviorAidl.searchEvent(String curActivityName, String functionName, String moduleDetail, String keyWord, String resultCount);
<br>

        /**
         * 搜索事件
         *
         * @param curActivityName 进入的activity
         * @param functionName    功能名称
         * @param moduleDetail    模块详细
         * @param keyWord         搜索的关键字
         * @param resultCount     搜索结果
         * @param dataId          数据ID
         * @param dataTitle       数据标题
         * @param dataEdition     数据版本
         * @param dataType        数据类型
         * @param dataGrade       数据年级
         * @param dataSubject     数据科目
         * @param dataPublisher   数据出版者
         * @param dataExtend      数据扩展
         */
        bfcBehaviorAidl.searchEvent(String curActivityName, String functionName, String moduleDetail, String keyWord, String resultCount
                , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
                , String dataSubject, String dataPublisher, String dataExtend);

### 界面切换事件:

- 开始:


        /**
         * 界面进入事件
         *
         * @param page 进入的activity
         */
        bfcBehaviorAidl.pageBegin(@NonNull String page);

- 结束:


        /**
         * 页面退出事件
         *
         * @param page         进入的activity
         * @param functionName 功能名称
         * @param moduleDetail 模块详细
         * @param extend       扩展信息
         */
        bfcBehaviorAidl.pageEnd(@NonNull String page, String functionName, String moduleDetail, String extend);
<br>

        /**
         * 页面退出事件
         *
         * @param page          进入的activity
         * @param functionName  功能名称
         * @param moduleDetail  模块详细
         * @param extend        扩展信息
         * @param dataId        数据ID
         * @param dataTitle     数据标题
         * @param dataEdition   数据版本
         * @param dataType      数据类型
         * @param dataGrade     数据年级
         * @param dataSubject   数据科目
         * @param dataPublisher 数据出版者
         * @param dataExtend    数据扩展
         */
        bfcBehaviorAidl.pageEnd(@NonNull String page, String functionName, String moduleDetail, String extend
                , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
                , String dataSubject, String dataPublisher, String dataExtend);

### 设置全局用户信息

        bfcBehaviorAidl.putAttr(BFCColumns.COLUMN_UA_USERID,"")         // 用户唯一id
                        .putAttr(BFCColumns.COLUMN_UA_USERNAME,"")      // 用户名
                        .putAttr(BFCColumns.COLUMN_UA_SEX,"")           // 性别
                        .putAttr(BFCColumns.COLUMN_UA_BIRTHDAY,"")      // 生日
                        .putAttr(BFCColumns.COLUMN_UA_GRADE,"")         // 年级
                        .putAttr(BFCColumns.COLUMN_UA_PHONENUM,"")      // 手机号码
                        .putAttr(BFCColumns.COLUMN_UA_AGE,"")           // 年龄
                        .putAttr(BFCColumns.COLUMN_UA_SCHOOL,"")        // 学校
                        .putAttr(BFCColumns.COLUMN_UA_GRADETYPE,"")     // 年级类型
                        .putAttr(BFCColumns.COLUMN_UA_SUBJECTS,"")      // 学科
                        .putAttr(BFCColumns.COLUMN_UA_USEREXTEND,"");   // 扩展字段 (请提交json格式)

### 马上上传所有数据:

        /**
         * 马上上传所有数据
         */
        bfcBehaviorAidl.realTime2Upload();

### 获取行为采集库版本信息:

        /**
         * 获取行为采集库版本信息
         *
         * @return
         */
        bfcBehaviorAidl.getBehaviorVersion();

### 获取行为采集aidl库版本信息:

        /**
         * 获取行为采集aidl库版本信息
         *
         * @return
         */
        bfcBehaviorAidl.getVersion();

### 获取aidl配置:

        /**
         * 获取aidl配置
         *
         * @return
         */
        bfcBehaviorAidl.getSettings();

### 设置aidl配置:

        bfcBehaviorAidl.setSettings(@NonNull Settings settings);

# 服务器查询采集数据

- 测试平台

	所有未发布的版本采集数据都会上报到此测试平台，测试的采集数据也不会录入正式数据库。地址：

	[测试平台地址](http://172.28.1.194:9380/openbdp/resources/page/checkdata.html "查询地址")

	>1.确保您的app是测试版本，如果是正式版本的采集数据在此服务器网站中无法查询。
	>2.服务器可能有延时，如果没有查询到，请过3到5分钟再查询一次。

- 正式平台

	[正式平台地址](http://webadmin.eebbk.com:9999/webadmin-authority/authority/authorization/navigation)

# 检验大数据采集是否成功

## 准备

1. 新旧版本jar包都在项目中的，请先删掉旧版，或者确保所有地方的引包都已经替换成**"com.eebbk.bfc.sdk.behavior.XXX"**
2. app有多个进程的。请自行处理进程间数据传递，或确保每个需要调用大数据采集的进程都已经初始化。
2. 确保wifi网络正常。
3. 确保手机系统时间正确。
4. 确保您的app当前版本是测试版本。如果是挂网发布版本，只能通过日志查看数据入库上报成功与否，无法从测试服务器中查看到数据。请和产品策划确认，如app该版本有发过大数据申请单，则数据已经进入正式数据库，测试平台中无法查询到。
<br>附查询埋点数据是否发布到正式库教程：
[查询是否发布教程地址](http://172.28.2.93/bfc/BfcBehavior/tree/develop/doc/%E5%8F%91%E5%B8%83%E7%89%88%E6%9C%AC%E6%9F%A5%E8%AF%A2%E6%95%99%E7%A8%8B/CHECK_VERSION_README.md "教程")
5. BehaviorCollector.getInstance().init()初始化时候，请打开调试模式setDebugMode(true);
6. log日志过滤不要选中自己的项目报名,需要显示整机的日志("No Filters").

## 检验

1. **数据入库**。logcat日志**tag** 过滤**"behavior"**，如果看到 **"插入一条行为采集信息：XXX"**，则数据入库成功。如下图：<br>
![insert](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/check_insert.png)<br>
2. **数据开始上报**。当前版本默认上报模式为：**app启动上报** 。依然按照上面对日志进行过滤，如果看到**"======DA数据开始上报======"**,则数据开始准备上报工作。如下图：
![uploading](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/check_uploading.png)<br>
3. **数据上报成功**。依然按照上面对日志进行过滤，如果看到**"上报成功，删除上报数据库的这条记录"**，则数据上报成功。如下图：
![uploading](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/check_upload_success.png)<br>
4. **服务器查询**。可以先到上面说的“服务器查询采集数据”点开服务器查询地址，根据应用名、机器码、触发时间段来查询相关采集信息。
5. 如果上述服务器没有查询到，别着急，服务器可能有延时，可以过3到5分钟再查询一次。（在使用高峰期有可能更久）
6. 如果还是没有查询到，请确保测试网络环境可用，并且有触发上报模式（默认是app启动会执行上报）。确保已经出发上报。
7. 还是没有查询到，试着看看数据是不是还保留在本地没有上报：拉取代码git clone git@172.28.2.93:bfc/BfcBehavior.git，运行里面的demo。打开测试-->功能测试 -->查询APP本地采集数据-->输入自己应用的包名-->点击“刷新/显示”，看看有没有你相关的数据。
8. 如果还没有，请打开手机或者平板的资源管理器-->Android-->data-->“应用包名”-->files-->UserBahavior,看看有没有.zip的压缩包，如果有，你想查询的数据可能在里面打包好，等待下一次触发上报。
9. 如果还是没有，请查看上面的配置和调用是否正确，再重复上述查询操作。

# 常见问题
- Q:native crash日志怎么分析:<br>
A:可以参考:[Android Native crash日志分析](http://www.cnblogs.com/willhua/p/6718379.html "Android Native crash日志分析")

# 错误码
- 02050101  绑定行为采集aidl失败,XXX
- 02050102  没有绑定行为采集aidl,或app没有引入行为采集库
- 02050103  注册监听home键失败
- 02050104  注销home键监听失败
- 02050105  异常捕获模块初始化失败
- 02050601  配置信息设置失败，Settings不能为空

# 依赖
本项目已集成:
- support-annotations
	
# 联系人
- 何思宁
- 工号：20251494