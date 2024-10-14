package ar.edu.utn.dds.k3003.model.Contribuciones.DTO;

public class FormulaDTO {
  private Double pesosDonados;
  private Double viandasDistribuidas;
  private Double viandasDonadas;
  private Double tarjetasRepartidas;
  private Double heladerasActivas;
  private Double heladerasReparadas;

  public Double getPesosDonados() {
    return pesosDonados;
  }

  public Double getViandasDistribuidas() {
    return viandasDistribuidas;
  }

  public Double getViandasDonadas() {
    return viandasDonadas;
  }

  public Double getTarjetasRepartidas() {
    return tarjetasRepartidas;
  }

  public Double getHeladerasActivas() {
    return heladerasActivas;
  }

  public Double getHeladerasReparadas() { return heladerasReparadas; }

  public void setPesosDonados(Double pesosDonados) {
    this.pesosDonados = pesosDonados;
  }

  public void setViandasDistribuidas(Double viandasDistribuidas) {
    this.viandasDistribuidas = viandasDistribuidas;
  }

  public void setViandasDonadas(Double viandasDonadas) {
    this.viandasDonadas = viandasDonadas;
  }

  public void setTarjetasRepartidas(Double tarjetasRepartidas) {
    this.tarjetasRepartidas = tarjetasRepartidas;
  }

  public void setHeladerasActivas(Double heladerasActivas) {
    this.heladerasActivas = heladerasActivas;
  }

  public void setHeladerasReparadas(Double reparacionesDeHeladera) {
    this.heladerasReparadas = reparacionesDeHeladera;
  }
}
