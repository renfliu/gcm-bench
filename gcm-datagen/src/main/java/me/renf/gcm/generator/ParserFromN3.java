package me.renf.gcm.generator;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParserFromN3 {
    public static void main(String[] args) {
        Path outPath = Paths.get("/home/renf/", "obo.xml");
        ParserFromN3 parser = new ParserFromN3("gcm.n3");
        parser.saveToXML(outPath.toString());
    }

    private String filename;

    public ParserFromN3(String filename) {
        this.filename = filename;
    }

    public void saveToXML(String outFile) {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = load(manager);
        try {
            manager.saveOntology(ontology, new RDFXMLDocumentFormat(), IRI.create(new File(outFile)));
        }catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    private OWLOntology load(OWLOntologyManager manager) {
        try {
            String filePath  = ParserFromN3.class.getClassLoader().getResource(filename).getPath();
            return manager.loadOntologyFromOntologyDocument(new File("/home/renf/data/gstore/obo.n3"));
        }catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
