package com.ifpbpj2.SIMULENEM_backend.model.entities.question;

import java.io.Serializable;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.IllustrationRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ILLUSTRATION")
public class Illustration implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    @Column(nullable = false)
    private String url;

    public Illustration() {
    }

    public Illustration(String description, String url) {
        this.description = description;
        this.url = url;
    }

    public Illustration(IllustrationRequestDTO illustrationRequestDTO) {
        this.description = illustrationRequestDTO.description();
        this.url = illustrationRequestDTO.url();
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Illustration other = (Illustration) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Illustration [id=" + id + " , description=" + description + ", url=" + url + "]";
    }

}
