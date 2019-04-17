SELECT DISTINCT *
WHERE {
    ?protein <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ProteinNode>;
             <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/sequenceLength> ?length.
    FILTER (?length < 10)
}
ORDER BY asc(?protein)
LIMIT 20
