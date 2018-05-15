# sience
设计思路：
1、对上游调用进行简单限流确保自身服务不会在高流量下被压垮。
2、开启线程池并行获取可用的支付方式，耗时最长时间默认为其中一个接口的最大耗时，
线程池核心线程数，队列大小，拒绝策略可以根据场景来配置。
3、调用下游服务增加断路器，防止下层服务挂掉，调用失败影响自身服务。断路器配置
可以从配置中心定时拉取。
4、日志记录可以生成一个全局id，通过threadLocal或者InheritableThreadLocal传递
做日志调用连。