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
       MAPA VARCHAR(256) NOT NULL,
       DESCRIPCION VARCHAR(120),
       ACTIVA BOOLEAN NOT NULL,
       FECHA_CREACION DATE NOT NULL,
       ID_AUTO INTEGER REFERENCES AUTOMOVIL (ID_AUTOMOVIL) ON DELETE CASCADE UNIQUE NOT NULL
);

CREATE TABLE HORARIO (
       ID_HORARIO SERIAL PRIMARY KEY,
       LUNES INTEGER, 
       MARTES INTEGER,
       MIERCOLES INTEGER,
       JUEVES INTEGER,
       VIERNES INTEGER,
       SABADO INTEGER,
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
        DIA INTEGER NOT NULL,
        ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL, 
        ID_RUTA INTEGER REFERENCES RUTA (ID_RUTA) ON DELETE CASCADE NOT NULL,
        PRIMARY KEY (ID_PASAJERO, ID_RUTA, DIA)
);

CREATE TABLE SOLICITUD (
        DIA INTEGER NOT NULL,
        ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE NOT NULL, 
        ID_RUTA INTEGER REFERENCES RUTA (ID_RUTA) ON DELETE CASCADE NOT NULL,
        PRIMARY KEY (ID_PASAJERO, ID_RUTA, DIA)
);

CREATE TABLE PERFIL_PASAJERO (
        ID_PPASAJERO SERIAL PRIMARY KEY,
        PFOTO VARCHAR(256),
        PSOBRE_MI VARCHAR(256),
        PESTADO BOOLEAN,
        ID_PASAJERO INTEGER REFERENCES PASAJERO (ID_PASAJERO) ON DELETE CASCADE UNIQUE NOT NULL
);

CREATE TABLE PERFIL_CHOFER (
        ID_PCHOFER SERIAL PRIMARY KEY,
        CFOTO VARCHAR(256),
        CSOBRE_MI VARCHAR(256),
        CESTADO BOOLEAN,
        ID_CHOFER INTEGER REFERENCES CHOFER (ID_CHOFER) ON DELETE CASCADE UNIQUE NOT NULL
);
