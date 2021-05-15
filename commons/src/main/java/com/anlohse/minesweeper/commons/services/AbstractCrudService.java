package com.anlohse.minesweeper.commons.services;

import com.anlohse.minesweeper.commons.entities.AbstractEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public abstract class AbstractCrudService<T extends AbstractEntity, ID> {

    protected CrudRepository<T, ID> repository;

    public AbstractCrudService(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        if (entity.getCreateTime() == null)
            entity.setCreateTime(new Date());
        else
            entity.setLastUpdated(new Date());
        return repository.save(entity);
    }

    public void saveAll(Iterable<T> entities) {
        entities.forEach(this::save);
    }

    public void delete(T entity) {
		entity.setDeleteTime(new Date());
		repository.save(entity);
	}

	public void remove(T entity) {
		repository.delete(entity);
	}

	public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

}
