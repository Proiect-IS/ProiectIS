package com.proiect.ProiectIsAeroport.companie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Companie {
    List<Zbor> zborList = new ArrayList<Zbor>();

    public void Creare(String codCursa, Tip_Zbor tip_zbor, String oras_plecare, String oras_destinatie, int pretBusiness,int pretClasa1,int pretEconomie, Model modelAvion,int locuriBusiness,int locuriClasa1,int locuriEconomie, boolean esteTurRetur,int discount, String ziua, String ora,String inceput,String sfarsit)
    {

        int discount_final = (esteTurRetur) ? (discount != 0 ? discount:5):0;
        Zbor zbor;
        if(tip_zbor == Tip_Zbor.SEZONIER)
        {
            zbor=new Zbor_Sezonier(codCursa,tip_zbor,oras_plecare,oras_destinatie,pretBusiness,pretClasa1,pretEconomie,modelAvion,locuriBusiness,locuriClasa1,locuriEconomie,esteTurRetur,discount,ziua,ora,inceput,sfarsit);
        }
        else
        {
            zbor=new Zbor_Regulat(codCursa,tip_zbor,oras_plecare,oras_destinatie,pretBusiness,pretClasa1,pretEconomie,modelAvion,locuriBusiness,locuriClasa1,locuriEconomie,esteTurRetur,discount,ziua,ora);
        }

        zbor.setDiscount(discount_final);
        zborList.add(zbor);
        if(esteTurRetur) {
            String codRetur = codCursa + "-R";
            Zbor zbor_retur;
            if (tip_zbor == Tip_Zbor.SEZONIER) {
                zbor_retur = new Zbor_Sezonier(codRetur, tip_zbor, oras_plecare, oras_destinatie, pretBusiness,pretClasa1,pretEconomie, modelAvion,locuriBusiness,locuriClasa1,locuriEconomie, true, discount,ziua, ora, inceput, sfarsit);
            } else {
                zbor_retur = new Zbor_Regulat(codCursa,tip_zbor,oras_plecare,oras_destinatie,pretBusiness,pretClasa1,pretEconomie,modelAvion,locuriBusiness,locuriClasa1,locuriEconomie,true,discount,ziua,ora);
            }
            zbor_retur.setDiscount(discount_final);
            zborList.add(zbor_retur);
        }
    }

    public boolean Steregere(String codCursa)
    {
        Iterator<Zbor> iterator = zborList.iterator();
        while (iterator.hasNext())
        {
            Zbor zbor = iterator.next();
            if(zbor.getCod_cursa().equals(codCursa))
            {
                iterator.remove();
                return true;
            }
        }
        return false;
    }


}
