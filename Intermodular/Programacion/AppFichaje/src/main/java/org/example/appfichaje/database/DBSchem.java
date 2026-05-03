package org.example.appfichaje.database;

import org.example.appfichaje.model.Rol;

import java.util.Date;

public interface DBSchem {
    String TAB_NAME = "usuarios";
    String COL_ID = "id_usuario";
    String COL_NOMBRE = "nombre";
    String COL_APELLIDO = "apellidos";
    String COL_DNI = "dni";
    String COL_TLF = "telefono";
    String COL_EMAIL = "email";
    String COL_ROL ="rol";
    String COL_FECHAALTA = "fechaAlta";
    String COL_FECHABAJA = "fechaBaja";
    String COL_JORNADA = "tipoJornada";
    String COL_PASS = "passwordHash";
}
