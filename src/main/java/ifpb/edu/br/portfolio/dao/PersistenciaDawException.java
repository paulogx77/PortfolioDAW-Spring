package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.util.DawException;

public class PersistenciaDawException extends DawException {
    private static final long serialVersionUID = 7159282553688713660L;

    public PersistenciaDawException(String message){
        super(message);
    }

    public PersistenciaDawException(String message, Throwable cause){
        super(message, cause);
    }

}
