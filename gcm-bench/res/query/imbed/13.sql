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
