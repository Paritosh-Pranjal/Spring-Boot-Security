package net.engineeringdigest.journalapp.entity;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//@Document  similar to @Entity
@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class Journal {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}
