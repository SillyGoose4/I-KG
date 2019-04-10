package cn.nnu.jyjs.knowledgegraph.service;

import cn.nnu.jyjs.knowledgegraph.domain.NodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NodeRepository extends Neo4jRepository<NodeEntity,Long> {
    @Override
    default <S extends NodeEntity> S save(S s, int i) {
        return null;
    }

    @Override
    default <S extends NodeEntity> Iterable<S> save(Iterable<S> iterable, int i) {
        return null;
    }

    @Override
    default Optional<NodeEntity> findById(Long aLong, int i) {
        return Optional.empty();
    }

    @Override
    default <S extends NodeEntity> S save(S s) {
        return null;
    }

    @Override
    default <S extends NodeEntity> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    default Optional<NodeEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    default boolean existsById(Long aLong) {
        return false;
    }

    @Override
    default Iterable<NodeEntity> findAll() {
        return null;
    }

    @Override
    default Iterable<NodeEntity> findAll(int i) {
        return null;
    }

    @Override
    default Iterable<NodeEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    default Iterable<NodeEntity> findAll(Sort sort, int i) {
        return null;
    }

    @Override
    default Iterable<NodeEntity> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    default long count() {
        return 0;
    }

    @Override
    default void deleteById(Long aLong) {

    }

    @Override
    default void delete(NodeEntity nodeEntity) {

    }

    @Override
    default void deleteAll(Iterable<? extends NodeEntity> iterable) {

    }

    @Override
    default void deleteAll() {

    }

    @Override
    default Iterable<NodeEntity> findAllById(Iterable<Long> iterable, int i) {
        return null;
    }

    @Override
    default Iterable<NodeEntity> findAllById(Iterable<Long> iterable, Sort sort) {
        return null;
    }

    @Override
    default Iterable<NodeEntity> findAllById(Iterable<Long> iterable, Sort sort, int i) {
        return null;
    }

    @Override
    default Page<NodeEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    default Page<NodeEntity> findAll(Pageable pageable, int i) {
        return null;
    }
}
