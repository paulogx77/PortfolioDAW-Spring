package ifpb.edu.br.portfolio.util;

import org.springframework.cache.interceptor.CacheOperationInvoker;

public class DawException extends Exception{
    private static final long serialVersionUID = 7669751088704144947L;

    public DawException(String message){
        super(message);
    }

    public DawException(String message, Throwable cause){
        super(message, cause);
    }
}
