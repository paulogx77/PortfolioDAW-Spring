-- 1. Habilitar PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;;

-- 2. Garantir que a coluna geométrica existe
-- Repare que dentro do BEGIN mantemos o ; normal. Só no final do $$ usamos ;;
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'perfil' AND column_name = 'localizacao') THEN
        PERFORM AddGeometryColumn('public', 'perfil', 'localizacao', 4326, 'POINT', 2);
    END IF;
END $$;;

-- 3. Função do Trigger
CREATE OR REPLACE FUNCTION atualiza_geometria_perfil()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.latitude IS NOT NULL AND NEW.longitude IS NOT NULL THEN
        NEW.localizacao := ST_SetSRID(ST_MakePoint(NEW.longitude, NEW.latitude), 4326);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;;

-- 4. Trigger
DROP TRIGGER IF EXISTS trg_atualiza_geo ON perfil;;

CREATE TRIGGER trg_atualiza_geo
BEFORE INSERT OR UPDATE ON perfil
FOR EACH ROW EXECUTE FUNCTION atualiza_geometria_perfil();;

-- 5. Função de Busca
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
$$ LANGUAGE plpgsql;;