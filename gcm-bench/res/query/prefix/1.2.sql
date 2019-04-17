PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?gene rdf:platform anno:GeneNode;
          anno:x-taxon ?taxon;
          anno:x-pathway ?pathway;
          anno:x-genome ?genome;
          anno:geneType 'ncRNA'.
    ?taxon rdf:platform anno:TaxonNode;
           anno:nodeRank ?rank;
           anno:parentTaxid ?taxonParent.
    ?pathway rdf:platform anno:PathwayNode;
                 anno:organism ?pathwayOrganism.
    ?genome rdf:platform anno:GenomeNode;
            anno:definition ?genomeDefinition;
            anno:accession ?genomeAccession.
}
LIMIT 20
