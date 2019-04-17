SELECT DISTINCT *
WHERE {
    ?gene <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxon;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-pathway> ?pathway;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome.
    ?taxon <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> ?taxonRank;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?taxonParent.
    ?pathway <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/PathwayNode>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/organism> ?pathwayOrganism.
    ?genome <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?genomeDefinition;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?genomeTaxon;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/accession> ?genomeAccession.
    OPTIONAL {
        ?gene <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-protein> ?protein.
        ?protein <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ProteinNode>;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?proteinTaxon;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/function> ?proteinFuntion;
                 <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/sequenceLength> ?proteinLength.
    }
}
LIMIT 20
