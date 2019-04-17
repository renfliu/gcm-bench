SELECT DISTINCT (max(?geneId) as ?max) (min(?geneId) as ?min)
WHERE {
    ?geneId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'protein-coding'.
}
