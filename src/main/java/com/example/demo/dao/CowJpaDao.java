package com.example.demo.dao;

import com.example.demo.entity.CowInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @author YM
 * @date 2019/11/18 11:47
 */
public class CowJpaDao implements PagingAndSortingRepository<CowInfo,String> {
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
    public Optional<CowInfo> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<CowInfo> findAll() {
        return null;
    }

    @Override
    public Iterable<CowInfo> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

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
