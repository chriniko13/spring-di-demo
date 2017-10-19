package spring.di.demo.dao;

import java.util.List;

public interface GenericDao<ID, ENT> {

    List<ENT> findAll();

    ENT find(ID id);

    void insert(ENT ent);

    void update(ENT ent);

    void delete(ID id);
}
