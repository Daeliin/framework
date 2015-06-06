package com.daeliin.framework.commons.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@MappedSuperclass
public abstract class LongIdPersistentResource implements PersistentResource<Long> {
    
    @Id
    @GeneratedValue
    private Long id;
}
