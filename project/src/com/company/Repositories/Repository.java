package com.company.Repositories;

import com.company.Entities.Tracker;

import java.util.HashSet;
import java.util.Set;

public class Repository <T extends Tracker> implements IRepository<T>{
    private Set<T> context = new HashSet<>();

    @Override
    public void create(T obj) {
        obj.setId(Tracker.nextId());
        context.add(obj);
    }

    @Override
    public Set<T> read() {
        return context;
    }

    @Override
    public void update(T obj) {
        context.removeIf(a -> a.getId() == obj.getId());
        context.add(obj);
    }

    @Override
    public void delete(int id) {
        context.removeIf(a -> a.getId() == id);
    }
}
