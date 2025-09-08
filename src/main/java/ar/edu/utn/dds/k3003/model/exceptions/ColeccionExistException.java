package ar.edu.utn.dds.k3003.model.exceptions;

public class ColeccionExistException extends RuntimeException {

  private static final long serialVersionUID = 1L;


  public ColeccionExistException(String message) {
    super(message);
  }
}
