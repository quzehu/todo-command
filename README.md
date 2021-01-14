# 极客时间—代码之丑练习项目

## 实现一个待办事项管理软件，是以命令行应用的方式存在

## 第一阶段需求 基本功能

1. 添加Todo项
2. 完成Todo项
3. 查看Todo列表，缺省情况下，只列出未完成的Todo项
4. 使用all参数，查看所有的Todo项目

```
todo add <item>
1. <item>
Item <itemIndex> added
```
```
todo done <itemIndex>
Item <itemIndex> done.
```
```
todo list
1. <item1>
2. <item2>
Total: 2 items
```
```
todo list --all
1. <item1>
2. <item2>
3. [Done] <item3>
Total: 3 items, 1 item done
```
要求：
1. Todo项存储在本地文件中;
2. Todo项索引逐一递增。

##  第二阶段需求 支持多用户
1. 用户登录
2. 用户退出
```
todo login -u user
Password:

Login success!
```
```
todo logout

Logout success!
```
要求:
1. 只能看到当前用户的Todo列表
2. 同一个用户的Todo索引逐一递增
3. 当前用户信息存储在配置文件中 .todo-config


## 第三阶段需求 支持Todo列表导入和导出
1. Todo列表导出
2. Todo列表导入

```$xslt
todo export > todolist
```
```$xslt
todo import -f todolist
```
## 第四阶段需求 支持数据库持久化
在配置文件中，配置数据库连接信息
1. 初始化数据库
```$xslt
todo init
```
要求：
1. 没有数据库的情况下，使用本地文件
2. 在有数据库的情况下，使用数据库
3. 在本地文件已经存在的情况，将本地信息导入到数据库中

# 附加说明
1. 支持“todo help”的帮助命令。 
2. 支持输入密码五次校验，校验不通过强制退出，校验次数可以在配置文件中修改。
3. 导入文件仅支持Excel文件导入。
4. 导出支持Txt文件和Excel文件，默认导出Excel文件，配置文件可修改
5. 持久化策略有Mysql数据库、本地txt文件、仅内存(不能持久化)三种方式，
默认使用Mysql数据库持久化，配置文件中可以修改使用的持久化策略。
6. 支持内存缓存读取策略(目前无论数据量多少，都从内存缓存读取)，
后期增加数据量大不使用内存缓存功能。
7. 支持缓存加载时机策略，提供启动项目加载所有用户、懒加载两种方式，
默认采用懒加载方式，可在配置文件中修改。
8. 内置系统用户在todo-config.txt配置文件中
9. 支持注册新用户。   
10. 用户必须登录，才能使用此应用。
## 备注
此命令行应用，暂不支持同一用户，多窗口登录。

## 运行项目
```
mvn spring-boot:run
```
## 构建打包 部署
```
mvn clean package

java -jar target/todo-command.jar
```






















































