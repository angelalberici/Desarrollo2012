/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import com.mkyong.common.controller.UploadGoogleDrive;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import modelo.DbCon;
import modelo.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Carlos
 */
public class AutenticacionManager {

    DriverManagerDataSource dataSource;
    JdbcTemplate jdbcTemplate;

    /**
     * procedimiento para insertar un usuario
     *
     * @param correo el correo del usuario
     * @throws IOException
     */
    public void insertarUsuario(String correo) throws IOException {
        String verificar = "";
        String folderId = "";
        jdbcTemplate = DbCon.getInstance().getJdbcTemplate();
        dataSource = DbCon.getInstance().getDriverManagerDataSource();
        List<Map> rows = jdbcTemplate.queryForList("select correo,folder_id from usuario where correo='" + correo + "'");
        for (Map row : rows) {
            verificar = (String) row.get("correo");
            folderId = ((String) row.get("folder_id"));
        }
        if (verificar.equals("")) {
            UploadGoogleDrive.insertFolder(Usuario.getInstance().getToken());
            int personas = jdbcTemplate.update("insert into usuario values ('" + correo + "','" + Usuario.getInstance().getFolderID() + "')");
        } else {
            Usuario.getInstance().setFolderID(folderId);
        }
    }
}
