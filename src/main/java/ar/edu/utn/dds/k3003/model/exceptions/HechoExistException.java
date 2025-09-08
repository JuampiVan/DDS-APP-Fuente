package ar.edu.utn.dds.k3003.model.exceptions;

public class HechoExistException extends RuntimeException{
  private static final long serialVersionUID = 1L;


  public HechoExistException(String message) {
    super(message);
  }
}
