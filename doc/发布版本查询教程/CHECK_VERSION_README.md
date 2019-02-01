# 教程：查询app某版本埋点数据是否发布到正式库

## 关于

### 现有问题
经常有同事测试埋点数据时，测试了半天，也等了半天，但是测试平台就是没有任何该版本的数据。最终发现该版本已经填了大数据埋点申请单，这时候该版本的数据在测试平台是查询不到了，数据已经进入正式数据库。
<br>这样的问题归根结底，是不知道有测试数据库和正式库，或者不知道自己app该版本的埋点数据是否已经进入正式数据库。

### 解决
本教程是手把手教你如何查看自己的app某版本的大数据是否已经挂网到正式库。
>此教程只适用于有发布“大数据埋点清单”（于2016年年底实施的埋点制度），如在此前发布的app，则无法通过此教程方法查询。

## 说明
-  **数据没有挂网。** 没有挂网的app对应版本，大数据埋点数据是不会进入正式数据库，所以可以在测试平台查询到：
<br> [http://172.28.1.194:9380/openbdp/resources/page/checkdata.html](http://172.28.1.194:9380/openbdp/resources/page/checkdata.html "测试平台")

-  **数据已挂网。** 数据已经挂网，则无法在测试平台查询到，可以找 **龙安忠** 或者 **陈凯**，申请大数据查询需求。然后在正式环境查询平台查询：
<br> [http://webadmin.eebbk.com:9999/webadmin-cas/login?service=http%3A%2F%2Fwebadmin.eebbk.com%3A9999%2Fwebadmin-authority%2Fauthority%2Fauthorization%2Fnavigation](http://webadmin.eebbk.com:9999/webadmin-cas/login?service=http%3A%2F%2Fwebadmin.eebbk.com%3A9999%2Fwebadmin-authority%2Fauthority%2Fauthorization%2Fnavigation "正式环境")

## 查询

- step 1
<br> 打开 **OA首页** -> **研发管理** -> **项目管理**

![演示1](http://172.28.2.93/bfc/BfcBehavior/raw/develop_hsn/doc/res/check_version_step1.png)

- step 2
<br> 点击 **大数据埋点申请单**，找到自己对应的项目。

![演示2](http://172.28.2.93/bfc/BfcBehavior/raw/develop_hsn/doc/res/check_version_step2.png)

- step 3
<br> 点开记录，查看版本号。
<br>(1) 如果是已经 **"公布"** 的，则埋点数据是已经提交到正式数据库。
<br>(2) 如果是 **"验证结果中"** 的，在这个状态下，你的数据可能已经发布到正式数据库，也能还没有，具体需要找 **龙安忠** 或者 **陈凯** 确认。
<br>(3) 如果在没有找到对应版本，则该版本的数据还 **没有** 进入正式数据库。

![演示3](http://172.28.2.93/bfc/BfcBehavior/raw/master/doc/res/check_version_step3.png)

## 联系
- 龙安忠
- 陈凯
- 何思宁