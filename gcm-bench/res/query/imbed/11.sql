SELECT ?name ?rank
WHERE {
    {
        ?taxonid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    } UNION {
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
        BIND(<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5> as ?taxonid)
        FILTER(regex(?name, 'Clyomys')||regex(?rank, 'order'))
    }
}
OFFSET 0 LIMIT 20
