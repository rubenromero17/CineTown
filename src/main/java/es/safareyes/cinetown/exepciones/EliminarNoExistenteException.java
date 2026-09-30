package es.safareyes.cinetown.exepciones;
public class EliminarNoExistenteException extends RuntimeException {


    public EliminarNoExistenteException(String mensaje) {
        super(mensaje);
    }
}