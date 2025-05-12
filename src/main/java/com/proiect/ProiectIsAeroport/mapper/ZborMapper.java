package com.proiect.ProiectIsAeroport.mapper;

import com.proiect.ProiectIsAeroport.companie.*;
import com.proiect.ProiectIsAeroport.dto.ZborDto;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.proiect.ProiectIsAeroport.companie.Tip_Zbor.REGULAT;
import static com.proiect.ProiectIsAeroport.companie.Tip_Zbor.SEZONIER;

public class ZborMapper {
    public static Zbor convertesteDTOinZbor(ZborDto zborDto)
    {
        Model modelAvion = Model.valueOf(String.valueOf(zborDto.modelAvion).toUpperCase());
        Tip_Zbor tipZbor = Tip_Zbor.valueOf(String.valueOf(zborDto.tipZbor).toUpperCase());
        return switch (tipZbor)
        {
            case SEZONIER ->
                    {
                        Zbor_Sezonier z = new Zbor_Sezonier(zborDto.codCursa,tipZbor,zborDto.rutaDestinatie,zborDto.rutaPlecare,zborDto.pretBusiness,zborDto.pretClasa1,zborDto.pretEconomie,modelAvion,zborDto.locuriBusiness,zborDto.locuriClasa1,zborDto.locuriEconomie,zborDto.esteTurRetur,zborDto.discount,zborDto.zi,zborDto.oraPlecare,zborDto.lunaStart,zborDto.lunaEnd);
                        z.setDiscount(zborDto.discount != 0 ? zborDto.discount : 5);
                        yield z;
                    }
            case REGULAT ->
            {
                Zbor_Regulat z = new Zbor_Regulat(zborDto.codCursa,tipZbor,zborDto.rutaDestinatie,zborDto.rutaPlecare,zborDto.pretBusiness,zborDto.pretClasa1,zborDto.pretEconomie,modelAvion,zborDto.locuriBusiness,zborDto.locuriClasa1,zborDto.locuriEconomie,zborDto.esteTurRetur,zborDto.discount,zborDto.zi,zborDto.oraPlecare);
                z.setDiscount(zborDto.discount != 0 ? zborDto.discount : 5);
                yield z;
            }
        };
    }
}
