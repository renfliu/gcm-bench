PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/gene/>
PREFIX enzyme:<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?geneId rdf:platform anno:GeneNode;
            anno:geneProduct ?product;
            anno:x-taxon ?taxon.
    FILTER regex(str(?product), 'Methanos', 'i')
    ?taxon rdf:platform anno:TaxonNode;
                    anno:nodeRank ?rank.
    FILTER regex(str(?rank), 'order', 'i')
}
LIMIT 20
