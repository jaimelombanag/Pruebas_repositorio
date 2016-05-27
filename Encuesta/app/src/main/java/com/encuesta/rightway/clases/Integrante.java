package com.encuesta.rightway.clases;

/**
 * Created by jalombanag on 23/04/2016.
 */
public class Integrante {

    private String sexo;
    private String edad;
    private String estudios;
    private String ocupacionPrincipal;
    private String sectorEconomico;
    private String direccion;
    private String direccionOrigen;
    private String horaInicio;
    private String direccionDestino;
    private String horaLLegada;
    private String proposito;
    private String modo;
    private String dias;
    private String tipoTransporte;
    private String datosTransporte;
    private String necesitaayuda;
    private String costoViaje;


    public Integrante(String sexo, String edad, String estudios, String ocupacionPrincipal, String sectorEconomico, String direccion,
                      String direccionOrigen, String horaInicio, String direccionDestino, String horaLLegada, String proposito,
                      String modo, String dias, String tipoTransporte, String datosTransporte, String necesitaayuda, String costoViaje) {
        this.sexo = sexo;
        this.edad = edad;
        this.estudios = estudios;
        this.ocupacionPrincipal = ocupacionPrincipal;
        this.sectorEconomico = sectorEconomico;
        this.direccion = direccion;
        this.direccionOrigen = direccionOrigen;
        this.horaInicio = horaInicio;
        this.direccionDestino = direccionDestino;
        this.horaLLegada = horaLLegada;
        this.proposito = proposito;
        this.modo = modo;
        this.dias = dias;
        this.tipoTransporte = tipoTransporte;
        this.datosTransporte = datosTransporte;
        this.necesitaayuda = necesitaayuda;
        this.costoViaje = costoViaje;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getOcupacionPrincipal() {
        return ocupacionPrincipal;
    }

    public void setOcupacionPrincipal(String ocupacionPrincipal) {
        this.ocupacionPrincipal = ocupacionPrincipal;
    }

    public String getSectorEconomico() {
        return sectorEconomico;
    }

    public void setSectorEconomico(String sectorEconomico) {
        this.sectorEconomico = sectorEconomico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public String getHoraLLegada() {
        return horaLLegada;
    }

    public void setHoraLLegada(String horaLLegada) {
        this.horaLLegada = horaLLegada;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public String getDatosTransporte() {
        return datosTransporte;
    }

    public void setDatosTransporte(String datosTransporte) {
        this.datosTransporte = datosTransporte;
    }

    public String getNecesitaayuda() {
        return necesitaayuda;
    }

    public void setNecesitaayuda(String necesitaayuda) {
        this.necesitaayuda = necesitaayuda;
    }

    public String getCostoViaje() {
        return costoViaje;
    }

    public void setCostoViaje(String costoViaje) {
        this.costoViaje = costoViaje;
    }
}
