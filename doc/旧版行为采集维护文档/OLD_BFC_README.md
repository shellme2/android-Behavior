# 旧版行为采集库维护

# 行为采集系统apk
<center>
<table>
	<tr>
		<td><b>机型</b></td>
		<td><b>系统版本</b></td>
		<td><b>采集系统apk版本</b></td>
		<td><b>git仓库</b></td>
		<td><b>分支</b></td>
	</tr>
	<tr>
		<td>S1</td>
		<td>V1.30</td>
		<td>BFCBehaviorV1.04_20150906</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>H9-apk</td>
	</tr>
	<tr>
		<td>S2</td>
		<td>V1.5.0</td>
		<td>BFCBehaviorV1.03_20150812</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>H9-apk</td>
	</tr>
	<tr>
		<td>H8S</td>
		<td>V1.40</td>
		<td>BFCBehaviorV1.04_20150906</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>H9-apk</td>
	</tr>
	<tr>
		<td>H9</td>
		<td>V1.30</td>
		<td>BFCBehaviorV1.04_20150906</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>H9-apk</td>
	</tr>
	<tr>
		<td>K3</td>
		<td>V1.40</td>
		<td>BFCBehaviorV1.05_20160220</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>M1000-apk</td>
	</tr>
	<tr>
		<td>M1000</td>
		<td></td>
		<td></td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>M1000-apk</td>
	</tr>
	<tr>
		<td>M2000</td>
		<td></td>
		<td>BFCBehaviorV1.05_20161124</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>M1000-apk</td>
	</tr>
</table>
</center>

# 行为采集jar包
<center>
<table>
	<tr>
		<td><b>采集jar名</b></td>
		<td><b>jar包版本</b></td>
		<td><b>是否需要采集系统支持</b></td>
		<td><b>是否为下海版</b></td>
		<td><b>git仓库</b></td>
		<td><b>分支</b></td>
	</tr>
	<tr>
		<td>eebbkDa.jar</td>
		<td>V2.0</td>
		<td>否</td>
		<td>是</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>M1000-apk</td>
	</tr>
	<tr>
		<td>BFC_BehaviorCollector.jar</td>
		<td>BFC_BehaviorCollector_V1.00_20161117</td>
		<td>是</td>
		<td>否</td>
		<td>android-4.1-apps/lib/BFC</td>
		<td>M1000-apk</td>
	</tr>
</table>
</center>

