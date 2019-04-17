PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    {
        ?taxonId anno:ancestorTaxid taxon:5;
                 anno:nodeRank ?rank.
        ?nameId anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    } UNION {
        ?nameId anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
    }
}
LIMIT 20
