PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT ?t ?pid ?ppid ?pppid
WHERE {
    ?t rdf:platform anno:TaxonNode;
       anno:parentTaxid ?pid.
    ?nameId anno:taxid ?t;
            anno:nameclass 'scientificName';
            anno:taxname ?p.
    ?pid rdf:platform anno:TaxonNode;
         anno:parentTaxid ?ppid.
    ?ppid rdf:platform anno:TaxonNode;
          anno:parentTaxid ?pppid.
}
ORDER BY ?t
LIMIT 20
