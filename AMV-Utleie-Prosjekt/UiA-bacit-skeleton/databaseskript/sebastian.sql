select Reservasjon_ID, Fornavn, Etternavn, Utstyr_Navn, Dato_Laant, Dato_Levert
from Reservasjon
join Utstyr_Enhet UE on Reservasjon.Utstyr_Enhet_ID = UE.Utstyr_Enhet_ID
join Utstyr_Type UT on UE.Utstyr_Type_ID = UT.Utstyr_Type_ID
join Ansatt A on Reservasjon.Ansatt_ID = A.Ansatt_ID
where Reservasjon.Ansatt_ID = (
    select Ansatt_ID
    from Reservasjon
    GROUP BY Ansatt_ID
    ORDER BY count(Ansatt_ID) DESC
    LIMIT 1
    )
order by Dato_Laant;
