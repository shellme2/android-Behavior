# 旧版行为采集库检验大数据采集是否成功

1. wifi网络可以。
2. 确认当前的版本是测试版本。挂网发布的版本在下面的测试平台上是查询不到的！（请和产品策划确认，如app该版本有发过大数据申请单，则数据已经进入正式数据库，测试平台中无法查询到。）
<br>附查询埋点数据是否发布到正式库教程： 
[http://172.28.2.93/bfc/BfcBehavior/tree/master/doc/%E5%8F%91%E5%B8%83%E7%89%88%E6%9C%AC%E6%9F%A5%E8%AF%A2%E6%95%99%E7%A8%8B/CHECK_VERSION_README.md](http://172.28.2.93/bfc/BfcBehavior/tree/master/doc/%E5%8F%91%E5%B8%83%E7%89%88%E6%9C%AC%E6%9F%A5%E8%AF%A2%E6%95%99%E7%A8%8B/CHECK_VERSION_README.md "教程")
4. 机器的系统时间正确。
5. 过滤log日志的tag“behavior”
5. **上报策略** “Mode=”：<br>
（1）“NEXT”:下一次启动上报；<br>
（2）“QUANTITY”:定量上报,一般100-200条；<br>
（3）“QUANTITY_PERIOD”：定量 + XX时间间隔内只触发一次上报。
<br>如下图：
<br>![insert](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/old_mode.png)<br>
6. **插入记录是否成功** ,查看“当前数据库记录数”是否随触发埋点而增加。如下图：
<br>![insert](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/old_insert.png)<br>
7. **正在上报**，如果看到类似下图，“正在上报中....”和相关文件生成，则正在上报大数据埋点 。
<br>![uploading](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/old_uploading.png)<br>
7. **上报是否成功** 。如果看到有相关删除文件日志则已经上报成功。如下图：
<br>![upload_success](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/old_upload_success.png)<br>
8. **服务器查看数据** 。请到下面地址上查询上报服务器记录：     
[http://172.28.1.194:9380/openbdp/resources/page/checkdata.html](http://172.28.1.194:9380/openbdp/resources/page/checkdata.html "查询地址")
<br>

## 常见问题:
1. Q:上述的第6,7都没有相关打印!<br>
A:过滤log日志的tag“behavior”,如果 **有"insert exception,the BFC unsupport,please call checkBfcSupport;or you do not set specified application.get the reason."** 这样的打印,可能有一下几种情况:<br>
（1）如果 **有"bfc support exception,error code is "** 这个打印,可能是 **缺少BFC系统apk** .<br>
（2）如果 **没有"bfc support exception,error code is "** 这个打印,则是 **没有初始化** 行为采集(尤其是有 **多进程** 的,需要在每个调用到行为采集的进程里,在调用采集之前确保已经初始化).
<br><br><br>
	
# 联系人
- 何思宁
- 工号：20251494