
Part 1: Excel driven framework



Part2: log diff framework.
1. 命令参数java -Xms2048m -Xmx2048m -jar logdiffrunnerv3.jar -apiName deepinfo -logLocation aseswsvalueaddeddeepinfo -module maps -urlA 10.101.12.24 -urlB 10.101.12.24 -threadCount 16 
2.日志格式，tengine过滤格式类似于，换行分割。

/ws/valueadded/deepinfo/search?sign=3957CF81D57C293CC271497EB3CCDA4F&channel=pc_openapi&poiid=B001B16DGU&mode=255&cms_ver=2&output=json&dip=11060&request_serial_number=c76a6f32474e4e99814aa4c9778088c0&gsid=aosmapsproxy010185216197.su18ee1b62c72465865e56427d2c8d586d0a

3. 对比字段，第一次运行会生成验证配置文件，格式如下，建议第一次运行选取样本日志量100-1000条之间，输出受输入参数影响越大，选取样本日志量越大。同一api所有case公用一个验证规则。

#the compare rules are: equal, notnull, exist, count, regex, precision, nocheck.
entireStringEquals=false 是否使用全局字符串相等比较。
stringRegex= 
code:json=equal 相等
timestamp:json=nocheck 不验证
version:json=equal
result:json=equal
message:json=equal
poiinfo.idDictionaries.index_info_id:json=equal
poiinfo.idDictionaries.hotel_dianping_api_id:json=equal
poiinfo.idDictionaries.dianping_api_id:json=equal
poiinfo.idDictionaries.muku_id:json=equal
poiinfo.idDictionaries.sinapoiid:json=equal
poiinfo.idDictionaries.alio2o_api_id:json=equal
poiinfo.idDictionaries.weibo_sina_id:json=equal
poiinfo.idDictionaries.groupbuy_meituan2_api_id:json=equal
poiinfo.idDictionaries.hotel_qunar_api_id:json=equal
poiinfo.idDictionaries.con_toplist_id:json=equal
poiinfo.idDictionaries.hotel_elong_api_id:json=equal
#the compare rules are: equal, notnull, exist, count, regex, precision, nocheck.

推荐使用最新版本

logdiffrunnerv4.jar  

v4 优化内存使用。

logdiffrunnerv5.jar

v5优化报表统计,增加时间统计。

logdiffrunnerv6.jar

v6,提高稳定性， 修改thread，增加console输出running&failed  count，暂去掉邮件通知。

logdiffrunnerv7.jar

v7,bug  -  fix ，exist比较规则修正。

logdiffrunnerv8.jar

v8: 针对全部pass场景优化。
