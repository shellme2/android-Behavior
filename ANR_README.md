# ANR异常捕获常见问题

## ANR的现象
出现anr异常时,会弹出“XXX没有响应”对话框,如图：

![anr_system_dialog](http://172.28.2.93/bfc/BfcBehavior/raw/develop_hsn/doc/res/anr_system_dialog.png)

## .crash文件夹中无生产anr异常文件

如果 ***出现了上述anr现象*** ，但是.crash文件夹中没有生成anr异常文件，可以试试下面的方法：    

#### 方法一

完全退出app后， ***重启app*** 。确保app的pid改变了。查看是否有生成anr异常文件。

#### 方法二

- 1.可以用 ***pull_dropbox.bat*** 拷贝出异常文件来（文件可能很多，建议新建一个文件夹然后运行脚本）。[脚本下载地址](http://172.28.2.93/bfc/BfcBehavior/tree/develop_hsn/doc/%E5%85%B6%E4%BB%96/%E8%84%9A%E6%9C%AC "脚本下载地址")
- 2.查看文件名中有包含anr字样的文件，如 ***system_app_anr@xxxxxxx.txt*** 和 ***data_app_anr@xxxxxxx.txt***
- 3.点击打开这些anr文件，查看有无对应 ***app包名*** 且 ***触发时间*** 吻合的anr文件。如果有，则是对应的anr异常文件。

***注：只要出现“XXX没有响应”对话框，则方法二就会生成记录。如果方法二没有查找到对应anr异常文件,说明卡顿现象还不满足触发anr条件***

## .crash文件夹中无生成anr异常文件原因

- 1.出现anr后，有无重新启动（冷启动）app。
- 2.行为采集初始化时候，是否关闭了anr异常采集。crashAnrEnable(boolean enable)。false则关闭采集anr异常。

# 联系人
- 何思宁
- 工号：20251494