package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.Profile;
import java.util.List;
import java.util.Map;

public interface ProfileDAO extends DAO<Profile, Long> {

    /**
     * Busca perfis próximos usando função PL/pgSQL do PostGIS.
     * @param lat Latitude de origem
     * @param lon Longitude de origem
     * @param raioKm Raio em Quilômetros
     * @return Lista com nome, cargo e distância
     */
    List<Map<String, Object>> buscarProximos(double lat, double lon, double raioKm);
}