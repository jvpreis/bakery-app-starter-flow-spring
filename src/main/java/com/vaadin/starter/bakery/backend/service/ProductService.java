package com.vaadin.starter.bakery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.vaadin.starter.bakery.backend.data.entity.Product;
import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.ProductRepository;

/**
 * Service class for managing {@link Product} entities in the bakery application.
 * <p>
 * Provides CRUD operations, filtering, and business logic for product management,
 * including unique name validation and integration with Spring Data repositories.
 * </p>
 * <ul>
 *   <li>Supports filtering products by name.</li>
 *   <li>Prevents saving products with duplicate names.</li>
 *   <li>Provides paginated access to products.</li>
 * </ul>
 */
@Service
public class ProductService implements FilterableCrudService<Product> {

	/**
	 * Repository for accessing product data.
	 */
	private final ProductRepository productRepository;

	/**
	 * Constructs a ProductService with the given product repository.
	 *
	 * @param productRepository the product repository
	 */
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * Finds products matching the given filter in their name.
	 *
	 * @param filter Optional filter string to search for in product names
	 * @param pageable Pagination information
	 * @return a page of products matching the filter
	 */
	@Override
	public Page<Product> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return productRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	/**
	 * Counts the number of products matching the given filter in their name.
	 *
	 * @param filter Optional filter string to search for in product names
	 * @return the count of products matching the filter
	 */
	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return productRepository.countByNameLikeIgnoreCase(repositoryFilter);
		} else {
			return count();
		}
	}

	/**
	 * Finds all products with pagination support.
	 *
	 * @param pageable Pagination information
	 * @return a page of products
	 */
	public Page<Product> find(Pageable pageable) {
		return productRepository.findBy(pageable);
	}

	/**
	 * Returns the product repository used for data access.
	 *
	 * @return the product repository
	 */
	@Override
	public JpaRepository<Product, Long> getRepository() {
		return productRepository;
	}

	/**
	 * Creates a new product entity.
	 *
	 * @param currentUser The user performing the operation
	 * @return a new product entity
	 */
	@Override
	public Product createNew(User currentUser) {
		return new Product();
	}

	/**
	 * Saves a product entity, ensuring the product name is unique.
	 *
	 * @param currentUser The user performing the operation
	 * @param entity The product entity to save
	 * @return the saved product entity
	 * @throws UserFriendlyDataException if a product with the same name already exists
	 */
	@Override
	public Product save(User currentUser, Product entity) {
		try {
			return FilterableCrudService.super.save(currentUser, entity);
		} catch (DataIntegrityViolationException e) {
			throw new UserFriendlyDataException(
					"There is already a product with that name. Please select a unique name for the product.");
		}

	}

}
