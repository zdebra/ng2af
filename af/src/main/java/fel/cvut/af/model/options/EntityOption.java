package fel.cvut.af.model.options;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * An Entity class for options where elements are JPA entities.
 * <p>
 * @see Option
 */
@Entity
@DiscriminatorValue("ENTITY")
public class EntityOption extends Option<Long> {

    /**
     * The id of entity.
     */
    @NotNull
    private Long value;

    /**
     *
     * @param label in human readable form
     * @param name should be unique in scope of input
     * @param entityId the unique id of encapsulating entity
     */
    public EntityOption(String label, String name, Long entityId) {
        super(label, name);
        this.value = entityId;
    }

    public EntityOption() {
        super();
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public void setValue(Long value) {
        this.value = value;
    }
}
