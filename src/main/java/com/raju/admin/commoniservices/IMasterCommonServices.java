package com.raju.admin.commoniservices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic master service interface for MongoDB.
 * Provides basic CRUD and pagination operations.
 * 
 * @param <T> Entity type
 * @param <D> DTO type (if applicable)
 * 
 * @author Raju
 * @since 2025
 * @version 1.0
 */
public interface IMasterCommonServices<T, D> {

	/**
     * Save a new entity.
     */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	T save(T dto);


    /**
     * Find entity by ID.
    */
    @Transactional(readOnly = true)
    T findById(Long id);

    /**
     * Update an existing entity.
     */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	T update(Long id, T dto);

	 /**
     * Delete entity by ID.
     */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED) 
	T deleteById(Long id);

	 /**
     * Get paginated list by company ID.
     */
    @Transactional(readOnly = true)
    Page<T> findAllByCompany(Long id, Pageable pageable);
}
