package common.entity;

public abstract class BaseEntity {
    protected Long id;

    protected BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
