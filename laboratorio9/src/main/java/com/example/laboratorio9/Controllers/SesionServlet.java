package com.example.laboratorio9.Controllers;

import com.example.laboratorio9.Beans.Rol;
import com.example.laboratorio9.Beans.Usuario;
import com.example.laboratorio9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "SesionServlet", value = {"/SesionServlet",""})
public class SesionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Usuario userLogged = (Usuario) httpSession.getAttribute("usuarioLogueado");

        if(userLogged != null && userLogged.getIdUsuario() > 0){

            if(request.getParameter("a") != null){//logout
                httpSession.invalidate();
            }
            response.sendRedirect(request.getContextPath());
        }else{
            request.getRequestDispatcher("loginForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        System.out.println("email: " + correo + " | password: " + password);
        UsuarioDao usuarioDao = new UsuarioDao();

        if(usuarioDao.validarUsuarioPasswordHashed(correo,password)){
            Usuario usuario = usuarioDao.obtenerUsuario(correo);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("usuarioLogueado",usuario);

            int idRol = usuario.getRol().getIdRol();

            switch (idRol) {
                case 1:
                    System.out.println("usuario o password correctos");
                    response.sendRedirect(request.getContextPath() + "/AdmiServlet");
                    break;
                case 2:
                    System.out.println("usuario o password correctos");
                    response.sendRedirect(request.getContextPath() + "/RectorServlet");
                    break;
                case 3:
                    System.out.println("usuario o password correctos");
                    response.sendRedirect(request.getContextPath() + "/DecanoServlet");
                    break;
                case 4:
                    System.out.println("usuario o password correctos");
                    response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                    break;
            }

        }else{
            System.out.println("usuario o password incorrectos");
            request.setAttribute("err","Correo o password incorrectos");
            request.getRequestDispatcher("loginForm.jsp").forward(request,response);
        }

    }
}

