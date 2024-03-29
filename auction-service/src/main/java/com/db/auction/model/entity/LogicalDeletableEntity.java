package com.db.auction.model.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostRemove;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

/**
 * Any entity that wants to support logical deletion, must be inherited from this class.
 * In that case, a boolean field named {@link #deleted} is added to the entity automatically, as well as all delete operations
 * will be handled in the logical deletion way automatically, thanks to {@link com.db.auction.database.listener.EnhancedDeleteEventListener}.
 *
 * @param <I> the type of identity of entity which should be a subclass of {@link Number}
 * @see com.db.auction.database.listener.EnhancedDeleteEventListener
 */

@Getter
@Setter
@MappedSuperclass
@Audited
public abstract class LogicalDeletableEntity<I extends Number> extends BaseEntity<I> {

    @Basic
    @Column(name = "deleted")
    private boolean deleted;

    @PostRemove
    public void postRemoveAction() {
        this.deleted = true;
    }
}
