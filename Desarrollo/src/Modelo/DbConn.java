/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

import javax.sql.DataSource;
import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Angel Alberici
 */
public class DbConn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

DriverManagerDataSource dataSource = new DriverManagerDataSource();
dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
dataSource.setUrl("jdbc:mysql://localhost/desarrollo");
dataSource.setUsername("root");
dataSource.setPassword("root");

// La clase Spring con la Connection
JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int personas = jdbcTemplate.update("update dummy set col=3; ");

    }
}
