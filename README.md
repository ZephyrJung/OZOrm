# OZOrm

#### 介绍

一个学习Mybatis用的ORM框架，欢迎提供修改意见，不胜感激

#### 软件架构
略（有空了再补）

核心类：目前存在的基本都是核心类，个别可参考注释

实现该项目功能的核心点是：

1. 结果对象的动态转换，见`MysqlExecutor`
2. 接口的动态代理实现，见`MapperProxy`

#### 使用说明

目前只能手动调用，未能向mybatis一样于spring集成。

示例参考测试用例：

`org.zephyr.orm.binding.MapperProxyFactoryTest`

#### 下一步计划

- [x] 与spring/springboot集成，使得使用者可以像使用mybatis一样，只需要添加配置和接口，就可以实现sql的增删改查（简单的）。
- [ ] 代码组织更合理化以及性能的提高