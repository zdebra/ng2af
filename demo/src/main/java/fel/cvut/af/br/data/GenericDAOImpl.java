package fel.cvut.af.br.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by zb on 11.1.16.
 */
public class GenericDAOImpl<T> implements GenericDAO<T> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> entityClass;

    public GenericDAOImpl(Class entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T create(T t) {
        this.em.persist(t);
        return t;
    }

    @Override
    public void delete(Object id) {
        this.em.remove(this.em.find(entityClass, id));
    }

    @Override
    public T find(Object id) {
        return this.em.find(entityClass, id);
    }

    @Override
    public T update(T t) {
        return this.em.merge(t);
    }

    @Override
    public List<T> findAll() {
        return this.em.createNamedQuery(entityClass.getSimpleName()+".findAll", entityClass).getResultList();
    }

}
