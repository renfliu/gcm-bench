# 平台驱动文档

gcm-bench测试框架采用的是驱动式的架构。测试框架已经实现了测试的功能，与被测试平台的连接需要编写相应的驱动来进行适配，系统已经提供了GStore和Jena两个系统的驱动。用户可以根据以下的文档为新平台编写驱动。


## 框架总体架构

gcm-bench测试框架主要分为三个主要部分，设置部分、平台运行部分和报告部分，三个部分相互依赖。设置部分用于读取配置，对系统环境进行设置，为其他部分提供方便的读取设置操作。平台运行部分用于在被测平台上运行负载，记录相关的数据。报告部分对运行的数据进行处理，并且生成报告。

平台运行部分已经将测试的逻辑完成，测试框架需要连接被测平台，加载数据集，运行SPARQL负载，卸载数据集，关闭连接。以上5个操作的具体操作与每个被测平台相关，需要在驱动程序中完成，而测试程序的运行，数据的收集由测试框架完成。

目前gcm-bench的测试报告是以HTML页面的形式展示出来的，在被测平台运行负载并且收集所有的数据后，系统会将测试数据发送给报告部分，报告部分将所有的数据整理后，读取系统的配置信息，将所有数据在报告中展示出来。


## 平台驱动接口

平台驱动需要完成的是继承Platform虚类和QueryJob虚类，并且实现其中的每个方法，Platform中的5个方法代表的是测试运行的5个步骤，为连接被测平台，加载数据集，运行SPARQL负载，卸载数据集，关闭连接。下面通过GStore的例子解释每个方法。

### 0. 建立GstoreQueryJob
```java
public class GstoreQuery extends QueryJob
```
在编写运行的5个步骤前，需要先定义要运行的QueryJob，这个类会添加到测试程序的执行队列中，测试程序执行时，依次执行队列中的Job。QueryJob中包含有SPARQL查询，实际上执行的就是其中的SPARQL查询。

### 1. 连接测试平台
```java
public void init() {
    try {
        GstoreConf gstoreConf = new GstoreConf();
        gstoreConf.loadFromFile();
        gc = new GstoreConnector(gstoreConf.getIp(), gstoreConf.getPort());
    } catch (IOException e) {
        throw new BenchmarkLoadException("读取配置文件出错: " + e.getMessage());
    }
}
```
Platform中的init()方法实现的是被测平台的初始化工作，可以在这里建立与被测平台的连接，并且做一些初始化的工作。

### 2. 加载数据集
```java
public void loadData() {
    gc.build(dataset, conf.getDataset());
    gc.load(dataset);
}
```
loadData()方法实现的是加载被测数据集，在这里可以加载数据生成工具生成的数据，也可以加载用户自定义的数据集。在加载数据集时进行的所有操作在这个方法中完成。

### 3. 运行SPARQL负载
```java
public void loadJob() {
    for (QueryJob job : getQueryFromFile()) {
        addQueryJob(job);
    }
}

public String query(String query) {
    String answer = gc.query(query);
    System.out.println(answer);
    return answer;
}
```
运行SPARQL负载需要实现两个方法，loadJob()方法负责建立GstoreQueryJob，并将这些Job通过addQueryJob()方法加载到系统的Job队列中去，测试程序运行时，会依次执行队列中的Job。GstoreQueryJob是QueryJob的子类。loadJob()方法有默认的实现，会读取测试框架的已经设计好的SPARQL查询，如果需要使用自定义的SPARQL查询，覆盖默认的loadJob()实现，提供自定义的实现便可。query()方法是执行SPARQL的方法，每个平台执行SPARQL的接口都不同，需要根据API提供具体的实现，在Gstore中使用连接的query()的方法便可以完成SPARQL查询，query()方法返回的是查询的结果。

### 4. 卸载数据集
```java
public void unload() {
    gc.unload(dataset);
}
```
完成SPARQL查询后，需要卸载数据集，如果不需要，方法留空即可。

### 5. 关闭连接
```java
public void exit() {
    // do nothing
}
```
所有的操作都完成后，需要关闭被测平台的连接，所有的关闭操作都可以在exit()方法中实现，如果不需要，留空即可。