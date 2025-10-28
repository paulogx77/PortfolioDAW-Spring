package ifpb.edu.br.portfolio.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainXSave {
    public static void main(String[] args) throws DawException{
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("daw")){

        }
    }
}
