PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT (max(?geneId) as ?max) (min(?geneId) as ?min)
WHERE {
    ?geneId rdf:platform anno:GeneNode;
            anno:geneType 'protein-coding'.
}
