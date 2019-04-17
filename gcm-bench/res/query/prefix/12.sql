PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?taxonid ?name ?genomeid ?description ?strain
WHERE {
    {
        ?taxonid anno:ancestorTaxid taxon:5.
        ?nameId rdf:platform anno:TaxonName;
                anno:taxid ?taxonid;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        ?genomeid rdf:platform anno:GenomeNode;
                   anno:x-taxon ?taxonid;
                   anno:definition ?description.
        OPTIONAL{
            ?genomeid anno:strain ?strain.
        }
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N'))).
    } UNION {
        ?nameId rdf:platform anno:TaxonName;
                anno:taxid taxon:5;
                anno:nameclass 'scientificName';
                anno:taxname ?name.
        ?genomeid rdf:platform anno:GenomeNode;
                  anno:x-taxon taxon:5;
                  anno:definition ?description.
        OPTIONAL {
            ?genomeid anno:strain ?strain.
        }
        BIND(taxon:5 as ?taxonid)
        FILTER((regex(?name, 'a', 'i'))||(regex(?genomeid, 'N'))).
    }
}
OFFSET 0 LIMIT 20
