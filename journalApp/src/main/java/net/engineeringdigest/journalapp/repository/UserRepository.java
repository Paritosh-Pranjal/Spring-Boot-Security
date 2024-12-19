package net.engineeringdigest.journalapp.repository;

import net.engineeringdigest.journalapp.entity.Journal;
import net.engineeringdigest.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
    User deleteByUserName(String username);
}
