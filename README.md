# CourseDir
仿石墨文档的文件管理效果

#### 使用技术
1.使用support V4包的FragmentManager管理fragment栈，做到点击返回时推出栈。

2.使用jetpack思想构建项目框架。做到Activity和Fragment与控制器交互，控制器与网络数据和本地数据交互，数据变化时，及时刷新UI。

3.Lifecycler模式去控制对应UI控件的生命周期

n.自定义可控制长度的EditText，实现控制输入长度为最大20.
