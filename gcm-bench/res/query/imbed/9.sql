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
