package me.renf.gcm.bench;

import me.renf.gcm.bench.conf.BenchConf;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import java.sql.*;
import java.sql.ResultSet;
import java.util.Iterator;

public class Test {
    public static void main(String[] args) throws Exception{
        //testVirtuoso();
        testJena();
    }

    public static void modify() throws Exception{
        BenchConf conf = new BenchConf();
        conf.loadFromFile();
        System.out.println(conf.getDataset());
        conf.setDataset("test");
        System.out.println(conf.getDataset());
    }

    public static void testJena() {
        Query query  = QueryFactory.create("select * where { ?s ?p ?o. } limit 10");
        Dataset dataset = DatasetFactory.createTxnMem();

        RDFConnection conn = RDFConnectionFactory.connect(dataset);
        System.out.println("loading data set");
        conn.load("out.n3");
        System.out.println("querying data set");
        StringBuilder sb = new StringBuilder();
        try (QueryExecution queryExecution = conn.query(query)) {
            org.apache.jena.query.ResultSet rs = queryExecution.execSelect();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                Iterator<String> varNames = qs.varNames();
                while (varNames.hasNext()) {
                    String name = varNames.next();
                    RDFNode node = qs.get(name);
                    sb.append(node);
                    sb.append(" ");
                }
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
        conn.close();
    }

    public static void testVirtuoso() {
        String driver = "virtuoso.jdbc4.Driver";
        String url = "jdbc:virtuoso://localhost:1111/UID=dba/PWD=dba";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            String sql = "sparql select * from <http://gcm.wdcm.org> where { ?s ?p ?o. } limit 10";
            ResultSet rs = stmt.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                sb.append(rs.getMetaData().getColumnName(i));
                sb.append("\t");
            }
            sb.append("\n");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(rs.getString(i));
                    sb.append("\t");
                }
                sb.append("\n");
            }
            System.out.println(sb.toString());
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
