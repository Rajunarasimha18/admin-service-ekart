package com.raju.admin.appuser;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppUserDao extends MongoRepository<AppUser, Long> {

	@Query("{ 'is_deleted_value': false, 'current_token': ?0 }")
	AppUser findAppUserByToken(String token);

	@Query(value = "{ " + "'is_deleted_value': false, " + "'user_name': { '$ne': 'admin@ekart.com' }, " + "'$or': [ "
			+ "{ 'full_name': { '$regex': ?0, '$options': 'i' } }, "
			+ "{ 'user_email': { '$regex': ?0, '$options': 'i' } }, "
			+ "{ 'mobile': { '$regex': ?0, '$options': 'i' } }, "
			+ "{ 'user_name': { '$regex': ?0, '$options': 'i' } } " + "] " + "}", sort = "{ 'created_date': -1 }")
	Page<AppUser> getUserList(String search, Pageable pageable);

	@Query(value = "{ " + "'is_deleted_value': false, " + "'user_name': { '$ne': 'admin@ekart.com' } "
			+ "}", sort = "{ 'created_date': -1 }")
	Page<AppUser> getUserList1(Pageable pageable);

	@Query("{ 'is_deleted_value': false, 'user_name': ?0 }")
	List<AppUser> findByUserNameByList(String userName);

	@Query("{ 'user_name': ?0, 'is_deleted_value': false }")
	AppUser findByUserName(String userName);

	@Query("{ 'user_email': ?0, 'is_deleted_value': false }")
	AppUser findUserByEmail(String email);

	@Query(value = "{ 'user_email': ?0, 'is_deleted_value': false }", exists = true)
	boolean existsUserByEmail(String email);

	@Query(value = "{ 'mobile': ?0, 'is_deleted_value': false }", exists = true)
	boolean existsUserByMobile(String email);
}
