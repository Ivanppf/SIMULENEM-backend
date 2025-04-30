package com.ifpbpj2.SIMULENEM_backend.model.entities;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ALTERNATIVE")
public class Alternative implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private char options;
    
    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "illustration_id", referencedColumnName = "id")
    private Illustration illustration;
    
    public Alternative() {
    }

    public Alternative(char options, String text, Illustration illustration) {
        this.options = options;
        this.text = text;
        this.illustration = illustration;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public char getOptions() {
        return options;
    }
    public void setOptions(char options) {
        this.options = options;
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
        return true;
    }

    @Override
    public String toString() {
        return "Alternative [id=" + id + ", options=" + options + ", text=" + text + ", illustration=" + illustration
                + "]";
    }

    
    
}
