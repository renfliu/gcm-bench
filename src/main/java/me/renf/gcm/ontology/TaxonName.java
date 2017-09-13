package me.renf.gcm.ontology;

import me.renf.gcm.random.NameGenerator;
import me.renf.gcm.random.RandomGenerator;
import me.renf.gcm.random.TaxonIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;

public class TaxonName implements RandomGenerator{
    final Logger logger = LoggerFactory.getLogger(RandomGenerator.class);
    private RandomGenerator nameGenerator;
    private RandomGenerator idGenerator;

    public TaxonName() {
        nameGenerator = new NameGenerator();
        idGenerator = new TaxonIDGenerator();
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
        return String.format("%s:scientificName:%s", idGenerator.next(), getMD5(nameGenerator.next()));
    }

    private String getTaxIDAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/name/%s> <http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/taxid> http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s .", id, idGenerator.next());
        return axiom+"\n";
    }

    private String getNameClassAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/name/%s> <http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/nameclass> \"%s\" .", id, nameGenerator.next());
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
}
