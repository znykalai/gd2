[配置文件]
 ### set log levels ###
log4j.rootLogger =  stdout,  D 

### 输出到控制台 ###
log4j.appender.stdout = org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target = System.out
log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern =  %d{ABSOLUTE} %5p %c:%L - %m%n

### 输出到日志文件 ###
log4j.appender.D = org.apache.log4j.DailyRollingFileAppender
log4j.appender.D.File = D:/logs/log.log
log4j.appender.D.Append = true
log4j.appender.D.Threshold= DEBUG 
## 输出DEBUG级别以上的日志
log4j.appender.D.layout = org.apache.log4j.PatternLayout
log4j.appender.D.layout.ConversionPattern =%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n

### 保存异常信息到单独文件 ###
log4j.appender.D = org.apache.log4j.DailyRollingFileAppender
log4j.appender.D.File = D:/logs/error.log 
## 异常日志文件名
log4j.appender.D.Append = true
log4j.appender.D.Threshold= ERROR 
## 只输出ERROR级别以上的日志!!!
log4j.appender.D.layout = org.apache.log4j.PatternLayout
log4j.appender.D.layout.ConversionPattern =%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r-%l ] - [ %p ]  %m%n