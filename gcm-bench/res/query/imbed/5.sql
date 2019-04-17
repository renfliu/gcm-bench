SELECT DISTINCT *
WHERE {
    ?geneID <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'protein-coding';
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-protein> ?protein;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/dbXrefs> ?refs.
}
ORDER BY asc(?geneID)
LIMIT 20
