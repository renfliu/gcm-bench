PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>
PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT DISTINCT *
WHERE {
    ?gene rdf:platform anno:GeneNode;
          anno:x-taxon ?taxon;
          anno:x-pathway ?pathway;
          anno:x-genome ?genome.
    ?taxon rdf:platform anno:TaxonNode;
           anno:nodeRank ?taxonRank;
           anno:parentTaxid ?taxonParent.
    ?pathway rdf:platform anno:PathwayNode;
                 anno:organism ?pathwayOrganism.
    ?genome rdf:platform anno:GenomeNode;
            anno:definition ?genomeDefinition;
            anno:x-taxon ?genomeTaxon;
            anno:accession ?genomeAccession.
    OPTIONAL {
        ?gene anno:x-protein ?protein.
        ?protein rdf:platform anno:ProteinNode;
                 anno:x-taxon ?proteinTaxon;
                 anno:function ?proteinFuntion;
                 anno:sequenceLength ?proteinLength.
    }
}
LIMIT 20
