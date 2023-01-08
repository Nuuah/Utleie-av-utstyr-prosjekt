SELECT * FROM Utstyr_Enhet JOIN Utstyr_Type UT on Utstyr_Enhet.Utstyr_Type_ID = UT.Utstyr_Type_ID;
/*fant en meire "clean" måte å lage tabellen*/
SELECT Utstyr_Enhet_ID, Utstyr_Navn, Utstyr_Enhet.Utstyr_Type_ID, Utstyr_Type.Beskrivelse from Utstyr_Enhet, Utstyr_Type where Utstyr_Type.Utstyr_Type_ID=Utstyr_Enhet.Utstyr_Type_ID order by Utstyr_Type_ID;