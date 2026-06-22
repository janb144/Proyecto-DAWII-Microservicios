package cibertec.pe.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_envio_sunat")
public class EnvioSunat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnvio;

    private Integer idFactura;
    private String tipoDocumento;
    private String serie;
    private String numero;
    private String estado;
    private String codigoRespuesta;

    // CORRECCIÓN CLAVE: Permitimos que almacene textos gigantescos sin reventar la BD
    @Column(name = "descripcion_respuesta", columnDefinition = "TEXT")
    private String descripcionRespuesta;

    private LocalDateTime fechaEnvio;

    public EnvioSunat() {
    }

    public EnvioSunat(Integer idEnvio, Integer idFactura, String tipoDocumento,
            String serie, String numero, String estado,
            String codigoRespuesta, String descripcionRespuesta,
            LocalDateTime fechaEnvio) {

        this.idEnvio = idEnvio;
        this.idFactura = idFactura;
        this.tipoDocumento = tipoDocumento;
        this.serie = serie;
        this.numero = numero;
        this.estado = estado;
        this.codigoRespuesta = codigoRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}