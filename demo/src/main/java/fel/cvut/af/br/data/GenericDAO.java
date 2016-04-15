package fel.cvut.af.br.data;

import java.util.List;

/**
 * Created by zb on 11.1.16.
 */
public interface GenericDAO<T> {

    T create(T t);

    void delete(Object id);

    T find(Object id);

    T update(T t);

    List<T> findAll();


}
