# gcm-datagen 数据生成工具

gcm-datagen可以单独使用，也可以集成到gcm-bench中使用，两者的使用方法虽然不同，但是使用的是相同的参数，用户可以根据需要自行选择。


## 数据说明

数据生成工具生成的数据是以[WDCM](http://www.wdcm.org/)组织发布的微生物数据集[GCM](http://gcm.wdcm.org/)为模板，根据该数据集中数据的特点，生成的模拟数据集。数据集的Ontology文件请参考res/gcm.n3文件，生成的数据中包含了enzyme、pathway、taxonomy、protein、gene等数据，各项数据的比例可以用参数进行调节。


## 工具安装

### 单独编译安装

进入gcm-datagen目录，利用Maven编译安装（*确保Maven已经安装*）。在gcm-datagen文件夹下会生成打包完成的工具，名字为gcm-datagen-{version}.tar.gz
```bash
cd gcmbench/gcm-datagen
maven package
```

### 集体安装

整个项目一起编译（*确保Maven已经安装*），在项目的主目录生成gcmbench-{version}.tar.gz的打包文件。
```bash
cd gcmbench
maven package
```


## 命令行使用

将编译打包完成的文件解压，会得到以下目录的文件
```
.
├── bin
│   └── gcm.sh
├── gcm-datagen-0.1.jar
├── logs
└── res
    ├── gcm.n3
    └── name.txt
```
运行 `bin/gcm.sh [params]` 便可以启动数据生成工具，详细的使用方法参考下述的参数说明。


## gcm-bench中使用

将编译打包完成的文件解压，会得到以下的目录文件

**FIXME**
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

修改conf目录下的conf.properties文件可以对数据生成工具进行配置，各项配置的说明如下：

  - **dataGenerate**:  是否使用数据生成工具生成数据
  - **dataset**: 使用生成工具生成数据的文件名
  - **lineNumber**: 生成数据的行数
  - **enzymeRatio**: 同--ratio参数
  - **pathwayRatio**: 同--pathway参数 
  - **taxonRatio**: 同--taxon参数
  - **proteinRatio**: 同--protein参数
  - **geneRatio**: 同--gene参数


## 参数说明

 - **-o, --output=FILE**
 
 -o can be used to specify the file name FILE to save the data generated.

 - **-n, --lines=NUM**
 
 the program will generate 1,000,000 lines data as default, you can use -n to generate NUM line data.

 - **-h, --help**
            
 show help. 

 - **--enzyme=RATIO**

 you can specify the ratio of enzyme in whole data, or 0.0117 as default.

 - **--pathway=RATIO**
 
 the pathway's ratio is specified as RATIO, or 0.0287 as default.

 - **--taxonomy=RATIO**
 
 the taxonomy's ratio is specified as RATIO, or 0.0619 as default.

 - **--protein=RATIO**
            
 the protein's ratio is specified as RATIO, or 0.0523 as default.

 - **--gene=RATIO**

 the gene's ratio is specified as RATIO, or 0.784 as default.

 - **--genome=RATIO**
 
 the genome's ratio is specified as RATIO, or 0.0595 as default.
