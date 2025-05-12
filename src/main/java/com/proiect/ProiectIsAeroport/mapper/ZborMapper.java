package com.proiect.ProiectIsAeroport.mapper;

import com.proiect.ProiectIsAeroport.companie.*;
import com.proiect.ProiectIsAeroport.dto.ZborDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ZborMapper {
    public static Zbor convertesteDTOinZbor(ZborDto zborDto)
    {
        Tarife tarife = zborDto.tarife;
        Avion avion = new Avion(Model.valueOf(String.valueOf(zborDto.model)));
        LocalDate ziua = zborDto.ziua;
        LocalTime ora = zborDto.ora;
        Tip_Zbor tip_zbor = Tip_Zbor.valueOf(String.valueOf(zborDto.tipZbor).toUpperCase());
        LocalDate inceput= zborDto.inceput;
        LocalDate sfarsit=zborDto.sfarit;

        return switch (tip_zbor)
        {
            case SEZONIER ->
                    {
                        Zbor_Sezonier z = new Zbor_Sezonier(zborDto.codSursa,tip_zbor,zborDto.oras_destinatie,zborDto.oras_plecare,tarife,avion,zborDto.esteTurRetur,zborDto.discount,ziua,ora,inceput,sfarsit);
                        z.setDiscount(zborDto.discount != 0 ? zborDto.discount : 5);
                        yield z;
                    }
            case REGULAT ->
            {
                Zbor_Regulat z = new Zbor_Regulat(zborDto.codSursa,tip_zbor,zborDto.oras_destinatie,zborDto.oras_plecare,tarife,avion,zborDto.esteTurRetur,zborDto.discount,ziua,ora);
                z.setDiscount(zborDto.discount != 0 ? zborDto.discount : 5);
                yield z;
            }
        };
    }
}
