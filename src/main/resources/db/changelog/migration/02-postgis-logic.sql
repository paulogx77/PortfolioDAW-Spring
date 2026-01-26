--liquibase formatted sql

-- Habilita a extensão PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- Adiciona a coluna geométrica na tabela perfil
-- Nota: O Liquibase gerencia erros, então não precisamos de IF NOT EXISTS complexos
SELECT AddGeometryColumn('public', 'perfil', 'localizacao', 4326, 'POINT', 2);

-- Cria a função de trigger
CREATE OR REPLACE FUNCTION atualiza_geometria_perfil()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.latitude IS NOT NULL AND NEW.longitude IS NOT NULL THEN
        NEW.localizacao := ST_SetSRID(ST_MakePoint(NEW.longitude, NEW.latitude), 4326);
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_atualiza_geo ON perfil;
CREATE TRIGGER trg_atualiza_geo
    BEFORE INSERT OR UPDATE ON perfil
                         FOR EACH ROW EXECUTE FUNCTION atualiza_geometria_perfil();

-- Cria a função de busca (Networking)
CREATE OR REPLACE FUNCTION buscar_usuarios_proximos(
    lat_origem DOUBLE PRECISION,
    lon_origem DOUBLE PRECISION,
    raio_km DOUBLE PRECISION
)
RETURNS TABLE (
    nome_perfil VARCHAR,
    cargo VARCHAR,
    distancia_km DOUBLE PRECISION
) AS $$
BEGIN
RETURN QUERY
SELECT
    p.nome,
    p.cargo,
    (ST_Distance(
             p.localizacao::geography,
             ST_SetSRID(ST_MakePoint(lon_origem, lat_origem), 4326)::geography
     ) / 1000) AS distancia
FROM perfil p
WHERE
    p.localizacao IS NOT NULL
  AND ST_DWithin(
        p.localizacao::geography,
        ST_SetSRID(ST_MakePoint(lon_origem, lat_origem), 4326)::geography,
        raio_km * 1000
      )
ORDER BY distancia ASC;
END;
$$ LANGUAGE plpgsql;