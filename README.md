# bfc-Behavior
中间件项目：行为采集库

# 零、关于
- 版本：V1.0
- API >= 15

# 一、功能列表
- 用户行为信息采集
	1. 计次事件
	2. 计数事件
	3. 页面切换
	4. 自定义事件
	5. 搜索事件
- 异常信息采集
- 数据保存
	1. 缓存
	2. 数据库保存
- 数据上传
- 接收推送上传系统日志文件
- 机型兼容

# 二、默认采集信息

<center>
<table>
	<tr>
		<td><b>名称</b></td>
		<td><b>字段</b></td>
		<td><b>描述</b></td>
	</tr>
	<tr>
		<td>应用id</td>
		<td>appid</td>
		<td>包名的md5生成</td>
	</tr>
	<tr>
		<td>应用包名</td>
		<td>packagename</td>
		<td></td>
	</tr>
	<tr>
		<td>应用名称</td>
		<td>modulename</td>
		<td>application中设置的label</td>
	</tr>
	<tr>
		<td>应用版本号</td>
		<td>appver</td>
		<td>versionName</td>
	</tr>
	<tr>
		<td>imei</td>
		<td>imei</td>
		<td></td>
	</tr>
	<tr>
		<td>屏幕分辨率</td>
		<td>extend</td>
		<td>行为采集2.2.0版本才加入此采集，显示在"APP调出前台事件"的extend字段中</td>
	</tr>
	<tr>
		<td>android系统版本</td>
		<td>extend</td>
		<td>行为采集2.2.0版本才加入此采集，显示在"APP调出前台事件"的extend字段中</td>
	</tr>
	<tr>
		<td>触发时间</td>
		<td>trigtime</td>
		<td>触发采集埋点的时间</td>
	</tr>
	<tr>
		<td>机器序列号</td>
		<td>machineid</td>
		<td></td>
	</tr>
	<tr>
		<td>设备名称</td>
		<td>devicever</td>
		<td>如：H8s、S2、C1</td>
	</tr>
	<tr>
		<td>OS版本</td>
		<td>osver</td>
		<td></td>
	</tr>
	<tr>
		<td>厂商</td>
		<td>brand</td>
		<td></td>
	</tr>
	<tr>
		<td>行为采集工具版本</td>
		<td>daver</td>
		<td></td>
	</tr>
	<tr>
		<td>连接路由的MAC地址</td>
		<td>routerMac</td>
		<td></td>
	</tr>
	<tr>
		<td>机器的MAC地址</td>
		<td>romver</td>
		<td></td>
	</tr>
	<tr>
		<td>cpuid</td>
		<td>innerModel</td>
		<td></td>
	</tr>
</table>
</center>

