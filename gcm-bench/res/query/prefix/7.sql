PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (count(?geneId) as ?count)
WHERE {
    ?geneId rdf:platform anno:GeneNode;
            anno:geneType 'protein-coding'.
}
