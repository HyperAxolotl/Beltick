CREATE or replace FUNCTION cr_pperfil() RETURNS TRIGGER AS $trig_cr_pperfil$
begin
INSERT INTO perfil_pasajero (pestado,id_pasajero) values(false,new.id_pasajero);
return null;
end;
$trig_cr_pperfil$ LANGUAGE plpgsql;

CREATE TRIGGER trig_cr_pperfil
AFTER INSERT ON PASAJERO
FOR EACH ROW EXECUTE PROCEDURE cr_pperfil();

CREATE or replace FUNCTION cr_cperfil() RETURNS TRIGGER AS $trig_cr_cperfil$
begin
INSERT INTO perfil_chofer (pestado,id_chofer) values(false,new.id_chofer);
return null;
end;
$trig_cr_cperfil$ LANGUAGE plpgsql;

CREATE TRIGGER trig_cr_cperfil
AFTER INSERT ON CHOFER
FOR EACH ROW EXECUTE PROCEDURE cr_cperfil();


