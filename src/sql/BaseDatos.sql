CREATE TABLE CHOFER (
        ID_CHOFER SERIAL PRIMARY KEY,
        CNOMBRE VARCHAR(64) NOT NULL,
        CAPP VARCHAR(64) NOT NULL,
        CAPM VARCHAR(64) NOT NULL,
	CCORREO VARCHAR(128) UNIQUE NOT NULL,
        CNO_CUENTA VARCHAR(9) UNIQUE NOT NULL,
        CNO_ID VARCHAR(64) UNIQUE NOT NULL, -- numero de identificacion en licencia de calificacion 
        CFECHA_NAC DATE NOT NULL,
        CCONTRASENIA VARCHAR(128) NOT NULL
);

CREATE TABLE AUTOMOVIL (
       ID_AUTOMOVIL SERIAL PRIMARY KEY,
       NO_IDENTIFICACION VARCHAR(64) UNIQUE NOT NULL, 
       MODELO VARCHAR(64) NOT NULL,
       PLACA VARCHAR(8) UNIQUE NOT NULL,
       COLOR VARCHAR(32) NOT NULL,
       CAPACIDAD INTEGER NOT NULL,
       ID_CHOFER INTEGER REFERENCES CHOFER (ID_CHOFER) ON DELETE CASCADE UNIQUE NOT NULL
);

CREATE TABLE RUTA (
       ID_RUTA SERIAL PRIMARY KEY,
       MAPA VARCHAR(512) NOT NULL,
       DESCRIPCION VARCHAR(120),
       ACTIVA BOOLEAN NOT NULL,
       FECHA_CREACION TIMESTAMP NOT NULL,
       ID_AUTOMOVIL INTEGER REFERENCES AUTOMOVIL (ID_AUTOMOVIL) ON DELETE CASCADE UNIQUE NOT NULL
);

CREATE TABLE HORARIO (
       ID_HORARIO SERIAL PRIMARY KEY,
       LUNES TIME, 
       MARTES TIME,
       MIERCOLES TIME,
       JUEVES TIME,
       VIERNES TIME,
       SABADO TIME,
       ID_RUTA INTEGER REFERENCES RUTA (ID_RUTA) ON DELETE CASCADE UNIQUE NOT NULL
);

CREATE TABLE PASAJERO (
       ID_PASAJERO SERIAL PRIMARY KEY,
       PNOMBRE VARCHAR(64) NOT NULL,
       PAPP VARCHAR(64) NOT NULL,
       PAPM VARCHAR(64) NOT NULL,
       PCORREO VARCHAR(128) UNIQUE NOT NULL,
       PNO_CUENTA VARCHAR(9) UNIQUE NOT NULL,
       PFECHA_NAC DATE NOT NULL,
       PCONTRASENIA VARCHAR(128) NOT NULL
);

CREATE TABLE PASAJERO_RUTA (
        DIA VARCHAR(16) NOT NULL,
        ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL, 
        ID_RUTA INTEGER REFERENCES RUTA (ID_RUTA) ON DELETE CASCADE NOT NULL,
        PRIMARY KEY (ID_PASAJERO, ID_RUTA, DIA)
);

CREATE TABLE SOLICITUD (
        DIA VARCHAR(16) NOT NULL,
        ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL, 
        ID_RUTA INTEGER REFERENCES RUTA (ID_RUTA) ON DELETE CASCADE NOT NULL,
        PRIMARY KEY (ID_PASAJERO, ID_RUTA, DIA)
);

CREATE TABLE IMAGEN (
       ID_IMAGEN SERIAL PRIMARY KEY,
       NOMBRE VARCHAR(128),
       IMAGEN BYTEA NOT NULL
);

CREATE TABLE PERFIL_PASAJERO (
        ID_PPASAJERO SERIAL PRIMARY KEY,
        PFOTO INTEGER REFERENCES IMAGEN (ID_IMAGEN),
        PSOBRE_MI VARCHAR(256),
        PESTADO BOOLEAN,
	FECHA_CREACION TIMESTAMP NOT NULL,
        ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE UNIQUE NOT NULL,
	CLAVE VARCHAR(32) UNIQUE
);

CREATE TABLE PERFIL_CHOFER (
        ID_PCHOFER SERIAL PRIMARY KEY,
        CFOTO INTEGER REFERENCES IMAGEN (ID_IMAGEN),
        CSOBRE_MI VARCHAR(256),
        CESTADO BOOLEAN,
	FECHA_CREACION TIMESTAMP NOT NULL,
        ID_CHOFER INTEGER REFERENCES CHOFER (ID_CHOFER) ON DELETE CASCADE UNIQUE NOT NULL,
	CLAVE VARCHAR(32) UNIQUE
);

--PASAJERO CALIFICA A CHOFER
CREATE TABLE CALIFICACION_CHOFER (
       ID_CALIFICACIONC SERIAL PRIMARY KEY,
       CALIFICACION INTEGER NOT NULL,
       DESCRIPCION VARCHAR(256),
       FECHA TIMESTAMP NOT NULL,
       ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL,
       ID_CHOFER INTEGER REFERENCES CHOFER (ID_CHOFER) ON DELETE CASCADE NOT NULL
);

--CHOFER CALIFICA A PASAJERO
CREATE TABLE CALIFICACION_PASAJERO (
       ID_CALIFICACIONP SERIAL PRIMARY KEY,
       CALIFICACION INTEGER NOT NULL,
       DESCRIPCION VARCHAR(256),
       FECHA TIMESTAMP NOT NULL,
       ID_CHOFER INTEGER REFERENCES CHOFER (ID_CHOFER) ON DELETE CASCADE NOT NULL,
       ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL
);

--PASAJERO MANDA MENSAJE A CHOFER
CREATE TABLE MENSAJE_CHOFER (
       ID_MENSAJEC SERIAL PRIMARY KEY,
       ASUNTO VARCHAR(64) NOT NULL,
       CONTENIDO TEXT NOT NULL,
       FECHA TIMESTAMP NOT NULL,
       ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL,
       ID_CHOFER INTEGER REFERENCES CHOFER (ID_CHOFER) ON DELETE CASCADE NOT NULL
);

--CHOFER MANDA MENSAJE A PASAJERO
CREATE TABLE MENSAJE_PASAJERO (
       ID_MENSAJEP SERIAL PRIMARY KEY,
       ASUNTO VARCHAR(64) NOT NULL,
       CONTENIDO TEXT NOT NULL,
       FECHA TIMESTAMP NOT NULL,
       ID_CHOFER INTEGER REFERENCES CHOFER (ID_CHOFER) ON DELETE CASCADE NOT NULL,
       ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL
);

CREATE TABLE BOLETIN (
       ID_BOLETIN SERIAL PRIMARY KEY,
       TITULO VARCHAR(128) NOT NULL,
       CONTENIDO TEXT NOT NULL,
       FECHA TIMESTAMP NOT NULL,
       ID_RUTA INTEGER REFERENCES RUTA (ID_RUTA) ON DELETE CASCADE NOT NULL
);
