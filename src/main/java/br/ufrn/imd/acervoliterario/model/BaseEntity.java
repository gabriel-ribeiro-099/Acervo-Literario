package br.ufrn.imd.acervoliterario.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Classe base para todas as entidades, fornecendo campos comuns como ID, data de criação,
 * data de atualização e status de atividade.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected ZonedDateTime createdAt;

    @Column(name = "updated_at")
    protected ZonedDateTime updatedAt;

    @Column(name = "active")
    protected boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Define o momento de criação antes de persistir o objeto.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    /**
     * Define o momento de atualização antes de atualizar o objeto.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BaseEntity that = (BaseEntity) o;
        return active == that.active && id == that.id
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt, active);
    }
}
