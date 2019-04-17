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
