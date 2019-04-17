SELECT ?taxonid ?rank ?nameId ?name
WHERE {
    ?taxonid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/33>;
             <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?rank.
    ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
}
LIMIT 20
