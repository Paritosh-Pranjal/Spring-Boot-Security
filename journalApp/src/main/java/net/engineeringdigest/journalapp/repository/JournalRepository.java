package net.engineeringdigest.journalapp.repository;

import net.engineeringdigest.journalapp.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Journal, ObjectId> {
}
