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
