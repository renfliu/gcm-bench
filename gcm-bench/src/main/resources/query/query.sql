#################################################################################
##                            Taxonomy                                         ##
#################################################################################

# Query 1
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/51013的孩子和该孩子的scientificName
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT ?p ?t
WHERE {
	?t rdf:type anno:TaxonNode;
	   anno:parentTaxid taxon:51013.
	?nameId anno:taxid ?t;
	        anno:nameclass 'scientificName';
	        anno:taxname ?p.
}
ORDER BY ?p

# Query 2
# 得到某个taxonomy的孩子和该孩子的scientificName，并且这个孩子是要有孩子的。
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT ?p ?t
WHERE {
	?t rdf:type anno:TaxonNode;
	   anno:parentTaxid taxon:1078830.
	?nameId anno:taxid ?t;
	        anno:nameclass 'scientificName';
	        anno:taxname ?p.
	?temp anno:parentTaxid ?t.
}
ORDER BY ?p

# Query 3
# 得到以Micrococcus luteus开头的，且不区分大小写的scientificName的前10个并返回。
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT ?name
WHERE {
	?nameId rdf:type anno:TaxonName;
	        anno:nameclass 'scientificName';
	        anno:taxname ?name.
	FILTER(regex(?name, '^Micrococcus luteus', 'i'))
}
LIMIT 10


# Query 4
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270的父亲
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?name ?parentId
WHERE {
	taxon:1270 anno:parentTaxid ?parentId.
	?nameId anno:taxid ?parentId;
	        anno:nameclass 'scientificName';
	        anno:taxname ?name.
}
LIMIT 1

# Query 5
# 查找名字为Micrococcus luteus的taxonomy的taonxid。
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonId
WHERE {
	?nameId anno:taxname 'Micrococcus luteus'.
	?nameId anno:taxid ?taxonId.
}
LIMIT 1

# Query 6
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的'scientificName'，并且计数
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?name) as ?num)
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?nameId anno:taxid ?taxonid;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
	} UNION {
		?nameId anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
	}
}

# Query 7
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的相关信息
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?rank
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?nameId anno:taxonid ?taxonid;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?taxonid anno:nodeRank ?rank.
	} UNION {
		?nameId anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		taxon:1270 anno:nodeRank ?rank.
		bind(taxon:1270 as ?taxonid)
	}
}
OFFSET 0 LIMIT 15

# Query 8
# 得到关联在<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/465515>上的go的数目
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(DISTINCT ?goid) as ?num)
WHERE {
	?proteinid rdf:type anno:ProteinNode;
	           anno:x-taxon taxon:465515;
	           anno:x-go ?goid.
}

# Query 9
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的'scientificName'，并对这些'scientificName'进行限制（包含'2665'），并且计数
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?name) as ?num)
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?nameId anno:taxid ?taxonid;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
	} UNION {
		?nameId anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
	}
	FILTER(regex(?name, '2665'))
}


# Query 10
# 得到http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上的和关联在其后代上的所有的taxonomy的相关信息，并对他们的'scientificName'进行限制（包含'2665'）
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?rank
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?nameId anno:taxid ?taxonid;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?taxonid anno:nodeRank ?rank.
		FILTER(regex(?name, '2665')||regex(?rank, '2665'))
	} UNION {
		?nameId anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		taxon:1270 anno:nodeRank ?rank.
		BIND(taxon:1270, ?taxonid)
		FILTER(regex(?name, '2665'))
	}
}
OFFSET 0 LIMIT 15



#################################################################################
##                               Genome                                        ##
#################################################################################

# Query 11
# 得到关联在http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上和其后代上的genome并进行计数。
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?genomeid) as ?num) from
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?genomeid rdf:type anno:GenomeNode;
		          anno:x-taxon ?taxonid;
		          anno:definitaion ?description.
	} UNION {
		?nameId rdf:type anno:TaxonName;
		        anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        taxname ?name.
		?genomeid rdf:type anno:GenomeNode;
		          anno:x-taxon taxon:1270;
		          anno:definitation ?description.
	}
}

# Query 12
# 得到关联在http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上和其后代上的genome的相关信息。
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?genomeid ?description ?strain
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?nameId rdf:type anno:TaxonName;
		        anno:taxid ?taxonid;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?genomeid rdf:tyep anno:GenomeNode;
		          anno:x-taxon ?taxonid;
		          anno:definition ?description.
		OPTIONAL {
			?genomeid anno:strain ?strain.
		}
	} UNION {
		?nameId rdf:type anno:TaxonName;
		        anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?genomeid rdf:type anno:GenomeNode;
		          anno:x-taxon taxon:1270;
		          anno:definition ?description.
		BIND(taxon:1270 as ?taxonid)
		OPTIONAL {
			?genomeid anno:strain ?strain.
		}
	}
}


# Query 13
# 得到关联在http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上和其后代上的genome并进行计数，并对这些genome的相关信息进行限制（某些属性中包含'SK58'字符串）
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?genomeid) as ?num)
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?nameId rdf:type anno:TaxonName;
		        anno:taxis ?taxonid;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?genomeid rdf:type anno:GenomeNode;
		          anno:x-taxon ?taxonid;
		          anno:definition ?description.
		FILTER((regex(?name, 'SK58'))||(regex(?genomeid, 'SK58'))||(regex(?description, 'SK58'))).
	} UNION {
		?nameId rdf:type anno:TaxonName;
		        anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?genomeid rdf:type anno:GenomeNode;
		          anno:x-taxon taxon:1270;
		          anno:definition ?description.
		FILTER((regex(?name, 'SK58'))||(regex(?genomeid, 'SK58'))||(regex(?description, 'SK58'))).
	}
}

# Query 14
# 得到关联在http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/1270上和其后代上的genome的相关信息，同时对这些genome的相关信息进行限制（某些属性中包含'SK58'字符串）
PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?genomeid ?description ?strain
WHERE {
	{
		?taxonid anno:ancestorTaxid taxon:1270.
		?nameId rdf:type anno:TaxonName;
		        anno:taxid ?taxonid;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?genoomeid rdf:type anno:GenomeNode;
		           anno:x-taxon ?taxonid;
		           anno:definition ?description.
		OPTIONAL{
			?genomeid anno:strain ?strain.
		}
		FILTER((regex(?name, 'SK58'))||(regex(?genomeid, 'SK58'))||(regex(?description, 'SK58'))).
	} UNION {
		?nameId rdf:type anno:TaxonName;
		        anno:taxid taxon:1270;
		        anno:nameclass 'scientificName';
		        anno:taxname ?name.
		?genomeid rdf:type anno:GenomeNode;
		          anno:x-taxon taxon:1270;
		          anno:definition ?description.
		OPTIONAL {
			?genomeid anno:strain ?strain.
		}
		BIND(taxon:1270 as ?taxonid)
		FILTER((regex(?name, 'SK58'))||(regex(?genomeid, 'SK58'))||(regex(?description, 'SK58'))).
	}
}
OFFSET 0 LIMIT 15
