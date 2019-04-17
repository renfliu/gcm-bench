PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/gene/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?geneID rdf:platform anno:GeneNode;
            anno:geneType 'protein-coding';
            anno:x-protein ?protein;
            anno:dbXrefs ?refs.
}
ORDER BY asc(?geneID)
LIMIT 20
