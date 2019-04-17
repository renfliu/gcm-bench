# Query 1.1
# 简单的SPARQL查询，只有WHERE语句，结果为星型结构
# 获取Enzyme数据
# 测试结果通过
# 已通过Jena验证
SELECT ?taxonid ?rank ?nameId ?name
WHERE {
    ?taxonid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/33>;
             <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
    ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
}
LIMIT 20

# Query 1.2
# 简单的SPARQL查询，只有WHERE语句，结果为雪花结构
# 获取与Gene相关的数据
# 已通过Jena验证
SELECT DISTINCT *
WHERE {
    ?gene <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxon;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-pathway> ?pathway;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'ncRNA'.
    ?taxon <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?taxonParent.
    ?pathway <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/PathwayNode>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/organism> ?pathwayOrganism.
    ?genome <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?genomeDefinition;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/accession> ?genomeAccession.
}
LIMIT 20


# Query 1.3
# 简单的SPARQL查询
# 已通过Jena验证
SELECT DISTINCT *
WHERE {
    ?gene <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'snRNA';
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxon;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome.
    ?taxon <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> 'species';
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?taxonParent.
    ?genome <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?genomeDefinition;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?genomeTaxon;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/accession> ?genomeAccession.
}
LIMIT 20


# Query 1.4
# 简单的SPARQL查询，测试taxonmy的parentid这个循环join的性能
# 已通过Jena验证
SELECT DISTINCT ?t ?pid ?ppid ?pppid
WHERE {
    ?t <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
       <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?pid.
    ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?t;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?p.
    ?pid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
         <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?ppid.
    ?ppid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?pppid.
}
ORDER BY ?t
LIMIT 20




# Query 2
# 简单的SPARQL查询，只有WHERE语句，结果为雪花结构
# 获取与Gene相关的数据
# 结果太多，需要再完善
# Jena可以，但gStore好像不行
SELECT DISTINCT *
WHERE {
    ?gene <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxon;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-pathway> ?pathway;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome.
    ?taxon <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?taxonRank;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?taxonParent.
    ?pathway <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/PathwayNode>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/organism> ?pathwayOrganism.
    ?genome <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?genomeDefinition;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?genomeTaxon;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/accession> ?genomeAccession.
    OPTIONAL {
        ?gene <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-protein> ?protein.
        ?protein <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ProteinNode>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?proteinTaxon;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/function> ?proteinFuntion;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/sequenceLength> ?proteinLength.
    }
}
LIMIT 20


# Query 3
# 带UNION的SPARQL查询
# 获取Taxonomy的信息、
# 通过测试，但是id的数字需要统一下
# 通过Jena测试
SELECT DISTINCT *
WHERE {
    {
        ?taxonId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
    } UNION {
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
    }
}
LIMIT 20

# Query 4
# 测试SPARQL中的FILTER
# 获取gene数据和enzyme数据
# Jena测试通过, 名字分需要改
SELECT DISTINCT *
WHERE {
    ?geneId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneProduct> ?product;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxon.
    FILTER regex(str(?product), 'Methanos', 'i')
    ?taxon <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
    FILTER regex(str(?rank), 'order', 'i')
}
LIMIT 20

# Query 5
# 测试SPARQL的排序性能
# 查找gene的数据，数量较大
# Jena测试通过，需要检验数据量很大时的性能表现，在数据量很大时，可能出现结果太多，占用太多内存
SELECT DISTINCT *
WHERE {
    ?geneID <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'protein-coding';
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-protein> ?protein;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/dbXrefs> ?refs.
}
ORDER BY asc(?geneID)
LIMIT 20

# Query 6
# 测试SPARQL的排序性能
# 查找protein的数据
# Jena通过测试， 数据较小，通过调整?length的长度来调整数量的大小，只能进行字符串的比较
SELECT DISTINCT *
WHERE {
    ?protein <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ProteinNode>;
             <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/sequenceLength> ?length.
    FILTER (?length < 10)
}
ORDER BY asc(?protein)
LIMIT 20

# Query 7
# 测试aggregate函数的性能, count
# 查找gene的数据
# 通过测试
SELECT (count(?geneId) as ?count)
WHERE {
    ?geneId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'protein-coding'.
}


# Query 8
# 测试aggregate函数的性能, max/min
# 测试通过，gstore只支持count
SELECT DISTINCT (max(?geneId) as ?max) (min(?geneId) as ?min)
WHERE {
    ?geneId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'protein-coding'.
}


# Query 9
# 测试aggregate函数的性能, avg, sum
# 数据中不存在整数类型
SELECT (avg(?length) as ?avgLength) (sum(?length) as ?s)
WHERE {
    ?geneId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> ?geneType;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-protein> ?proteinId.
    OPTIONAL {
        ?geneId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/chromosome> ?chromosome.
    }
    ?proteinId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ProteinNode>;
               <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/sequenceLength> ?length.
}


# Query 10
# 综合性的测试
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的'scientificName'，并对这些'scientificName'进行限制（包含'2665'），并且计数
# 测试通过
SELECT (count(?name) as ?num)
WHERE {
    {
        ?taxonid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>.
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
    } UNION {
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
    }
}


# Query 11
# 综合性的测试
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的相关信息，并对他们的'scientificName'进行限制（包含'2665'）
# Jena测试通过,
# ?name filter 函数里面的匹配需要更改一下，重写nameGenerator
SELECT ?name ?rank
WHERE {
    {
        ?taxonid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    } UNION {
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
        BIND(<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5> as ?taxonid)
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    }
}
OFFSET 0 LIMIT 20

# Query 12
# 综合性的测试
# 得到关联在http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上和其后代上的genome的相关信息，同时对这些genome的相关信息进行限制（某些属性中包含'SK58'字符串）
# 测试通过
SELECT ?taxonid ?name ?genomeid ?description ?strain
WHERE {
    {
        ?taxonid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>.
        ?nameId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonName>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        ?genomeid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
                   <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxonid;
                   <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?description.
        OPTIONAL{
            ?genomeid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/strain> ?strain.
        }
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N')))
    } UNION {
        ?nameId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonName>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        ?genomeid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
                  <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                  <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?description.
        OPTIONAL {
            ?genomeid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/strain> ?strain.
        }
        BIND(<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5> as ?taxonid)
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N')))
    }
}
OFFSET 0 LIMIT 20

# Query 13
# 综合性测试
# 获取关于gene的数据
# Jena测试通过
# 需要更改name的生成方式
SELECT *
WHERE {
    {
        ?geneId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> ?geneType;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-enzyme> ?enzymeId.
        OPTIONAL {
            ?geneId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/chromosome> ?chromosome.
        }
        ?enzymeId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/EnzymeNode>;
                  <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/class> ?class.
        FILTER(regex(?class, 'n', 'i')).
    } UNION {
        ?geneId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> ?geneType;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-protein> ?proteinId.
        OPTIONAL {
            ?geneId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/chromosome> ?chromosome.
        }
        ?proteinId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ProteinNode>;
                   <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/sequenceLength> ?length.
        FILTER (?length < 500).
    }
    
}
OFFSET 0 LIMIT 20
