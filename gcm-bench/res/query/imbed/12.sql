SELECT ?taxonid ?name ?genomeid ?description ?strain
WHERE {
    {
        ?taxonid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>.
        ?nameId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonName>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> ?taxonid;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        ?genomeid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
                   <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxonid;
                   <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?description.
        OPTIONAL{
            ?genomeid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/strain> ?strain.
        }
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N')))
    } UNION {
        ?nameId <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonName>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nameclass> 'scientificName';
                <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/taxname> ?name.
        ?genomeid <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
                  <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5>;
                  <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?description.
        OPTIONAL {
            ?genomeid <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/strain> ?strain.
        }
        BIND(<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/5> as ?taxonid)
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N')))
    }
}
OFFSET 0 LIMIT 20
