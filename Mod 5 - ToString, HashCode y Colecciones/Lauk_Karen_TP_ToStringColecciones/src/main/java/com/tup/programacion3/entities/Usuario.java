package com.tup.programacion3.entities;
import com.tup.programacion3.enums.Rol;
import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private Long id;
    private boolean eliminado;
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private Set<Pedido> pedidos;

    public Usuario() {
        this.pedidos = new HashSet<>();
    }

    public Usuario(Long id, boolean eliminado, String nombre,
                   String apellido, String mail, String celular,
                   String contrasenia, Rol rol) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = new HashSet<>();
    }

    public void addPedido(Pedido pedido) {
        if (pedido != null) {
            this.pedidos.add(pedido);
        }
    }
    
    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Set<Pedido> getPedidos() { return pedidos; }

    // TO STRING
    public String toString() {
        return "Usuario{id=" + id +
                ", nombre='" + nombre +
                "', apellido='" + apellido +
                "', rol=" + rol +
                ", pedidos=" + pedidos.size() + "}";
    }
}