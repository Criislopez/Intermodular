package org.example.appfichaje.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.appfichaje.model.Rol;
import org.example.appfichaje.model.TipoJornada;
import org.example.appfichaje.model.Usuario;

import java.time.LocalDate;
import java.util.Optional;

public class DataSet {


    private static Usuario usuarioActual;

    @Setter
    @Getter
    private static ObservableList<Usuario> listaUsuarios;

    public static boolean addUsuario(Usuario usuario){

        for (Usuario u : listaUsuarios) {
            if (u.getDNI().equalsIgnoreCase(usuario.getDNI()) ||
                    u.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                return false;
            }
        }

        listaUsuarios.add(usuario);
        return true;
    }

    public static Usuario getLogin(String correo, String pass){
        Optional<Usuario> usuarioOptional = listaUsuarios.stream().filter(item -> item.getCorreo().equals(correo) && item.getPass().equals(pass))
                .findFirst();
        return usuarioOptional.orElse(null);
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }
}