## 配置
请查看下面文档配置：<br>
[http://172.28.2.93/bfc/Bfc](http://172.28.2.93/bfc/Bfc)

### jar包下载
如果不能使用gradle编译，也可以通过下面路径获取旧库的jar包     

- 平板：<br>
[http://172.28.1.147:8081/nexus/content/repositories/thirdparty/com/eebbk/oldbfc/pad/bfc-behavior/](http://172.28.1.147:8081/nexus/content/repositories/thirdparty/com/eebbk/oldbfc/pad/bfc-behavior/)

- 手机：<br>
[http://172.28.1.147:8081/nexus/content/repositories/thirdparty/com/eebbk/oldbfc/phone/bfc-behavior/](http://172.28.1.147:8081/nexus/content/repositories/thirdparty/com/eebbk/oldbfc/phone/bfc-behavior/)

>建议使用新版本。

# 旧版本文档
- 设计文档：BFC/doc/设计文档
- 使用文档：BFC/doc/SDK文档
- demo：BFC/project/app

# 旧行为采集依赖关系

## 采集系统apk
- 项目名称：BFCBehavior
- 项目路径：BFC\project\app\BFCBehavior
- 依赖
	- UserBehaviorSystem
	- BFCUtils
	- UploadManager
	- BFC_BehaviorCollector.jar
	- BFC_Component_OrmLite_V1.00.jar
	- FastJson.jar

## BFC_BehaviorCollector.jar
- 项目名称：BehaviorCollector
- 项目路径：BFC\project\sdk\BehaviorCollector
- 依赖
	- BFCUtils

## eebbkDa.jar
- 项目名称：eebbkDA
- 项目路径：bfc\BFC\mobile\eebbkDA
- 依赖
	- bfc_utils.jar
	- android-support-v4.jar
	- commons-net-3.3.jar
	- gson-2.2.2.jar
	- httpmime-4.1.3.jar

# 旧版本问题修改记录
## app异常信息格式化
- 时间：2016年12月13日
- 机器：家教机、手机
- 概要描述：app异常采集信息，包括保存在.crash文件夹下的异常信息格式化
- 解决方案：1.FileWriter代替FileOutputStream；"\\\t"转成"\t","\\\n"转成"\n"
- 参与人员： 张明云 何思宁
- 家教机-原版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：H9-apk
	- git commit id：86c1267a1f88a39269ca1ab1d4c4465df73fd9a4
	- git commit：修改了上报数据的策略和增加了实时上报的配置文件
	- 版本：BFCBehaviorV1.00_20150803
	- 工程路径： BFC/project/sdk/BehaviorCollector

- 家教机-修改后版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：H9-apk
	- git commit id：1f4bb9d8eb3448307a16a1f7cfc67c79579cd12a
	- git commit：BFC_BehaviorCollector_V1.0.1_20161213
	- git tag：vBFC_BehaviorCollector_V1.0.1_20161213
	- 版本：BFC_BehaviorCollector_V1.0.1_20161213
	- 工程路径： BFC/project/sdk/BehaviorCollector
	- maven：com.eebbk.oldbfc.pad:bfc-behavior:1.0.1

- 手机-原版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：4fe5209b05fab3ff45f9701b480a03324a0a8cf9
	- git commit：EebbkDA_V3.0_20161202 添加imei和修改重入问题
	- git tag：vBFC_BehaviorCollector_V1.01_20161202
	- 版本：BFC_BehaviorCollector_V1.01_20161202
	- 工程路径： BFC/project/sdk/BehaviorCollector

- 手机-修改后版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：9af5028d233f0a8e4cdc6e8b4a6860b16c95c790
	- git commit：1.格式化行为采集的异常信息;2.上传数据切割,避免oom
	- git tag：vBFC_BehaviorCollector_V1.0.2_20161213
	- 版本：BFC_BehaviorCollector_V1.0.2_20161213
	- 工程路径： BFC/project/sdk/BehaviorCollector
	- maven：com.eebbk.oldbfc.phone:bfc-behavior:1.0.2

## H8s行为采集系统apk OOM,导致在线下载闪退
- 时间：2016年12月05日
- 机器：h8s
- 概要描述：大数据采集系统apk，上报数据时候OOM，导致在线下载界面闪退。
- 解决方案：把下海版中的上报数据切割部分代码移植过来。
- 参与人员： 张明云 陈志杨 雷祖花 何思宁
- 原版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：H9-apk-DA-V1.04_20150906-patch
	- git commit id：38e711d073120c074557b6b9b4a71bde10cdc393
	- git commit：提测v1.06_20161121版本
	- 版本：v1.06_20161121
	- 工程路径： BFC/project/app/BFCBehavior
	- 注：此版本非发布版本，是为了解决“H8S桌面插入大数据导致系统卡死问题”提测版本，最终此解决方案没有通过。
	﻿
- 补丁版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：H9-apk-DA-V1.04_20150906-patch
	- git commit id：c49aa7635b7611d20dd2106d3494b08346f96ba9
	- git commit：行为采集系统apk BFCBehaviorV1.06_20161205 提测
	- git tag：vBFCBehaviorV1.06_20161205
	- 版本：BFCBehaviorV1.06_20161205
	- 工程路径： BFC/project/app/BFCBehavior

## BehaviorCollector重入问题兼容
- 时间：2016年12月02日
- 机器：M1000 M2000 K3
- 概要描述：M1000-apk 分支同步解决“H8S桌面插入大数据导致系统卡死问题”
- 解决方案：入库添加锁。
- 参与人员： 张明云 何思宁
- 原版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：cf2d4e2d532a2536897b3c85a9e61bb67f6260ed
	- git commit：还原UserBehaviorSystem/project.properties设置
	- git tag：vBFC_BehaviorCollector_V1.00_20161117
	- 版本：BFC_BehaviorCollector_V1.00_20161117
	- 工程路径： BFC/project/sdk/BehaviorCollector
	﻿
- 补丁版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：b26b02c7a12d6530d4f1f39c8e2fccf8dc643898
	- git commit：行为采集vBFC_BehaviorCollector_V1.01_20161202
	- git tag：vBFC_BehaviorCollector_V1.01_20161202
	- 版本：BFC_BehaviorCollector_V1.01_20161202
	- 工程路径： BFC/project/sdk/BehaviorCollector

## 2G网络下限制大数据上报
- 时间：2016年11月25日
- 机器：M2000
- 概要描述：针对"H8S桌面插入大数据导致系统卡死问题"修改，解决方案提交到M1000-apk分支
- 解决方案：同"H8S桌面插入大数据导致系统卡死问题"。
- 参与人员： 张明云 何思宁
- 原版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：H9-apk
	- git commit id：1c3c33fa22f935c9424545496b6676570678f581
	- git commit：UserBehaviorSystem 兼容旧上报模式QUANTITY_PERIOD转NEXT上报模式
	- 版本：BFCBehaviorV1.04_20150906
	- 工程路径： BFC/project/app/BFCBehavior
	﻿
- 补丁版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：1c3c33fa22f935c9424545496b6676570678f581
	- git commit：UserBehaviorSystem 兼容旧上报模式QUANTITY_PERIOD转NEXT上报模式
	- git tag：vBFCBehaviorV1.05_20161124
	- 版本：BFCBehaviorV1.05_20161124
	- 工程路径： BFC/project/app/BFCBehavior

## 名师辅导班分享版添加imei字段
- 时间：2016年11月23日
- 机器：M2000
- 概要描述：下海版添加iemi字段。添加同步重入限制。
- 解决方案：下海版添加iemi字段。入库添加锁。
- 参与人员： 龙安忠 施伟彬 何思宁
- 原版本信息：
	- 施伟彬保存的版本
	- 版本：inner.ver.20161123
	﻿
- 补丁版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：4fe5209b05fab3ff45f9701b480a03324a0a8cf9
	- git commit：EebbkDA_V3.0_20161202 添加imei和修改重入问题
	- git tag：vEebbkDA_V3.0_20161202
	- 版本：EebbkDA_V3.0_20161202
	- 工程路径： BFC/mobile/eebbkDA

## H8S桌面插入大数据导致系统卡死问题
- 时间：2016年11月18日
- 机器：h8s
- 概要描述：行为采集系统apk ContentProvider死锁导致无法启动。机器启动时，桌面重入行为采集信息，出现概率较大。
- 解决方案：桌面更新，行为采集jar入库添加锁。
- 参与人员： 张明云 李通 卢浪平 何思宁
- 原版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：H9-apk
	- git commit id：58a4be1ae52f727432f90b21ba31e7d87801db38
	- git commit：change packeage name
	- 版本：BFC_BehaviorCollector_V1.00_20150803
	- 工程路径： BFC/project/sdk/BehaviorCollector
	﻿
- 补丁版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：H9-apk-DA-jar-V1.00_20150803-patch
	- git commit id：747911b69d0d917fb7dc2692b64387d4be8c2a7d
	- git commit：行为采集入库添加同步锁,解决h8s桌面卡死问题
	- git tag：vBFC_BehaviorCollector_V1.07_20161123
	- 版本：BFC_BehaviorCollector_V1.07_20161123
	- 工程路径： BFC/project/sdk/BehaviorCollector

## M2000无法采集机器序列号
- 时间：2016年11月16日
- 机器：M2000
- 概要描述：旧版行为采集jar包中，会判断是否为步步高产品机器。如果是则采集机器序列号，否则采集imei。由于新的手机M2000没有添加到行为采集jar包判断的白名单中，所以误认为是非步步高产品机器，采集了imei。
- 解决方案：无法获取机器序列号的app更新jar包。为了以后扩张性，目前先获取机器序列号，没有则获取imei。以后可能多添加一个字段，机器序列号和imei都同时采集。
- 更换jar文档地址：<br>[http://172.28.2.175:8080/xwiki/bin/view/%E4%B8%AD%E9%97%B4%E4%BB%B6/%E6%97%A7%E7%89%88%E8%A1%8C%E4%B8%BA%E9%87%87%E9%9B%86%E5%BA%93%E7%BB%B4%E6%8A%A4/](http://172.28.2.175:8080/xwiki/bin/view/%E4%B8%AD%E9%97%B4%E4%BB%B6/%E6%97%A7%E7%89%88%E8%A1%8C%E4%B8%BA%E9%87%87%E9%9B%86%E5%BA%93%E7%BB%B4%E6%8A%A4/ "文档地址")
- 参与人员： 龙安忠 施伟彬 何思宁
- 原版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：58da7ebdc0b4b105664d5ad14d35644b452121f8
	- git commit：将账户连接服务器超时时间上调至60S
	- 版本：BFC_BehaviorCollector_V1.00_20150803
	- 工程路径： BFC/project/sdk/BehaviorCollector
	﻿
- 补丁版本信息：
	- git：android-4.1-apps/lib/BFC
	- git分支：M1000-apk
	- git commit id：cf2d4e2d532a2536897b3c85a9e61bb67f6260ed
	- git commit：还原UserBehaviorSystem/project.properties设置
	- git tag：vBFC_BehaviorCollector_V1.00_20161117
	- 版本：BFC_BehaviorCollector_V1.00_20161117
	- 工程路径： BFC/project/sdk/BehaviorCollector
	
>旧版本问题修改记录备份地址：
[http://172.28.2.175:8080/xwiki/bin/view/%E4%B8%AD%E9%97%B4%E4%BB%B6/%E6%97%A7%E7%89%88%E8%A1%8C%E4%B8%BA%E9%87%87%E9%9B%86%E4%BF%AE%E6%94%B9%E8%AE%B0%E5%BD%95/](http://172.28.2.175:8080/xwiki/bin/view/%E4%B8%AD%E9%97%B4%E4%BB%B6/%E6%97%A7%E7%89%88%E8%A1%8C%E4%B8%BA%E9%87%87%E9%9B%86%E4%BF%AE%E6%94%B9%E8%AE%B0%E5%BD%95/ "旧版本问题修改记录备份地址")
	
# 联系人
- 何思宁
- 工号：20251494