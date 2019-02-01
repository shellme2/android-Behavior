<center><h4>文档管理信息</h4>
<table>
	<tr>
		<td><b>版本</b></td>
		<td>V1.0</td>		
	</tr>
	<tr>
		<td><b>关键字</b></td>
		<td>中间件、maven库、说明</td>		
	</tr>
	<tr>
		<td><b>创建时间</b></td>
		<td>2016-9-19</td>		
	</tr>
	<tr>
		<td><b>创建人</b></td>
		<td>卢浪平</td>		
	</tr>
	<tr>
		<td><b>最新发布日期</b></td>
		<td>2016-9-19</td>		
	</tr>
</table></center>
<center><h4>文档变更记录</h4>
<table>
	<tr>
		<td><b>更改人</b></td>
		<td><b>日期</b></td>
		<td><b>更改内容</b></td>		
	</tr>
	<tr>
		<td>卢浪平</td>
		<td>2016-9-19</td>
		<td>创建文件</td>		
	</tr>
	<tr>
		<td>&emsp;</td>
		<td>&emsp;</td>
		<td>&emsp;</td>		
	</tr>
</table></center>  


#一、说明
中间jar包统一采用maven库管理，有公开和调试两个仓库，公开仓库提供给应用使用，调试仓库只提供给中间件开发人员使用。

#仓库配置
##1、调试
- **maven 库地址：**MAVEN\_SNAPSHOT\_URL = http://172.28.1.147:8081/nexus/content/repositories/thirdparty-snapshot/
- **对应maven的groupId值：** GROUP=com.eebbk.bfc
- **登录nexus oss的用户名：** NEXUS_USERNAME=admin
- **登录nexus oss的密码：** NEXUS_PASSWORD=admin123
- **groupid：** GROUP_ID = com.eebbk.bfc   

##### 参考项目地址：git@172.28.2.93:bfc/BfcJson.git


##2、公开
- **maven 库地址：** MAVEN\_SNAPSHOT\_URL = http://172.28.1.147:8081/nexus/content/repositories/thirdparty/
- **对应maven的groupId值：** GROUP=com.eebbk.bfc
- **登录nexus oss的用户名：** NEXUS_USERNAME=admin
- **登录nexus oss的密码：** NEXUS_PASSWORD=admin123
- **groupid：** GROUP_ID = com.eebbk.bfc   

##### 参考项目地址：git@172.28.2.93:bfc/BfcJson.git