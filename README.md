# kingbase-dialect-for-spring-data

根据spring data官方的说法，要使用自己的数据库，需要满足以下三点

1.实现您自己的 Dialect。

2.实现返回 Dialect 的 JdbcDialectProvider。

3.通过在 META-INF 下创建 spring.factories 资源来注册提供程序，并通过添加一行来执行注册


本项目提供了人大金仓三种模式使用spring data的方言和方言解析器
