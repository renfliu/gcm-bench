PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?gene rdf:platform anno:GeneNode;
          anno:geneType snRNA;
          anno:x-taxon ?taxon;
          anno:x-genome ?genome.
    ?taxon rdf:platform anno:TaxonNode;
           anno:nodeRank species;
           anno:parentTaxid ?taxonParent.
    ?genome rdf:platform anno:GenomeNode;
            anno:definition ?genomeDefinition;
            anno:x-taxon ?genomeTaxon;
            anno:accession ?genomeAccession.
}
LIMIT 20