package com.company.Repositories;

import com.company.Entities.Tracker;

import java.util.Set;

public interface IRepository<T extends Tracker> {
    void create(T obj);
    Set<T> read();
    void update(T obj);
    void delete(int id);
}
