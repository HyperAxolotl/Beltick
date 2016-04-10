CREATE TABLE AUTOMOVIL (
       ID_AUTOMOVIL INTEGER PRIMARY KEY,
       NO_IDENTIFICACION INTEGER UNIQUE NOT NULL, 
       MODELO VARCHAR(64) NOT NULL,
       PLACAS VARCHAR(8) UNIQUE NOT NULL,
       COLOR VARCHAR(32) NOT NULL,
       ID_CHOFER INTEGER REFERENCES CHOFER,
);

CREATE TABLE HORARIO (
       ID_HORARIO INTEGER PRIMARY KEY,
       LUNES INTEGER, 
       MARTES INTEGER,
       MIERCOLES INTEGER,
       JUEVES INTEGER,
       VIERNES INTEGER,
       ID_RUTA INTEGER REFERENCES RUTA,
);

CREATE TABLE RUTA (
       ID_RUTA INTEGER PRIMARY KEY,
       MAPA VARCHAR(256),
       DESCRIPCION VARCHAR(120),
       ACTIVA BOOLEAN,
       FECHA_CREACION DATE,
       ID_AUTO INTEGER REFERENCES AUTOMOVIL (ID_AUTO)
);

CREATE TABLE PASAJERO (
       ID_PASAJERO INTEGER PRIMARY KEY,
       PNOMBRE VARCHAR(64) NOT NULL,
       PAPP VARCHAR(64) NOT NULL,
       PAPM VARCHAR(64) NOT NULL,
       PCORREO VARCHAR(128) UNIQUE NOT NULL,
       PNO_CUENTA VARCHAR(9) UNIQUE NOT NULL,
       PFECHA_NAC DATE NOT NULL
);
