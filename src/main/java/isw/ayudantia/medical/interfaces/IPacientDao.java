package isw.ayudantia.medical.interfaces;

import isw.ayudantia.medical.model.Pacient;

public interface IPacientDao {
    Pacient[] getPacients();

    Pacient getPacient(String rut);

    Pacient savePacient(Pacient pacient);
}