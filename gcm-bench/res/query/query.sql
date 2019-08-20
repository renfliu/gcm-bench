# Query 1.1
# 简单的SPARQL查询，只有WHERE语句，结果为星型结构
# 获取Enzyme数据
# 测试结果通过
# 已通过Jena验证
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?rank ?nameId ?name
WHERE {
    ?taxonid anno:ancestorTaxid taxon:33;
             anno:nodeRank ?rank.
    ?nameId anno:taxid ?taxonid;
            anno:nameclass 'scientificName';
            anno:taxname ?name.
}
LIMIT 20


# Query 1.2
# 简单的SPARQL查询，只有WHERE语句，结果为雪花结构
# 获取与Gene相关的数据
# 已通过Jena验证
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?gene rdf:type anno:GeneNode;
          anno:x-taxon ?taxon;
          anno:x-pathway ?pathway;
          anno:x-genome ?genome.
    ?taxon rdf:type anno:TaxonNode;
           anno:nodeRank ?rank;
           anno:parentTaxid ?taxonParent.
    ?pathway rdf:type anno:PathwayNode;
                 anno:organism ?pathwayOrganism.
    ?genome rdf:type anno:GenomeNode;
            anno:definition ?genomeDefinition;
            anno:accession ?genomeAccession.
}
LIMIT 20


# Query 1.3
# 简单的SPARQL查询
# 已通过Jena验证
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?gene rdf:type anno:GeneNode;
          anno:geneType 'snRNA';
          anno:x-taxon ?taxon;
          anno:x-genome ?genome.
    ?taxon rdf:type anno:TaxonNode;
           anno:nodeRank 'species';
           anno:parentTaxid ?taxonParent.
    ?genome rdf:type anno:GenomeNode;
            anno:definition ?genomeDefinition;
            anno:x-taxon ?genomeTaxon;
            anno:accession ?genomeAccession.
}
LIMIT 20


# Query 1.4
# 简单的SPARQL查询，测试taxonmy的parentid这个循环join的性能
# 已通过Jena验证
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT ?t ?pid ?ppid ?pppid
WHERE {
    ?t rdf:type anno:TaxonNode;
       anno:parentTaxid ?pid.
    ?nameId anno:taxid ?t;
            anno:nameclass 'scientificName';
            anno:taxname ?p.
    ?pid rdf:type anno:TaxonNode;
         anno:parentTaxid ?ppid.
    ?ppid rdf:type anno:TaxonNode;
          anno:parentTaxid ?pppid.
}
ORDER BY ?t
LIMIT 20


# Query 2
# 简单的SPARQL查询，只有WHERE语句，结果为雪花结构
# 获取与Gene相关的数据
# 结果太多，需要再完善
# Jena可以，但gStore好像不行
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?gene rdf:platform anno:GeneNode;
          anno:x-taxon ?taxon;
          anno:x-pathway ?pathway;
          anno:x-genome ?genome.
    ?taxon rdf:platform anno:TaxonNode;
           anno:nodeRank ?taxonRank;
           anno:parentTaxid ?taxonParent.
    ?pathway rdf:platform anno:PathwayNode;
                 anno:organism ?pathwayOrganism.
    ?genome rdf:platform anno:GenomeNode;
            anno:definition ?genomeDefinition;
            anno:x-taxon ?genomeTaxon;
            anno:accession ?genomeAccession.
    OPTIONAL {
        ?gene anno:x-protein ?protein.
        ?protein rdf:platform anno:ProteinNode;
                 anno:x-taxon ?proteinTaxon;
                 anno:function ?proteinFuntion;
                 anno:sequenceLength ?proteinLength.
    }
}
LIMIT 20


# Query 3
# 带UNION的SPARQL查询
# 获取Taxonomy的信息、
# 通过测试，但是id的数字需要统一下
# 通过Jena测试
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    {
        ?taxonId anno:ancestorTaxid taxon:5;
                 anno:nodeRank ?rank.
        ?nameId anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    } UNION {
        ?nameId anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    }
}
LIMIT 20

# Query 4
# 测试SPARQL中的FILTER
# 获取gene数据和enzyme数据
# Jena测试通过, 名字分需要改
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/gene/>
PREFIX enzyme:<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?geneId rdf:type anno:GeneNode;
            anno:geneProduct ?product;
            anno:x-taxon ?taxon.
    FILTER regex(str(?product), 'Methanos', 'i')
    ?taxon rdf:type anno:TaxonNode;
                    anno:nodeRank ?rank.
    FILTER regex(str(?rank), 'order', 'i')
}
LIMIT 20

