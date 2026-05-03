package org.example.appfichaje.dao;

import javafx.scene.control.Alert;
import org.example.appfichaje.database.DBConnection;
import org.example.appfichaje.database.DBSchem;
import org.example.appfichaje.model.Rol;
import org.example.appfichaje.model.TipoJornada;
import org.example.appfichaje.model.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void insertUser(Usuario usuario) throws SQLException {
            String query = String.format(
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?,?,?)",
                    DBSchem.TAB_NAME,
                    DBSchem.COL_NOMBRE,
                    DBSchem.COL_APELLIDO,
                    DBSchem.COL_DNI,
                    DBSchem.COL_TLF,
                    DBSchem.COL_ROL,
                    DBSchem.COL_FECHAALTA,
                    DBSchem.COL_FECHABAJA,
                    DBSchem.COL_JORNADA,
                    DBSchem.COL_EMAIL,
                    DBSchem.COL_PASS
            );

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getApellidos());
                ps.setString(3, usuario.getDNI());
                ps.setString(4, usuario.getTelefono());
                ps.setString(5, String.valueOf(usuario.getRol()));
                ps.setDate(6, Date.valueOf(usuario.getFechaAlta()));
                ps.setDate(7, usuario.getFechaBaja() != null ? Date.valueOf(usuario.getFechaBaja()) : null);
                ps.setString(8, String.valueOf(usuario.getJornada()));
                ps.setString(9, usuario.getCorreo());
                ps.setString(10, usuario.getPass());

                ps.executeUpdate();
            }
        }


    public void updateUser(Usuario usuario) {
        String query = String.format(
                "UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?",
                DBSchem.TAB_NAME,
                DBSchem.COL_NOMBRE,
                DBSchem.COL_APELLIDO,
                DBSchem.COL_TLF,
                DBSchem.COL_ROL,
                DBSchem.COL_FECHAALTA,
                DBSchem.COL_FECHABAJA,
                DBSchem.COL_JORNADA,
                DBSchem.COL_EMAIL,
                DBSchem.COL_PASS,
                DBSchem.COL_DNI
        );

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, String.valueOf(usuario.getRol()));
            ps.setDate(5, usuario.getFechaAlta() != null ? Date.valueOf(usuario.getFechaAlta()) : null);
            ps.setDate(6, usuario.getFechaBaja() != null ? Date.valueOf(usuario.getFechaBaja()) : null);
            ps.setString(7, String.valueOf(usuario.getJornada()));
            ps.setString(8, usuario.getCorreo());
            ps.setString(9, usuario.getPass());
            ps.setString(10, usuario.getDNI());

            int filas = ps.executeUpdate();
            System.out.println("Filas actualizadas: " + filas);

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Usuario> getAllUsers(){

        List<Usuario> lista = new ArrayList<>();
        connection = DBConnection.getConnection();
        String query = "SELECT * FROM "+DBSchem.TAB_NAME;
        try {
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(DBSchem.COL_ID);
                String nombre = resultSet.getString(DBSchem.COL_NOMBRE);
                String apellidos = resultSet.getString(DBSchem.COL_APELLIDO);
                String correo = resultSet.getString(DBSchem.COL_EMAIL);
                String DNI = resultSet.getString(DBSchem.COL_DNI);
                String telefono = resultSet.getString(DBSchem.COL_TLF);
                LocalDate fechaAlta = resultSet.getDate(DBSchem.COL_FECHAALTA).toLocalDate();
                String pass = resultSet.getString(DBSchem.COL_PASS);
                TipoJornada jornada = TipoJornada.valueOf(resultSet.getString(DBSchem.COL_JORNADA));
                Rol rol = Rol.valueOf(resultSet.getString(DBSchem.COL_ROL));


                lista.add(new Usuario(nombre,apellidos, correo,DNI, pass, telefono, fechaAlta , jornada, rol));
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta");
        }
        return lista;
    }
}
