package model;

import model.enums.Rol;
import model.enums.EstadoUsuario;

import java.time.LocalDateTime;

public class Usuario {

    private int id;
    private Rol rol;
    private String nombre;
    private String apellido;
    private String dni;
    private String celular;
    private String mail;
    private String nombreUsuario;
    private String contrasena;
    private LocalDateTime fechaRegistro;
    private EstadoUsuario estado;

    //Constructores☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public Usuario() {
    }

    public Usuario(Rol rol, String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.celular = celular;
        this.mail = mail;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    public Usuario(int id, Rol rol, String nombre, String apellido, String dni, String celular, String mail, String nombreUsuario, String contrasena, LocalDateTime fechaRegistro, EstadoUsuario estado) {
        this.id = id;
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.celular = celular;
        this.mail = mail;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    //Getters y setters☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    public int getId() {
        return id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    //ToString☆゜・。。・゜゜・。。・゜★゜・。。・゜゜・。。・゜☆゜・。。・゜゜・。。・゜★
    @Override
    public String toString() {
        return "Usuario:" +
                "\nRol: " + rol +
                "\nNombre: " + nombre +
                "\nApellido: " + apellido +
                "\nDni: " + dni +
                "\nCelular: " + celular +
                "\nMail: " + mail+
                "\nNombre de usuario: " + nombreUsuario +
                "\nContraseña: " + contrasena +
                "\nFecha de registro: " + fechaRegistro +
                "\nEstado: " + estado;
    }

}