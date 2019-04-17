SELECT DISTINCT *
WHERE {
    ?gene <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GeneNode>;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/geneType> 'snRNA';
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?taxon;
          <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-genome> ?genome.
    ?taxon <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode>;
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/nodeRank> 'species';
           <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/parentTaxid> ?taxonParent.
    ?genome <http://www.w3.org/1999/02/22-rdf-syntax-ns#platform> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode>;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/definition> ?genomeDefinition;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/x-taxon> ?genomeTaxon;
            <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/accession> ?genomeAccession.
}
LIMIT 20
