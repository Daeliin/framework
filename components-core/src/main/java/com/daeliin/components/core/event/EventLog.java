package com.daeliin.components.core.event;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(of = {"descriptionKey", "creationDate"})
@Entity
public class EventLog extends UUIDPersistentResource implements Comparable<EventLog> {
    
    private static final long serialVersionUID = -5353349286441881283L;

    @NotBlank
    private String descriptionKey;
    
    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public EventLog(String descriptionKey, LocalDateTime creationDate) {
        this.descriptionKey = descriptionKey;
        this.creationDate = creationDate;
    }
    
    @Override
    public int compareTo(EventLog other) {
        boolean creationDatesAreNotNull = this.creationDate != null && other.creationDate != null;
        
        if (this.equals(other)) {
            return 0;
        }
        
        if (creationDatesAreNotNull) {
            return this.creationDate.compareTo(other.creationDate);
        } else {
            return -1;
        }
    }
}
