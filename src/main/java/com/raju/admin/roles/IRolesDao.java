package com.raju.admin.roles;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesDao extends MongoRepository<Roles,Long> {

}
