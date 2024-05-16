package isw.ayudantia.medical.dao;

import isw.ayudantia.medical.interfaces.IPacientDao;
import isw.ayudantia.medical.model.Pacient;
import isw.ayudantia.medical.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PacientDao implements IPacientDao {

    @Override
    public Pacient[] getPacients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            //consulta HQL
            //from Pacient es el nombre de la clase
            //Pacient.class es el tipo de dato que se espera
            //list() es el metodo que ejecuta la consulta
            //toArray(new Pacient[0]) convierte la lista en un arreglo
            
            return session.createQuery("from Pacient", Pacient.class).list().toArray(new Pacient[0]);
        }
    }

    public Pacient getPacient(String rut) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Pacient.class, rut);
        }
    }

    public Pacient savePacient(Pacient pacient) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the pacient object
            session.save(pacient);
            // commit transaction
            transaction.commit();
            return pacient;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
