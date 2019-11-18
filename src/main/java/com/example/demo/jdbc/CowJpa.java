package com.example.demo.jdbc;

import com.example.demo.entity.CowInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * @author YM
 * @date 2019/11/18 17:18
 */
public class CowJpa implements PagingAndSortingRepository<CowInfo,Long> {
    @Override
    public Iterable<CowInfo> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<CowInfo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CowInfo> S save(S s) {
        return null;
    }

    @Override
    public <S extends CowInfo> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<CowInfo> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<CowInfo> findAll() {
        return null;
    }

    @Override
    public Iterable<CowInfo> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(CowInfo cowInfo) {

    }

    @Override
    public void deleteAll(Iterable<? extends CowInfo> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
