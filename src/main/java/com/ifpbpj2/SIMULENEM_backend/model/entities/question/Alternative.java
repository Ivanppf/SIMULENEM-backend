package com.ifpbpj2.SIMULENEM_backend.model.entities.question;

import java.io.Serializable;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.AlternativesRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ALTERNATIVE")
public class Alternative implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "illustration_id", referencedColumnName = "id")
    private Illustration illustration;

    public Alternative() {
    }

    public Alternative(String text, Illustration illustration) {
        this.text = text;
        this.illustration = illustration;
    }

    public Alternative(AlternativesRequestDTO alternativesRequestDTO) {
        this.text = alternativesRequestDTO.text();
        if (alternativesRequestDTO.illustration() != null) {
            this.illustration = new Illustration(alternativesRequestDTO.illustration());
        }
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Illustration getIllustration() {
        return illustration;
    }

    public void setIllustration(Illustration illustration) {
        this.illustration = illustration;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((illustration == null) ? 0 : illustration.hashCode());
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
        Alternative other = (Alternative) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (illustration == null) {
            if (other.illustration != null)
                return false;
        } else if (!illustration.equals(other.illustration))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Alternative [id=" + id + ", text=" + text + ", illustration=" + illustration + "]";
    }

}
