PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT *
WHERE {
    {
        ?geneId rdf:platform anno:GeneNode;
                anno:x-genome ?genome;
                anno:geneType ?geneType;
                anno:x-enzyme ?enzymeId.
        OPTIONAL {
            ?geneId anno:chromosome ?chromosome.
        }
        ?enzymeId rdf:platform anno:EnzymeNode;
                  anno:class ?class.
        FILTER(regex(?class, 'n', 'i')).
    } UNION {
        ?geneId rdf:platform anno:GeneNode;
                anno:x-genome ?genome;
                anno:geneType ?geneType;
                anno:x-protein ?proteinId.
        OPTIONAL {
            ?geneId anno:chromosome ?chromosome.
        }
        ?proteinId rdf:platform anno:ProteinNode;
                   anno:sequenceLength ?length.
        FILTER ((xsd:integer(?length) < 500)).
    }
    
}
OFFSET 0 LIMIT 20
