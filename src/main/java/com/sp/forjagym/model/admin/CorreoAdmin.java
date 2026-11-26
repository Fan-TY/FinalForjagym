package com.sp.forjagym.model.admin;

public final class CorreoAdmin {

    public static final String DOMINIO = "forjagym.com";
    public static final String REGEX = "^[A-Za-z0-9._%+-]+@forjagym\\.[A-Za-z]{2,}$";

    private CorreoAdmin() {
    }

    public static boolean esValido(String correo) {
        return correo != null && correo.matches(REGEX);
    }
}