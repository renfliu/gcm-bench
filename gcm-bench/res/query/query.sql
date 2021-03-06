# Query 1
# 简单的SPARQL查询，只有WHERE语句，结果为星型结构
# 获取Enzyme数据
# 测试结果通过
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?e rdf:type anno:EnzymeNode;
       anno:name ?name;
       anno:description ?description;
       anno:history ?history;
       anno:class ?class;
       anno:x-pathway ?pathway.
    OPTIONAL { ?e anno:keggGene ?gene. }
    OPTIONAL { ?e anno:product ?product. }
}
LIMIT 100


# Query 2
# 简单的SPARQL查询，只有WHERE语句，结果为雪花结构
# 获取与Gene相关的数据
# 结果太多，需要再完善
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
           anno:nodeRank ?taxonRank;
           anno:parentTaxid ?taxonParent.
    ?pathway rdf:type anno:PathwayNode;
                 anno:organism ?pathwayOrganism.
    ?genome rdf:type anno:GenomeNode;
            anno:definition ?genomeDefinition;
            anno:x-taoxn ?genomeTaxon;
            anno:accession ?genomeAccession.
    OPTIONAL {
        ?gene anno:x-protein ?protein.
        ?protein rdf:type anno:ProteinNode;
                 anno:x-taxon ?proteinTaxon;
                 anno:function ?proteinFuntion;
                 anno:sequenceLength ?proteinLength.
    }
}
LIMIT 100


# Query 3
# 带UNION的SPARQL查询
# 获取Taxonomy的信息、
# 通过测试，但是id的数字需要统一下
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    {
        ?taxonId anno:ancestorTaxid taxon:399171;
                 anno:nodeRank ?rank.
        ?nameId anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    } UNION {
        ?nameId anno:taxid taxon:399171;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    }
}
LIMIT 100

# Query 4
# 测试SPARQL中的FILTER
# 获取gene数据和enzyme数据
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/gene/>
PREFIX enzyme:<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?geneId rdf:type anno:GeneNode;
            anno:geneProduct ?product;
            anno:x-taxon ?taxon.
    FILTER reget(str(?))
    ?taxon rdf:type anno:TaxonNode;
                      anno:nodeRank ?rank.
    FILTER regex(str(?rank), "order", 'i').
}
LIMIT 100
# gene 没有与enzyme相连，没有product，废弃
#PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
#PREFIX gene:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/gene/>
#PREFIX enzyme:<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/>
#PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
#SELECT DISTINCT *
#WHERE {
#    ?geneId rdf:type anno:GeneNode;
#            anno:x-enzyme enzyme:%enzymeID%;
#            anno:geneProduct ?product.
#    FILTER regex(str(?product), "Cellulose", 'i').
#    enzyme:%enzymeID% rdf:type anno:EnzymeNode;
#                      anno:product ?enzymeProduct.
#    FILTER regex(str(?enzymeProduct), "diphosphate", 'i').
#}

# Query 5
# 测试SPARQL的排序性能
# 查找gene的数据，数量较大
# 测试通过，需要检验数据量很大时的性能表现，在数据量很大时，可能出现结果太多，占用太多内存
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/gene/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?geneID rdf:type anno:GeneNode;
            anno:geneType "protein-coding";
            anno:x-protein ?protein;
            anno:dbXrefs ?refs.
}
ORDER BY asc(?geneID)
LIMIT 100

# Query 6
# 测试SPARQL的排序性能
# 查找protein的数据
# 通过测试， 数据较小，通过调整?length的长度来调整数量的大小，只能进行字符串的比较
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?protein rdf:type anno:ProteinNode;
             anno:sequenceLength ?length.
    FILTER (?length < "5").
}
ORDER BY asc(?protein)
LIMIT 100

# Query 7
# 测试aggregate函数的性能, count
# 查找gene的数据
# 通过测试
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?geneId) as ?count)
WHERE {
    ?geneId rdf:type anno:GeneNode;
            anno:geneType "protein-coding".
}
LIMIT 100

# Query 8
# 测试aggregate函数的性能, max/min
# 测试通过，gstore只支持count
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT (max(?geneId) as ?max)
WHERE {
    ?geneId rdf:type anno:GeneNode;
            anno:geneType "protein-coding".
}
LIMIT 100

# Query 9
# 测试aggregate函数的性能, avg
# 数据中不存在整数类型
#PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
#PREFIX gene:<http://gcm.wdcm.org/data/gcmAnnotation1/gene/>
#PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
#SELECT DISTINCT (avg(?geneId) as ?avg)
#WHERE {
#    ?geneId rdf:type anno:GeneType;
#            anno:geneType "protein-coding".
#}

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
LIMIT 100

# Query 11
# 综合性的测试
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的相关信息，并对他们的'scientificName'进行限制（包含'2665'）
# 测试通过
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?rank
WHERE {
    {
        ?taxonid anno:ancestorTaxid taxon:5.
        ?nameId anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        ?taxonid anno:nodeRank ?rank.
        FILTER(regex(?name, '5')||regex(?rank, '5'))
    } UNION {
        ?nameId anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        taxon:5 anno:nodeRank ?rank.
        BIND(taxon:5 as ?taxonid)
        FILTER(regex(?name, '5'))
    }
}
OFFSET 0 LIMIT 15

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
        FILTER((regex(?name, 'n'))||(regex(?genomeid, 'n'))||(regex(?description, 'n'))).
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
        FILTER((regex(?name, 'n'))||(regex(?genomeid, 'n'))||(regex(?description, 'n'))).
    }
}
OFFSET 0 LIMIT 15

# Query 13
# 综合性测试
# 获取关于gene的数据
# 测试通过
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
                  anno:class ?class;
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
    }
    FILTER ( (?length > "1") && (?length < "6")).
}
OFFSET 0 LIMIT 20
