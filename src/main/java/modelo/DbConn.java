///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package modelo;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
///**
// *
// * @author Angel Alberici
// */
//public class DbConn {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost/desarrollo");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//
//// La clase Spring con la Connection
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//
//
//        int personas = jdbcTemplate.update("update dummy set col=30; ");
//
//        
//     //Hacindo select e imprimiendo
//        String sql = "SELECT id,titulo,texto FROM NOTA";
//
//        List<Nota> customers = new ArrayList<Nota>();
//
//        List<Map> rows = jdbcTemplate.queryForList(sql);
//        for (Map row : rows) {
//            Nota customer = new Nota();
//            customer.setId((Integer) (row.get("id")));
//            customer.setTitulo((String) row.get("titulo"));
//            customer.setTexto((String) row.get("texto"));
//            customers.add(customer);
//        }
//
//        Iterator iter = rows.iterator();
//        while (iter.hasNext()) {
//            Nota obj = (Nota) iter.next();
//            obj.getId();
//        }
//
//
//    }
//}
