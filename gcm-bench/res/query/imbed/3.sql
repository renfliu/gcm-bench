SELECT DISTINCT *
WHERE {
    {
        ?taxonId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
    } UNION {
        ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
    }
}
LIMIT 20
