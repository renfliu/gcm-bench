PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT DISTINCT *
WHERE {
    ?protein rdf:platform anno:ProteinNode;
             anno:sequenceLength ?length.
    FILTER (xsd:integer(?length) < 10).
}
ORDER BY asc(?protein)
LIMIT 20
