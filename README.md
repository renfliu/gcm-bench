# GCMBench 

## Introduction

[网站介绍](http://prof.ict.ac.cn/bdb_uploads/GCM-Bench/)

RDF数据构成的知识图谱已经成为人工智能领域一支不可或缺的力量，同时也在许多其它领域发挥着重要的作用。而RDF数据管理系统的性能、容量等也影响着人们对这种工具的使用。GCMBench 是一个用于测试RDF管理系统的测试框架，可以对常用的RDF数据管理系统如Jena、GStore等进行测试，生成报告，以供研究人员参考。主要包含了RDF数据生成工具和系统评测框架两部分。


## gcm-datagen数据生成工具

gcm-datagen数据生成工具可以生成TB级的数据，来提供Benchmark使用。当然，用户也可以根据需要，单独使用数据生成工具生成数据，满足不同的使用需求。详细的使用方法请参考[使用说明](./doc/datagen.md)


## gcm-bench系统测试

gcm-bench是一个通用的RDF管理系统测试框架，可以对系统进行一系列的性能评测，并且生成评测报告供用户参考。详细的使用方法请参考[使用说明](./doc/bench.md)。gcm-bench同时是一个插件式的测试框架，系统提供了一系列针对各个不同平台的插件来对不同的平台进行测试。在使用时，用户可以根据不同的系统选择不同的平台插件来进行测试，对于不支持的平台，用户也可以根据Java接口编写自己的系统插件，来对该平台进行测试，开发接口参考[开发说明](./doc/drivers.md)