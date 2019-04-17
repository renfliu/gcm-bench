SELECT DISTINCT ?t ?pid ?ppid ?pppid
WHERE {
    ?t <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
       <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?pid.
    ?nameId <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?t;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?p.
    ?pid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
         <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?ppid.
    ?ppid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?pppid.
}
ORDER BY ?t
LIMIT 20