# 三、升级清单文档
- 文档名称：[UPDATE.md](http://172.28.2.93/bfc/BfcBehavior/blob/master/UPDATE.md)

# 四、配置
请根据BFC公共配置文档进行配置：
[BFC公共配置文档地址](http://172.28.2.93/bfc/Bfc/blob/master/public-config/%E4%BE%9D%E8%B5%96%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md "配置文件地址")

        // 行为采集的依赖
        compile bfcBuildConfig.deps.'bfc-behavior'

>如果编译中发现“Multiple dex files define Lnet/lingala/zip4j/XXXX”   
或者有引用、间接引用BFC_Utils.jar的，请注释掉：
        

	    //compile group: 'net.lingala.zip4j', name: 'zip4j', version: '1.3.2'


>注意：请确保工程build.gradle中有设置applicationId，这里的applicationId就是app的包名如：


        defaultConfig {
            applicationId "com.eebbk.xxxx"
        }


# 五、使用

## 5.1 前置条件  
#### 5.1.1 需要动态申请的敏感权限

        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
        <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
        <uses-permission android:name="android.permission.RECORD_AUDIO"/>
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

> 5.0以上系统还需要在代码中动态申请权限,具体请查看Android API

#### 5.1.2 已申请的权限

        <uses-permission android:name="android.permission.READ_LOGS"/>
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
        <!-- bfc-upload merge -->
        <uses-permission android:name="android.permission.WAKE_LOCK" />
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
        <uses-permission android:name="android.permission.RECORD_AUDIO" />
        <uses-permission android:name="android.permission.WRITE_SETTINGS" />
        <uses-permission android:name="android.permission.GET_TASKS" />
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.CHANGE_CONFIGURATION" />
        <!-- bfc-common merge -->
        <uses-permission android:name="android.permission.VIBRATE"/>
        <uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"/>

> 在行为采集库中的AndroidManifest.xml中已申请以上所有权限

## 5.2 初始化
### 5.2.1 简单初始化配置
最基本的初始化方法，所有设置都是默认设置。建议是在Application中的onCreate()中加入下面代码：

		BehaviorCollector.getInstance().init(Application app);


### 5.2.2 带参初始化配置
如果需要附加其他设置的，可以调用下面的代码：

		BehaviorCollector.getInstance().init(BehaviorConfig config)；

使用方式如下：

		BehaviorCollector.getInstance().init(new BehaviorCollector.Builder(this)
                .enable(true)
                .enableCrash(true)
                .enableReport(true)
                .build());


### 5.2.3 初始化配置参数说明：
- enable(boolean enable) 
<br> 开启/关闭 大数据采集, 默认true
- enableCache(boolean enable)
<br> 开启/关闭 入库缓存 为了性能优化，减少频繁数据库操作, 默认false
- enableCrash(boolean enable)
<br> 开启/关闭 异常捕获, 默认true
- setCrashToast(boolean enable)
<br> 开启/关闭 捕获到异常信息后，弹出异常提示toast, 默认true
- setCrashUIReport(boolean enable)
<br> 开启/关闭 捕获到异常信息后，弹出异常信息显示界面, 默认false
- crashAnrEnable(boolean enable)
<br> 开启/关闭 anr异常采集, 默认true
- autoFilterAnr(boolean enable)
<br> 开启/关闭 自动过滤信息不全的anr异常,默认 true 过滤
- crashStrictModeEnable(boolean enable)
<br> 开启/关闭 严苛模式采集, 默认false.(需要采集严苛模式异常的,在初始化严苛模式时,需要加上 .penaltyDropBox())
- crashNativeEnable(boolean enable)
<br> 开启/关闭 native异常采集开关,默认 true 打开
- ignoreBeforeCrash(String type, long timeMillis)
<br> 忽略设置的时间之前的异常信息
- enableLogCache(boolean enable)
<br> 开启/关闭 日志缓存, 默认false
- enableReport(boolean enable)
<br> 开启/关闭 上传功能, 默认true
- openActivityDurationTrack(boolean enable)
<br> 自动采集Activity界面的进入与退出, 默认true
- enableCollectLaunch(boolean enable)
<br> 自动采集app启动事件, 默认true
- sessionTimeout(long timeout)
<br> (单位毫秒)app进入后台超时时间。如果超时，再进入前台则算是新的一次启动, 默认30秒
- setCacheIgnore(int... eventTypes)
<br> 设置不需要缓存，直接入库的数据类型
- setCachePolicy(int... policyTypes)
<br> 设置缓存策略
- setDebugMode(boolean enable)
<br> 调式模式 调式模式下，数据直接上传, 默认false. (调试模式是非常耗性能的，所以正式发布的版本一定要关闭！)
- setReportMode(int mode)
<br> 上报模式, 默认下次启动时上报
- setReportMode(IReportModeConfig config)
<br> 上报模式 带配置信息
- setReportSort(ISort sort)
<br> 数据过滤策略 上报时的数据过滤策略即数据的上报优先级 若null则不过滤
- setNetworkTypes(int networkType)
<br> 上报埋点数据可用的网络类型,可同时设置多种类型.默认只有wifi情况下上报.
- setModuleName(String moduleName)
<br> 设置所有埋点的模块名.（默认是应用名）

 ***`setDebugMode(boolean enable)调试模式是非常耗性能的，所以正式发布的版本一定要关闭！`***

#### 5.2.3.1 异常捕获UI显示效果

- setCrashToast(boolean enable) 异常提示toast（下图中显示的“MobileBehaviorDemo”是app名）。

![crash_toast](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/crash_toast.png)

- setCrashUIReport(boolean enable) 异常信息显示界面。PRODUCT为app名，除此外还会显示versionName和versionCode。(DebugMode下默认显示次界面)

![crash_ui](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/crash_ui.png)

#### 5.2.3.2 debugMode 效果

- 显示所有打印.非debugMode只显示Log.INFO级别(包括Log.INFO)以上的log日志打印.
- 每插入一条数据就直接上报.(方便调试问题,但是会耗性能,所以正式发布的版本一定要关闭！)
- 无论setCrashUIReport()是true或者false,捕获异常都会弹出异常信息显示界面.

#### 5.2.3.3 严苛模式异常采集
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

#### 5.2.3.4 ignoreBeforeCrash(String type, long timeMillis) 忽略设置的时间之前的异常信息
- 作用于:anr异常采集,严苛模式异常采集,native异常采集.
- 原因:这几种采集基本都是通过获取dropbox中保存的信息.dropbox中是基本永久保持,所以如果初次集成这些采集,或者app数据被清除,会出现把以前旧数据从新上报的现象,导致当前版本异常数据上涨.
预留此接口,app根据自己需求选择需不需要忽略某个时间之前的异常信息.

## 5.3 重要功能使用演示：

### 5.3.1 点击事件:

#### 5.3.1.1 方法一

        new ClickEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setExtend(extend)
                .setDataAttr(getDataAttr())
                .insert();

>调用insert()才会触发埋点入库。

#### 5.3.1.2 方法二

        ClickEvent clickEvent = new ClickEvent();
    	clickEvent.activity = 当前触发的activity;
    	clickEvent.functionName = 功能名称;
    	clickEvent.moduleDetail = 模块详细;
    	// 扩展信息
    	// 请提交json格式
   		// 或者使用 clickEvent.setExtend(map)，提交map
    	clickEvent.extend = 扩展字段；

    	BehaviorCollector.getInstance().clickEvent(clickEvent); 

两种方法都可以，效果一样。

>旧版本中可能会有用到clickEvent.eventName和clickEvent.eventType,在新版本中无需再填写，内部已经有处理。下同。

### 5.3.2 计数事件:

#### 5.3.2.1 方法一

        new CountEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setTrigValue(trigValue)
                .setExtend(extend)
                .setDataAttr(getDataAttr())
                .insert();

>调用insert()才会触发埋点入库。

#### 5.3.2.2 方法二

        CountEvent countEvent = new CountEvent();
        countEvent.activity = 当前触发的activity;
        countEvent.functionName = 功能名称;
        countEvent.moduleDetail = 模块详细;
        countEvent.trigValue = 数量;
        // 扩展信息
        // 请提交json格式
        // 或者使用 countEvent.setExtend(map)，提交map
        countEvent.extend = 扩展字段；

        BehaviorCollector.getInstance().countEvent(countEvent);

两种方法都可以，效果一样。

### 5.3.3 自定义事件:

#### 5.3.3.1 方法一

        new CustomEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setTrigValue(trigValue)
                .setExtend(extend)
                .setDataAttr(getDataAttr())
                .insert();

>调用insert()才会触发埋点入库。

#### 5.3.3.2 方法二

        CustomEvent customEvent = new CustomEvent();
        customEvent.activity = 当前触发的activity;
        customEvent.functionName = 功能名称;
        customEvent.moduleDetail = 模块详细;
        customEvent.trigValue = 自定义值;
        // 扩展信息
        // 请提交json格式
        // 或者使用 customEvent.setExtend(map)，提交map
        customEvent.extend = 扩展字段；

        BehaviorCollector.getInstance().customEvent(customEvent);
        
两种方法都可以，效果一样。

### 5.3.4 搜索事件:

#### 5.3.4.1 方法一

        new SearchEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setKeyWrod(keyWord)
                .setResultCount(resultCount)
                .setDataAttr(getDataAttr())
                .insert();

>调用insert()才会触发埋点入库。

#### 5.3.4.2 方法二

        SearchEvent searchEvent = new SearchEvent();
        searchEvent.activity = 当前触发的activity;
        searchEvent.functionName = 功能名称;
        searchEvent.moduleDetail = 模块详细;
        searchEvent.keyWrod = 搜索的关键字;
        searchEvent.resultCount = 搜索结果;

        BehaviorCollector.getInstance().searchEvent(searchEvent);

### 5.3.5 界面切换事件:
>需要注意的是参数page必须一一对应，否则采集失败！   

- 开始


		/**
	 	 * 界面进入事件
	 	 * 
	 	 * @param page 为 activity或fragment
     	 * @return
     	*/
        BehaviorCollector.getInstance().pageBegin(String page);

- 结束 


		/**
	 	 * 界面退出事件
	 	 * 
	 	 * @param page 为 activity或fragment
     	 * @return
     	*/
        BehaviorCollector.getInstance().pageEnd(String page);
<br>

		/**
	 	 * 界面退出事件
	 	 * 
	 	 * @param page 为 activity或fragment
	 	 * @param functionName 为 功能名称
	 	 * @param moduleDetail 为 模块详细
     	 * @return
     	*/
		pageEnd(String page, String functionName, String moduleDetail);
<br>

		/**
	 	 * 界面退出事件
	 	 * 
	 	 * @param page 为 activity或fragment
	 	 * @param functionName 为 功能名称
	 	 * @param moduleDetail 为 模块详细
	 	 * @param dataAttr 数据属性
	 	 * @param extend 扩展属性
     	 * @return
     	*/
		public boolean pageEnd(String page, String functionName, String moduleDetail, DataAttr dataAttr, Map extend);
<br>

		/**
	 	 * 界面退出事件
	 	 * 
	 	 * @param page 为 activity或fragment
	 	 * @param functionName 为 功能名称
	 	 * @param moduleDetail 为 模块详细
	 	 * @param dataAttr 数据属性
	 	 * @param extend 扩展属性
     	 * @return
     	*/
		public boolean pageEnd(String page, String functionName, String moduleDetail, DataAttr dataAttr, String extend);

### 5.3.6 用户信息设置:

        BehaviorCollector.getInstance().initUserInfo(new UserAttr()
                        .setBirthday() 		// 生日
                        .setGrade() 		// 年级
                        .setPhoneNum() 		// 手机号码
                        .setSex() 			// 性别
                        .setUserId() 		// 用户唯一id
                        .setUserName() 		// 用户名
                        .setAge() 			// 年龄
                        .setSchool() 		// 学校
                        .setGradeType() 	// 年级类型
                        .setSubjects() 		// 学科
                        .setUserExtend()); 	// 扩展字段 (请提交json格式)

### 5.3.7 马上上传所有采集数据:

        BehaviorCollector.getInstance().realTime2Upload();

### 5.3.8 查看当前行为采集库版本:

        BehaviorCollector.getInstance().getBehaviorVersion();

### 5.3.9 上报埋点数据可用的网络类型

    /**
     * <pre>上报埋点数据可用的网络类型：
     *  {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_WIFI}
     * , {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_MOBILE}
     * , {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_MOBILE_2G},
     * , {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_MOBILE_3G},
     * , {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_MOBILE_4G}
     * <br/>
     * 默认只有 wifi {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_WIFI}
     * <br/>
     * 如需使用多种类型的网络请用“|”连接，如 int networkTypes = {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_WIFI} | {@link com.eebbk.bfc.sdk.behavior.report.common.constants.NetworkType#NETWORK_MOBILE};
     * </pre>
     *
     * @param networkType 网络类型
     */
    public void setNetworkTypes(int networkType) {
        ConfigAgent.getBehaviorConfig().reportConfig.mNetworkTypes = networkType;
    }

### 5.3.10 APP调出前台事件
APP调出前台事件是用来统计app启动次数。目前的统计方式是基于activity的生命周期实现，如果有app是无activity的，需要app自己在启动的地方调用如下方法：

        BehaviorCollector.getInstance().appLaunch();


## 5.4 服务器查询采集数据

- 测试平台
	
	所有未发布的版本采集数据都会上报到此测试平台，测试的采集数据也不会录入正式数据库。地址：

	[测试平台地址](http://172.28.199.58:9380/openbdp/resources/page/checkdata.html# "查询地址")

	>1.确保您的app是测试版本，如果是正式版本的采集数据在此服务器网站中无法查询。   
	>2.服务器可能有延时，如果没有查询到，请过3到5分钟再查询一次。   
	
- 正式平台

	[正式平台地址](http://admin.eebbk.com:10000/webadmin-authority/authority/authorization/navigation)

# 六、检验大数据采集是否成功

## 6.1 准备

1. 新旧版本jar包都在项目中的，请先删掉旧版，或者确保所有地方的引包都已经替换成**"com.eebbk.bfc.sdk.behavior.XXX"**
2. app有多个进程的。请自行处理进程间数据传递，或确保每个需要调用大数据采集的进程都已经初始化。
2. 确保wifi网络正常。
3. 确保手机系统时间正确。
4. 测试时，请在BehaviorCollector.getInstance().init()初始化时候，打开调试模式setDebugMode(true);
5. 确保您的app当前版本是测试版本。如果是挂网发布版本，只能通过日志查看数据入库上报成功与否，无法从测试平台中查看到数据。查询方式：       
在[测试平台](http://172.28.199.58:9380/openbdp/resources/page/checkdata.html# "地址") 左上角输入“App名-版本号”,如下图，下拉框出现的版本则数据已经进入正式库，需要到正式平台查询。          
![](http://172.28.2.93/bfc/BfcBehavior/raw/develop_hsn/doc/res/check_version.png)           

>如图中显示“古文观止-1.3.0-all”中“all”的意思是：1.3.0的所有版本，如“1.3.0a”、“1.3.0b”、“1.3.0c”...

## 6.2 检验

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

## 6.3 旧版本行为采集库验证方法

请按照下面文档进行验证：
[旧版本行为采集库验证方法地址](http://172.28.2.93/bfc/BfcBehavior/blob/master/doc/%E6%97%A7%E7%89%88%E8%A1%8C%E4%B8%BA%E9%87%87%E9%9B%86%E7%BB%B4%E6%8A%A4%E6%96%87%E6%A1%A3/%E9%AA%8C%E8%AF%81%E9%87%87%E9%9B%86%E6%98%AF%E5%90%A6%E6%88%90%E5%8A%9F%E6%96%87%E6%A1%A3/OLD_CHECK_DATA_README.md "旧验证文档")

# 七、常见问题

## 7.1 配置
- httpmime-4.1.3 DuplicateFileException
![insert](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/gradle_execution_1.png)<br>
如果有遇到类似上图问题，解决办法： <br>
在gradle中添加如下代码


        android {
            packagingOptions {
                exclude('META-INF/LICENSE.txt')
                exclude('META-INF/NOTICE.txt')
            }
        }



## 7.2 新旧工具包替换中可能遇到问题
- Q：继承BFCApplication，怎么修改？
A：旧实现方式：


        @Override
        public boolean onCrashNeedUIReport() {
            return !DebugPrint.DEBUG_MODE;
        }

        @Override
        public boolean onNeedCrashExeception() {
            return !DebugPrint.DEBUG_MODE;
        }

        @Override
        public boolean onNeedToast() {
            return false;
        }

        @Override
        public UserAttr getUserAttr() {
            UserInfoPojo userInfoPojo = PersonalInfoUtils.getUserInfo(getApplicationContext());
            if (userInfoPojo == null) {
                return null;
            }

            String userName = userInfoPojo.getNickName();
            String sex = userInfoPojo.getSex();
            String grade = userInfoPojo.getGrade();

            return new UserAttr().setUserName(userName).setSex(sex).setGrade(grade);
        }

新实现方式：


		BehaviorCollector.getInstance().init(new BehaviorCollector.Builder(this)
                .enableCrash(!DebugPrint.DEBUG_MODE)
                .setCrashUIReport(!DebugPrint.DEBUG_MODE)
                .setCrashToast(false)
                .build()
        );
        BehaviorCollector.getInstance().initUserInfo(getUserAttr());

- Q：使用recordFunctionBegin()和recordFunctionEnd()的，怎么修改？  
A：recordFunctionBegin()和recordFunctionEnd()已经不建议使用了，可以用pageBegin()和pageEnd()实现。如：


		recordFunctionBegin(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr)；

		recordFunctionEnd(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr)；

新实现方式：

		BehaviorCollector.getInstance().pageBegin(String page);

        BehaviorCollector.getInstance().pageEnd(String page, String functionName, String moduleDetail, DataAttr dataAttr, String extend);

>避免参数重复提交、混乱，和效率的考虑，额外的参数统一在pageEnd()时候提交。

- Q：EventAttr，怎么修改？  
A：旧版本实现方式：


		new EventAttr(EType.XXXX)
				.setActivity(activity)
				.setEventName(eventName)
				.setFunctionName(functionName)
				.setModuleDetail(moduleDetail);

新实现方式：

		new EventAttr()
				.setPage(activity)
				.setEventType(EType.XXXX)
				.setEventName(eventName)
				.setFunctionName(functionName)
				.setModuleDetail(moduleDetail);

- Q：EebbkDA.java不见了！怎么办？
A：请用BehaviorCollector.getInstance()替换。
<br><br>
- Q：函数找不到了！怎么办？     
A：基本原有的函数都继续能使用，有部分大写开头的函数试试改成小写应该就能找到。

## 7.3 使用中的常见问题

- Q：app有多个进程，有影响吗？   
A：有。大数据不做跨进程处理。请自行处理进程间的数据传送，或确保每个需要调用大数据采集的进程都已经初始化。

- Q：上报数据模式不能满足怎么办？   
A：可以结合自身的业务需求，灵活利用及时上报接口（调用就会上报所有采集信息），定制自己想要的上报模式。

        BehaviorCollector.getInstance().realTime2Upload();

- Q：服务器查询查询不到采集数据！怎么办？    
	1. 确保您的app是测试版本，如果是正式版本的采集数据在此服务器网站中无法查询。
	2. 服务器可能有延时，如果没有查询到，请过3到5分钟再查询一次。

- Q:native crash日志怎么分析:
A:可以参考:[Android Native crash日志分析](http://www.cnblogs.com/willhua/p/6718379.html "Android Native crash日志分析")

- Q:埋点数据中的modulename可以修改吗？      
A:可以的，但是没有全局设置方法，需要在每个埋点事件都添加，以ClickEvent为例，其他事件也支持：
   
        ClickEvent clickEvent = new ClickEvent();
        clickEvent.activity = activity;
        clickEvent.functionName = functionName;
        clickEvent.moduleDetail = moduleDetail;
        clickEvent.extend = extend;
        
        // 修改modulename
        AidlCustomAttr attr = new AidlCustomAttr();
        Map<String, String> attrMap = new HashMap<>();
        attrMap.put(BFCColumns.COLUMN_AA_MODULENAME, "自定义modulename");
        attr.setMap(attrMap);
        clickEvent.addAttr(attr);

        BehaviorCollector.getInstance().clickEvent(clickEvent);

    注：任何 ***默认采集参数*** 都可以通过此方式修改。

# 八、错误码
- 02010101	数据入库失败，请先调用BehaviorCollector.getInstance().init()进行初始化
- 02010102	异常捕获模块初始化失败
- 02010201	在调用 pawo qgeEnd() 前必须要先调用 pageBegin()
- 02010202	在调用 recordFunctionEnd() 前必须要先调用 recordFunctionBegin()
- 02010203	数据入库失败，该functionName已经存在
- 02010301	记录的时间有问题，计算得到使用时长小于0
- 02010601	缓存配置设置失败，cacheConfig不能为空！
- 02010602	EventAttr参数不能为空
- 02010603	数据入库失败，数据为空
- 02010604	functionName不能为空
- 02010605	配置信息设置失败，behaviorConfig不能为空
- 02010606	Application context不能设置为空
- 02020601	数据数据为空，无法缓存上报
- 02030101	数据库provider初始化异常
- 02040101	上报模块初始化失败
- 02040102	初始化异常，无法执行上报操作
- 02040103	注册监听home键失败
- 02040104	注销home键监听失败
- 02040401	没有网络，无法执行上报操作
- 02040402	当前非wifi网络状态，仅在wifi下上报
- 02040601	上传参数有问题!! context = null
- 02040602	上传参数有问题!! zipFile = null
- 02040603	上传参数有问题!! zipFile文件不存在:xxxx
- 0204ff01	收到定时上报消息，但当前非定时上报模式，不做处理
- 0204ff02	收到推送上报消息，但当前非推送上报模式，不做处理
- 0204ff03	此上报模式尚未开启
- 0204ff04	无效上报触发.
- 0204ff05	创建上传文件失败
- 0204ff06	创建上传文件压缩失败
- 0204ff07	上报行为采集数据不支持类型,　taskType:xxxx

# 九、特殊情况
- 退出app上报数据风险   
目前版本的默认上报数据模式是启动app上报，也提供了退出app上报数据模式供使用。但是，据了解分析，退出app上报数据可能存在一定的风险。有部分手机对内存控制比较严格，在退出app时，可能会杀掉应用，导致无法上报或者数据丢失的风险。所以请需要退出app上报数据的应用自行风险管控。<br>
- 服务器数据查询延时   
根据上述“服务器查询采集数据”查询上报的数据，可能会有延时的情况，如果没有查到，请隔3到5分钟后再查询。
- 无activity的app     
对于行为采集自动采集的一些信息，是基于activity的生命周期的监听开发。如app启动，数据的上报，还有app使用时长统计都会受到影响。
所以，如果没有activity的app，并且也需要统计这些数据，需要自行实现。
    - app启动：在合适地方调用appLaunch()函数，每调用一次，会插入一条app启动事件。
    - 数据上报：在合适地方调用realTime2Upload()函数，就会触发上报所有数据。
    - app使用时长：调用pageBegin()和pageEnd()函数来实现app使用时长。

# 十、依赖
本项目已集成:

- bfc-log
- bfc-json
- bfc-common
- bfc-upload
- okhttp

# 十一、旧版行为采集维护
[旧版行为采集维护文档地址](http://172.28.2.93/bfc/BfcBehavior/blob/develop/doc/旧版行为采集维护文档/OLD_BFC_README.md "旧版维护文档")

# 十二、aidl行为采集文档(BFC内部使用)
[aidl行为采集文档地址](http://172.28.2.93/bfc/BfcBehavior/blob/develop/AIDL_README.md "aidl采集文档")

# 十三、资料
- [ANRs](https://developer.android.com/topic/performance/vitals/anr.html "ANRs")
- [anr分析资料](http://172.28.2.93/bfc/BfcBehavior/tree/develop/doc/%E5%85%B6%E4%BB%96/anr%E5%88%86%E6%9E%90 "anr分析资料")

# 十四、已申请异常进入大数据平台正式平台的库有：
- bfc-account-aidl
- bfc-accountsdk
- bfc-behavior-aidl
- bfc-behavior
- bfc-blockcanary
- bfc-blockcanary-no-op
- bfc-book-shelf-ui
- bfc-camera
- bfc-common
- bfc-common-res
- bfc-common-res-pad
- bfc-component-ui
- bfc-content-view-ui
- bfc-crypto
- bfc-db
- bfc-db-generator
- bfc-download
- bfc-feedback
- bfc-feedback-pad-ui
- bfc-feedback-phone-ui
- bfc-http
- bfc-imageloader
- bfc-leakcanary-no-op
- bfc-leakcanary
- bfc-log
- bfc-mic-support
- bfc-net-state-ui
- bfc-network-data-ui
- bfc-password-ui
- bfc-permission
- bfc-push
- bfc-push-common
- bfc-ringtone-ui
- bfc-sequencetools-json
- bfc-uploadsdk
- bfc-version
- bfc-version-core
- bfc-custom-widget-ui
- bfc-ijkplayer
- bfc-ijkplayer-java

# 十五、联系人
- 何思宁
- 工号：20251494