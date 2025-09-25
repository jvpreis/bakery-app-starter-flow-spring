package com.vaadin.starter.bakery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.vaadin.starter.bakery.backend.data.entity.PickupLocation;
import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.PickupLocationRepository;

/**
 * Service class for managing {@link PickupLocation} entities in the bakery application.
 * <p>
 * Provides CRUD operations, filtering, and business logic for pickup location management,
 * including support for filtering by name and retrieving the default pickup location.
 * Integrates with Spring Data repositories for persistence.
 * </p>
 * <ul>
 *   <li>Supports filtering pickup locations by name.</li>
 *   <li>Provides paginated access to pickup locations.</li>
 *   <li>Allows retrieval of the default pickup location.</li>
 * </ul>
 */
@Service
public class PickupLocationService implements FilterableCrudService<PickupLocation>{

	private final PickupLocationRepository pickupLocationRepository;

	@Autowired
	public PickupLocationService(PickupLocationRepository pickupLocationRepository) {
		this.pickupLocationRepository = pickupLocationRepository;
	}

	/**
	 * Finds pickup locations matching the given filter in their name.
	 *
	 * @param filter Optional filter string to search for in pickup location names
	 * @param pageable Pagination information
	 * @return a page of pickup locations matching the filter
	 */
	public Page<PickupLocation> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return pickupLocationRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return pickupLocationRepository.findAll(pageable);
		}
	}

	/**
	 * Counts   the number of pickup locations matching the given filter in their name.
	 * ewrte
	 * @param filter Optional filter string to search for in pickup location names
	 * @return the count of pickup locations matching the filter
	 */
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return pickupLocationRepository.countByNameLikeIgnoreCase(repositoryFilter);
		} else {
			return pickupLocationRepository.count();
		}
	}

	/**
	 * Retrieves the default pickup location.
	 *
	 * @return the default pickup location
	 */
	public PickupLocation getDefault() {
		return findAnyMatching(Optional.empty(), PageRequest.of(0, 1)).iterator().next();
	}

	/**
	 * Returns the pickup location repository used for data access.
	 *
	 * @return the pickup location repository
	 */
	@Override
	public JpaRepository<PickupLocation, Long> getRepository() {
		return pickupLocationRepository;
	}

	/**
	 * Creates a new pickup location entity.
	 *
	 * @param currentUser The user performing the operation
	 * @return a new pickup location entity
	 */
	@Override
	public PickupLocation createNew(User currentUser) {
		return new PickupLocation();
	}
}
