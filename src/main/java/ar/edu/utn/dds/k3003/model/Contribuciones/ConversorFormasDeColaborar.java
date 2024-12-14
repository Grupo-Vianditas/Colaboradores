package ar.edu.utn.dds.k3003.model.Contribuciones;

import ar.edu.utn.dds.k3003.model.Contribuciones.FormaDeColaborarEnum;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ConversorFormasDeColaborar implements AttributeConverter<List<FormaDeColaborarEnum>, String> {
  @Override
  public String convertToDatabaseColumn(List<FormaDeColaborarEnum> formasDeColaborar) {
    //Recibe una lista de formas de colaborar y devuelve un string
    return formasDeColaborar.stream()
        .map(FormaDeColaborarEnum::name)
        .collect(Collectors.joining(","));
  }

  @Override
  public List<FormaDeColaborarEnum> convertToEntityAttribute(String formasDeColaborar) {
    //Llega una lista de palabras separadas por coma y devuelve una lista de FormaDeColaborarEnum
    return Arrays.stream(formasDeColaborar.split(","))
        .map(FormaDeColaborarEnum::valueOf)
        .collect(Collectors.toList());
  }
}
