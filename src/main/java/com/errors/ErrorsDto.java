package fr.maileva.facile.commons.jaxrs.errors;

import java.util.ArrayList;
import java.util.List;

public class ErrorsDto {

  private List<ErrorDto> errors = new ArrayList<>();

  public ErrorsDto add(ErrorDto err) {
    errors.add(err);
    return this;
  }

  public List<ErrorDto> getErrors() {
    return errors;
  }
}
