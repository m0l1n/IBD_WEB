CREATE OR REPLACE TYPE place AS OBJECT (
	rang INTEGER,
	num INTEGER
);
/
CREATE OR REPLACE TYPE array_place AS TABLE OF place;
/
CREATE OR REPLACE PROCEDURE bookPlaces
(
	NUM_ZONE IN LesPlaces.numZ%TYPE,
	NUM_SPECT IN LesRepresentations.numS%TYPE,
	DATE_REP IN LesRepresentations.dateRep%TYPE,
	QUANTITY IN INTEGER,
	PLACES_BOOKED OUT array_place
)
IS
	CURSOR C_PLACE(
		N_Z LesPlaces.numZ%TYPE, 
		N_S LesRepresentations.numS%TYPE, 
		D_R LesRepresentations.dateRep%TYPE,
		Q INTEGER
	) IS
		SELECT noPlace, noRang
		FROM LesPlaces P
		WHERE NOT EXISTS (
			SELECT * 
			FROM LesTickets T
			JOIN LesPlaces P2 ON P2.noPlace = T.noPlace AND P2.noRang = T.noRang
			WHERE T.dateRep = D_R AND T.numS = N_S AND P2.noRang = P.noRang AND P2.noPlace = P.noPlace
		) AND P.numZ = N_Z AND ROWNUM <= Q
		FOR UPDATE;
	CURSOR C_TARIF(
		N_Z LesPlaces.numZ%TYPE,
		Q INTEGER
	) IS
		SELECT prix*Q
		FROM LesCategories 
		JOIN LesZones ON LesCategories.nomC = LesZones.nomC 
		WHERE numZ = N_Z;
	CURSOR C_NUM_DOSSIER IS
		SELECT MAX(noDossier)+1
		FROM LesDossiers;
	TARIF_RESA NUMBER;
	NUM_DOSSIER INTEGER;
	IDX INTEGER;
BEGIN
	PLACES_BOOKED := NEW array_place();
	FOR R_PLACE IN C_PLACE(NUM_ZONE, NUM_SPECT, DATE_REP, QUANTITY) LOOP
		PLACES_BOOKED.EXTEND;
		PLACES_BOOKED(PLACES_BOOKED.count) := place(R_PLACE.noRang, R_PLACE.noPlace);
	END LOOP;
	--S'il n'y a pas assez de place dispo on vide la liste et on stoppe la procedure
	IF PLACES_BOOKED.count <> QUANTITY THEN
		PLACES_BOOKED.DELETE;
		RETURN;
	END IF;
	--On recupere le tarif de la reservation
	OPEN C_TARIF(NUM_ZONE, QUANTITY);
	FETCH C_TARIF INTO TARIF_RESA;
	--On recupere un num de dossier
	OPEN C_NUM_DOSSIER;
	FETCH C_NUM_DOSSIER INTO NUM_DOSSIER;
	--Creation du dossier
	INSERT INTO LesDossiers VALUES (NUM_DOSSIER, TARIF_RESA);
	--Creation des tickets
	IDX := PLACES_BOOKED.first;
	WHILE IDX IS NOT NULL LOOP
		INSERT INTO LesTickets SELECT COALESCE(MAX(noSerie), 0)+1, NUM_SPECT, DATE_REP, PLACES_BOOKED(IDX).num, PLACES_BOOKED(IDX).rang, SYSDATE, NUM_DOSSIER FROM LesTickets;
		IDX := PLACES_BOOKED.NEXT(IDX);
	END LOOP;

	CLOSE C_TARIF;
	CLOSE C_NUM_DOSSIER;
END;
/