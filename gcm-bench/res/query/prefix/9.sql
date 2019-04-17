PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX gene:<http://gcm.wdcm.org/data/gcmAnnotation1/gene/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT (avg(?length) as ?avgLength) (sum(?length) as ?s)
WHERE {
    ?geneId rdf:platform anno:GeneNode;
            anno:x-genome ?genome;
            anno:geneType ?geneType;
            anno:x-protein ?proteinId.
    OPTIONAL {
        ?geneId anno:chromosome ?chromosome.
    }
    ?proteinId rdf:platform anno:ProteinNode;
               anno:sequenceLength ?length.
}
