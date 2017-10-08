# gcm-bench 测试框架

gcm-bench是一个对RDF数据管理系统进行评测的框架，该框架通过对系统的数据加载、查询等操作进行评测，从而比较不同系统的性能，帮助用户选择合适的系统。gcm-bench采用了驱动式的设计，可以编写不同的驱动来适配不同的平台。框架本身提供了Jena、GStrore等系统的驱动，用户可以根据框架的接口为不同的平台的编写驱动，来测试更多的系统。


## 编译运行

进入GCMBench项目的根目录，用Maven编译打包整个项目（*确保Maven已经安装*）
```bash
cd gcmbench
maven package
```
程序的配置项在 `conf/conf.properties` 文件中，启动程序可以执行 `bin/bench.sh`。 


## 结构说明

编译打包后，得到gcmbench-{version}.tar.gz的压缩文件，解压该文件后，会得到以下目录的文件
```
.
├── bin
│   └── bench.sh
├── conf
│   └── conf.properties
├── gcm-bench-0.1.jar
├── logs
└── res
    ├── gcm.n3
    └── name.txt
```
 - **bin** 目录包含程序运行的脚本，启动程序可以运行 `bin/bench.sh`。
 - **conf** 目录包含了程序的配置文件，在运行测试程序之前，需要先对程序进行设置。配置项说明参考下一节。
 - **logs** 目录包含了系统运行时的日志，程序出现异常时可以查找相应的日志，确定问题出现的原因。
 - **res** 目录包含了系统运行时所需要的资源文件，请不要改动这里的文件。
 - **gcm-bench-{version}.jar** 文件是程序运行的主体


## 参数说明

 - 数据生成工具参数
   * **dataGenerate** 是否数据使用内置数据生成工具, 默认打开
   * **dataset** 输出文件的文件名，default=out
   * **lineNumber** 数据生成文件的行数, default=1,000,000
   * **enzymeRatio** enzyme数据所占的比例, default=0.0117
   * **pathwayRatio** pathway数据所占的比例, default=0.0287
   * **taxonRatio** taxonomy数据所占的比例, default=0.0619
   * **proteinRatio** protein数据所占的比例, default=0.0523
   * **geneRatio** gene数据所占的比例, default=0.784
 - Benchmark配置参数
   * **benchData** 若需要使用自定义的数据集，可以将数据生成开关关闭，并在此指定第三方的数据集进行测试
   * **sparql** 使用第三方的数据集时，需要自定义SPARQL查询，每个查询用空行分隔开
   * **benchType** 指定需要进行测试的程序，程序支持gstore，gena等。可以根据框架，自定义相应的程序
 - Gstore配置参数
   * **gstore_path** gsotre的安装路径, default=/opt/gstore
   * **gstore_ip** gstore开启server模式后，所在的ip地址， default=127.0.0.1
   * **gstore_port** gstore开启server的端口号， default=3305
 - Jena配置参数
   * **待补**


## 驱动编写

gcm-bench测试框架采用的是驱动式的架构。测试框架已经实现了测试的功能，与被测试平台的连接需要编写相应的驱动来进行适配，系统已经提供了GStore和Jena两个系统的驱动编写，用户可以参考这两个平台的驱动和驱动开发[文档](./drivers.md)来编写新的驱动。