# Query 5
# 测试SPARQL的排序性能
# 查找gene的数据，数量较大
# Jena测试通过，需要检验数据量很大时的性能表现，在数据量很大时，可能出现结果太多，占用太多内存
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/gene/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?geneID rdf:type anno:GeneNode;
            anno:geneType 'protein-coding';
            anno:x-protein ?protein;
            anno:dbXrefs ?refs.
}
ORDER BY asc(?geneID)
LIMIT 20

# Query 6
# 测试SPARQL的排序性能
# 查找protein的数据
# Jena通过测试， 数据较小，通过调整?length的长度来调整数量的大小，只能进行字符串的比较
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT DISTINCT *
WHERE {
    ?protein rdf:type anno:ProteinNode;
             anno:sequenceLength ?length.
    FILTER (xsd:integer(?length) < 10).
}
ORDER BY asc(?protein)
LIMIT 20

# Query 7
# 测试aggregate函数的性能, count
# 查找gene的数据
# 通过测试
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?geneId) as ?count)
WHERE {
    ?geneId rdf:type anno:GeneNode;
            anno:geneType 'protein-coding'.
}


# Query 8
# 测试aggregate函数的性能, max/min
# 测试通过，gstore只支持count
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT (max(?geneId) as ?max) (min(?geneId) as ?min)
WHERE {
    ?geneId rdf:platform anno:GeneNode;
            anno:geneType 'protein-coding'.
}


# Query 9
# 测试aggregate函数的性能, avg, sum
# 数据中不存在整数类型
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/data/gcmAnnotation1/gene/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (avg(?length) as ?avgLength) (sum(?length) as ?s)
WHERE {
    ?geneId rdf:platform anno:GeneNode;
            anno:x-genome ?genome;
            anno:geneType ?geneType;
            anno:x-protein ?proteinId.
    OPTIONAL {
        ?geneId anno:chromosome ?chromosome.
    }
    ?proteinId rdf:platform anno:ProteinNode;
               anno:sequenceLength ?length.
}


# Query 10
# 综合性的测试
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的'scientificName'，并对这些'scientificName'进行限制（包含'2665'），并且计数
# 测试通过
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?name) as ?num)
WHERE {
    {
        ?taxonid anno:ancestorTaxid taxon:5.
        ?nameId anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    } UNION {
        ?nameId anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    }
}


# Query 11
# 综合性的测试
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的相关信息，并对他们的'scientificName'进行限制（包含'2665'）
# Jena测试通过,
# ?name filter 函数里面的匹配需要更改一下，重写nameGenerator
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?rank
WHERE {
    {
        ?taxonid anno:ancestorTaxid taxon:5;
                 anno:nodeRank ?rank.
        ?nameId anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    } UNION {
        ?nameId anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        taxon:5 anno:nodeRank ?rank.
        BIND(taxon:5 as ?taxonid)
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    }
}
OFFSET 0 LIMIT 20


# Query 12
# 综合性的测试
# 得到关联在http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上和其后代上的genome的相关信息，同时对这些genome的相关信息进行限制（某些属性中包含'SK58'字符串）
# 测试通过
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?genomeid ?description ?strain
WHERE {
    {
        ?taxonid anno:ancestorTaxid taxon:5.
        ?nameId rdf:type anno:TaxonName;
                anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        ?genomeid rdf:type anno:GenomeNode;
                   anno:x-taxon ?taxonid;
                   anno:definition ?description.
        OPTIONAL{
            ?genomeid anno:strain ?strain.
        }
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N'))).
    } UNION {
        ?nameId rdf:type anno:TaxonName;
                anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        ?genomeid rdf:type anno:GenomeNode;
                  anno:x-taxon taxon:5;
                  anno:definition ?description.
        OPTIONAL {
            ?genomeid anno:strain ?strain.
        }
        BIND(taxon:5 as ?taxonid)
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N'))).
    }
}
OFFSET 0 LIMIT 20


# Query 13
# 综合性测试
# 获取关于gene的数据
# Jena测试通过
# 需要更改name的生成方式
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT *
WHERE {
    {
        ?geneId rdf:type anno:GeneNode;
                anno:x-genome ?genome;
                anno:geneType ?geneType;
                anno:x-enzyme ?enzymeId.
        OPTIONAL {
            ?geneId anno:chromosome ?chromosome.
        }
        ?enzymeId rdf:type anno:EnzymeNode;
                  anno:class ?class.
        FILTER(regex(?class, 'n', 'i')).
    } UNION {
        ?geneId rdf:type anno:GeneNode;
                anno:x-genome ?genome;
                anno:geneType ?geneType;
                anno:x-protein ?proteinId.
        OPTIONAL {
            ?geneId anno:chromosome ?chromosome.
        }
        ?proteinId rdf:type anno:ProteinNode;
                   anno:sequenceLength ?length.
        FILTER ((xsd:integer(?length) < 500)).
    }
    
}
OFFSET 0 LIMIT 20
