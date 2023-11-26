package com.example.laboratorio9.Daos;

import com.example.laboratorio9.Beans.Rol;
import com.example.laboratorio9.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao extends DaoBase{
    public boolean validarUsuarioPasswordHashed(String correo, String password){
        String sql = "SELECT * FROM usuario WHERE correo = ? and password = sha2(?,256)";
        boolean exito = false;

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    exito = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exito;
    }

    public Usuario obtenerUsuario(String email){
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt(1));
                    usuario.setNombre(rs.getString(2));
                    usuario.setCorreo(rs.getString(3));

                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt(5));
                    usuario.setRol(rol);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usuario;
    }

}
