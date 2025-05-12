package com.proiect.ProiectIsAeroport.mapper;

import com.proiect.ProiectIsAeroport.companie.*;
import com.proiect.ProiectIsAeroport.dto.ZborDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ZborMapper {
    public static Zbor convertesteDTOinZbor(ZborDto zborDto)
    {
        Avion avion = new Avion(Model.valueOf(String.valueOf(zborDto.modelAvion)));
        Tip_Zbor tip_zbor = Tip_Zbor.valueOf(String.valueOf(zborDto.tipZbor).toUpperCase());
        return switch (tip_zbor)
        {
            case SEZONIER ->
                    {
                        Zbor_Sezonier z = new Zbor_Sezonier(zborDto.codCursa,tip_zbor,zborDto.rutaDestinatie,zborDto.rutaPlecare,zborDto.pretBusiness,zborDto.pretClasa1,zborDto.pretEconomie,avion,zborDto.esteTurRetur,zborDto.discount,zborDto.zi,zborDto.oraPlecare,zborDto.lunaStart,zborDto.lunaEnd);
                        z.setDiscount(zborDto.discount != 0 ? zborDto.discount : 5);
                        yield z;
                    }
            case REGULAT ->
            {
                Zbor_Regulat z = new Zbor_Regulat(zborDto.codCursa,tip_zbor,zborDto.rutaDestinatie,zborDto.rutaPlecare,zborDto.pretBusiness,zborDto.pretClasa1,zborDto.pretEconomie,avion,zborDto.esteTurRetur,zborDto.discount,zborDto.zi,zborDto.oraPlecare);
                z.setDiscount(zborDto.discount != 0 ? zborDto.discount : 5);
                yield z;
            }
        };
    }
}
