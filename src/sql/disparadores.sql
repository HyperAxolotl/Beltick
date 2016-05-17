CREATE or replace FUNCTION cr_pperfil() RETURNS TRIGGER AS $trig_cr_pperfil$
declare
	a varchar(32);
begin
	SELECT md5(random()::text) INTO a;
	INSERT INTO perfil_pasajero (pestado,id_pasajero,fecha_creacion,clave) values(false,new.id_pasajero,current_timestamp,a);
	return null;
end;
$trig_cr_pperfil$ LANGUAGE plpgsql;

CREATE TRIGGER trig_cr_pperfil
AFTER INSERT ON PASAJERO
      FOR EACH ROW EXECUTE PROCEDURE cr_pperfil();

CREATE or replace FUNCTION cr_cperfil() RETURNS TRIGGER AS $trig_cr_cperfil$
declare
	a varchar(32);
begin
	SELECT md5(random()::text) INTO a;
	INSERT INTO perfil_chofer (cestado,id_chofer,fecha_creacion,clave) values(false,new.id_chofer,current_timestamp,a);
	return null;
end;
$trig_cr_cperfil$ LANGUAGE plpgsql;

CREATE TRIGGER trig_cr_cperfil
AFTER INSERT ON CHOFER
      FOR EACH ROW EXECUTE PROCEDURE cr_cperfil();
