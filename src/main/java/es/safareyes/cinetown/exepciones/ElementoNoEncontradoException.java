package es.safareyes.cinetown.exepciones;

public class ElementoNoEncontradoException  extends RuntimeException{

    public ElementoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}