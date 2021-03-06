package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.random.NameGenerator;
import me.renf.gcm.generator.random.RandomGenerator;
import me.renf.gcm.generator.random.TaxonIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class TaxonName implements RandomGenerator{
    final Logger logger = LoggerFactory.getLogger(RandomGenerator.class);
    private RandomGenerator nameGenerator;
    private RandomGenerator idGenerator;
    private Random rand;

    public TaxonName() {
        nameGenerator = new NameGenerator();
        idGenerator = new TaxonIDGenerator();
        rand = new Random();
    }

    public String next() {
        StringBuilder sb = new StringBuilder();
        String id = getID();
        sb.append(getTaxIDAxiom(id));
        sb.append(getNameClassAxiom(id));
        sb.append(getTaxnameAxiom(id));
        sb.append(getTypeAxiom(id));

        return sb.toString();
    }

    private String getID() {
        int r = rand.nextInt(100);
        String[] nameClasses = {"scientificName", "genbankCommonName", "equivalentName", "synonymName", "misnomerName", "includesName",
                "authorityName", "typeMaterialName", "blastName", "commonName", "misspellingName", "genbankSynonym" };
        return String.format("%s:%s:%s", idGenerator.next(), nameClasses[r%nameClasses.length], getMD5(nameGenerator.next()));
    }

    private String getTaxIDAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/name/%s> <http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/taxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> .", id, getIDNumber(id));
        return axiom+"\n";
    }

    private String getNameClassAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/name/%s> <http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/nameclass> \"%s\" .", id, getNameClass(id));
        return axiom+"\n";
    }

    private String getTaxnameAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/name/%s> <http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/taxname> \"%s\" .", id, nameGenerator.next());
        return axiom+"\n";
    }

    private String getTypeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/name/%s> <http://www.w3.org/" +
                "1999/02/22-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonName> .", id);
        return axiom+"\n";
    }

    private String getIDNumber(String id) {
        String[] ids = id.split(":");
        if (ids.length > 2) {
            return ids[0];
        }
        return id;
    }

    private String getMD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            logger.error("生成Taxon Name的md5出错" + e.getMessage());
        }
        return "";
    }

    private String getNameClass(String id) {
        String[] ids = id.split(":");
        return ids[1];
    }
}
