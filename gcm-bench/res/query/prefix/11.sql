PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?rank
WHERE {
    {
        ?taxonid anno:ancestorTaxid taxon:5;
                 anno:nodeRank ?rank.
        ?nameId anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    } UNION {
        ?nameId anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        taxon:5 anno:nodeRank ?rank.
        BIND(taxon:5 as ?taxonid)
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    }
}
OFFSET 0 LIMIT 20
