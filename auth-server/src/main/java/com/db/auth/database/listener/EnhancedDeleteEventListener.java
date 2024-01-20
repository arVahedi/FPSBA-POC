package com.db.auth.database.listener;

import com.db.auth.model.entity.LogicalDeletableEntity;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.event.internal.DefaultDeleteEventListener;
import org.hibernate.event.spi.DeleteContext;
import org.hibernate.event.spi.DeleteEvent;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

/**
 * This is an enhanced delete event listener for Hibernate that can handle both logical and physical deletions automatically
 * according to type of entity object.
 *
 * @see LogicalDeletableEntity
 */

@Component
public class EnhancedDeleteEventListener extends DefaultDeleteEventListener {

    @Override
    public void onDelete(DeleteEvent event, DeleteContext transientEntities) {
        Object eventObject = event.getObject();
        if (eventObject instanceof LogicalDeletableEntity<?> logicalDeletableObject) {
            logicalDeletableObject.setDeleted(true);
            EntityPersister entityPersister = event.getSession().getEntityPersister(event.getEntityName(), eventObject);
            EntityEntry entityEntry = event.getSession().getPersistenceContext().getEntry(eventObject);
            cascadeBeforeDelete(event.getSession(), entityPersister, entityEntry, transientEntities);

            cascadeAfterDelete(event.getSession(), entityPersister, eventObject, transientEntities);
        } else {
            super.onDelete(event, transientEntities);
        }
    }

}
