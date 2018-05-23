/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.vo;


public class Automovil {
    private int id_automovil;
    private String Placa;
    private String Marca;
    private int Modelo;
    private int preciodia;
    private Garaje garaje;

    public int getId_automovil() {
        return id_automovil;
    }

    public void setId_automovil(int id_automovil) {
        this.id_automovil = id_automovil;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public int getModelo() {
        return Modelo;
    }

    public void setModelo(int Modelo) {
        this.Modelo = Modelo;
    }

    public int getPreciodia() {
        return preciodia;
    }

    public void setPreciodia(int preciodia) {
        this.preciodia = preciodia;
    }

    public Garaje getGaraje() {
        return garaje;
    }

    public void setGaraje(Garaje garaje) {
        this.garaje = garaje;
    }

 
    
    
}
