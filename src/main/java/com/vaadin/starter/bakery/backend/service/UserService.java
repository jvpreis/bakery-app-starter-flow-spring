package com.vaadin.starter.bakery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.UserRepository;


/**
 * Service class for managing {@link User} entities in the bakery application.
 * <p>
 * Provides CRUD operations, filtering, and business logic for user management,
 * including restrictions for locked users and self-deletion. Integrates with Spring Data
 * for repository access and supports transactional operations.
 * </p>
 * <ul>
 *   <li>Prevents modification or deletion of locked users.</li>
 *   <li>Prevents users from deleting their own account.</li>
 *   <li>Supports filtering users by email, first name, last name, or role.</li>
 * </ul>
 *
 * @author Bakery App
 */
@Service
public class UserService implements FilterableCrudService<User> {

	public static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "User has been locked and cannot be modified or deleted";
	private static final String DELETING_SELF_NOT_PERMITTED = "You cannot delete your own account";
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Finds users matching the given filter in their email, first name, last name, or role.
	 *
	 * @param filter Optional filter string to search for in user fields
	 * @param pageable Pagination information
	 * @return a page of users matching the filter
	 */
	public Page<User> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository()
					.findByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(
							repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	/**
	 * Counts the number of users matching the given filter in their email, first name, last name, or role.
	 *
	 * @param filter Optional filter string to search for in user fields
	 * @return the count of users matching the filter
	 */
	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return userRepository.countByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter);
		} else {
			return count();
		}
	}

	/**
	 * Returns the user repository used for data access.
	 *
	 * @return the user repository
	 */
	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

	/**
	 * Finds all users with pagination support.
	 *
	 * @param pageable Pagination information
	 * @return a page of users
	 */
	public Page<User> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	/**
	 * Saves a user entity after checking if the user is locked.
	 *
	 * @param currentUser The user performing the operation
	 * @param entity The user entity to save
	 * @return the saved user entity
	 * @throws UserFriendlyDataException if the user is locked
	 */
	@Override
	public User save(User currentUser, User entity) {
		throwIfUserLocked(entity);
		return getRepository().saveAndFlush(entity);
	}

	/**
	 * Deletes a user entity after checking if the user is locked or if the current user is deleting themselves.
	 *
	 * @param currentUser The user performing the operation
	 * @param userToDelete The user entity to delete
	 * @throws UserFriendlyDataException if the user is locked or if the current user is deleting themselves
	 */
	@Override
	@Transactional
	public void delete(User currentUser, User userToDelete) {
		throwIfDeletingSelf(currentUser, userToDelete);
		throwIfUserLocked(userToDelete);
		FilterableCrudService.super.delete(currentUser, userToDelete);
	}

	/**
	 * Throws an exception if the current user is attempting to delete themselves.
	 *
	 * @param currentUser The user performing the operation
	 * @param user The user entity to check
	 * @throws UserFriendlyDataException if the current user is deleting themselves
	 */
	private void throwIfDeletingSelf(User currentUser, User user) {
		if (currentUser.equals(user)) {
			throw new UserFriendlyDataException(DELETING_SELF_NOT_PERMITTED);
		}
	}

	/**
	 * Throws an exception if the user entity is locked.
	 *
	 * @param entity The user entity to check
	 * @throws UserFriendlyDataException if the user is locked
	 */
	private void throwIfUserLocked(User entity) {
		if (entity != null && entity.isLocked()) {
			throw new UserFriendlyDataException(MODIFY_LOCKED_USER_NOT_PERMITTED);
		}
	}

	/**
	 * Creates a new user entity.
	 *
	 * @param currentUser The user performing the operation
	 * @return a new user entity
	 */
	@Override
	public User createNew(User currentUser) {
		return new User();
	}

}
