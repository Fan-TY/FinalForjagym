package com.sp.forjagym.dto.dashboard;

public class MetricaDashboardDto {
    private final String clave;
    private final String titulo;
    private final String valorTexto;
    private final String anteriorTexto;
    private final Double variacion;
    private final String icono;
    private final String color;

    public MetricaDashboardDto(String clave, String titulo, String valorTexto, String anteriorTexto,
                               Double variacion, String icono, String color) {
        this.clave = clave;
        this.titulo = titulo;
        this.valorTexto = valorTexto;
        this.anteriorTexto = anteriorTexto;
        this.variacion = variacion;
        this.icono = icono;
        this.color = color;
    }

    public String getClave() { return clave; }
    public String getTitulo() { return titulo; }
    public String getValorTexto() { return valorTexto; }
    public String getAnteriorTexto() { return anteriorTexto; }
    public Double getVariacion() { return variacion; }
    public String getIcono() { return icono; }
    public String getColor() { return color; }
}