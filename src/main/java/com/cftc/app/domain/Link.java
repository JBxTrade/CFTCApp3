package com.cftc.app.domain;

import com.cftc.app.domain.enumeration.TheRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Link.
 */
@Entity
@Table(name = "link")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "the_role", nullable = false)
    private TheRole theRole;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @NotNull
    @Column(name = "the_link", nullable = false)
    private String theLink;

    @JsonIgnoreProperties(
        value = {
            "mainTitleFr",
            "mainTitleEn",
            "mainDescriptionFr",
            "mainDescriptionEn",
            "subTitleFr",
            "subTitleEn",
            "subDescriptionFr",
            "subDescriptionEn",
            "unSubTitleFr",
            "unSubTitleEn",
            "unSubDescriptionFr",
            "unSubDescriptionEn",
            "linkTitleFr",
            "linkTitleEn",
            "linkDescriptionFr",
            "linkDescriptionEn",
            "linkdataTitleFr",
            "linkdataTitleEn",
            "linkdataDescriptionFr",
            "linkdataDescriptionEn",
            "codeFr",
            "codeEn",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private I18n titleFr;

    @JsonIgnoreProperties(
        value = {
            "mainTitleFr",
            "mainTitleEn",
            "mainDescriptionFr",
            "mainDescriptionEn",
            "subTitleFr",
            "subTitleEn",
            "subDescriptionFr",
            "subDescriptionEn",
            "unSubTitleFr",
            "unSubTitleEn",
            "unSubDescriptionFr",
            "unSubDescriptionEn",
            "linkTitleFr",
            "linkTitleEn",
            "linkDescriptionFr",
            "linkDescriptionEn",
            "linkdataTitleFr",
            "linkdataTitleEn",
            "linkdataDescriptionFr",
            "linkdataDescriptionEn",
            "codeFr",
            "codeEn",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private I18n titleEn;

    @JsonIgnoreProperties(
        value = {
            "mainTitleFr",
            "mainTitleEn",
            "mainDescriptionFr",
            "mainDescriptionEn",
            "subTitleFr",
            "subTitleEn",
            "subDescriptionFr",
            "subDescriptionEn",
            "unSubTitleFr",
            "unSubTitleEn",
            "unSubDescriptionFr",
            "unSubDescriptionEn",
            "linkTitleFr",
            "linkTitleEn",
            "linkDescriptionFr",
            "linkDescriptionEn",
            "linkdataTitleFr",
            "linkdataTitleEn",
            "linkdataDescriptionFr",
            "linkdataDescriptionEn",
            "codeFr",
            "codeEn",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private I18n descriptionFr;

    @JsonIgnoreProperties(
        value = {
            "mainTitleFr",
            "mainTitleEn",
            "mainDescriptionFr",
            "mainDescriptionEn",
            "subTitleFr",
            "subTitleEn",
            "subDescriptionFr",
            "subDescriptionEn",
            "unSubTitleFr",
            "unSubTitleEn",
            "unSubDescriptionFr",
            "unSubDescriptionEn",
            "linkTitleFr",
            "linkTitleEn",
            "linkDescriptionFr",
            "linkDescriptionEn",
            "linkdataTitleFr",
            "linkdataTitleEn",
            "linkdataDescriptionFr",
            "linkdataDescriptionEn",
            "codeFr",
            "codeEn",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private I18n descriptionEn;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "links", "linkData", "unSub" },
        allowSetters = true
    )
    private Sub sub;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "subs", "links", "linkData" },
        allowSetters = true
    )
    private UnSub unSub;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Link id(Long id) {
        this.id = id;
        return this;
    }

    public TheRole getTheRole() {
        return this.theRole;
    }

    public Link theRole(TheRole theRole) {
        this.theRole = theRole;
        return this;
    }

    public void setTheRole(TheRole theRole) {
        this.theRole = theRole;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Link image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Link imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getTheLink() {
        return this.theLink;
    }

    public Link theLink(String theLink) {
        this.theLink = theLink;
        return this;
    }

    public void setTheLink(String theLink) {
        this.theLink = theLink;
    }

    public I18n getTitleFr() {
        return this.titleFr;
    }

    public Link titleFr(I18n i18n) {
        this.setTitleFr(i18n);
        return this;
    }

    public void setTitleFr(I18n i18n) {
        this.titleFr = i18n;
    }

    public I18n getTitleEn() {
        return this.titleEn;
    }

    public Link titleEn(I18n i18n) {
        this.setTitleEn(i18n);
        return this;
    }

    public void setTitleEn(I18n i18n) {
        this.titleEn = i18n;
    }

    public I18n getDescriptionFr() {
        return this.descriptionFr;
    }

    public Link descriptionFr(I18n i18n) {
        this.setDescriptionFr(i18n);
        return this;
    }

    public void setDescriptionFr(I18n i18n) {
        this.descriptionFr = i18n;
    }

    public I18n getDescriptionEn() {
        return this.descriptionEn;
    }

    public Link descriptionEn(I18n i18n) {
        this.setDescriptionEn(i18n);
        return this;
    }

    public void setDescriptionEn(I18n i18n) {
        this.descriptionEn = i18n;
    }

    public Sub getSub() {
        return this.sub;
    }

    public Link sub(Sub sub) {
        this.setSub(sub);
        return this;
    }

    public void setSub(Sub sub) {
        this.sub = sub;
    }

    public UnSub getUnSub() {
        return this.unSub;
    }

    public Link unSub(UnSub unSub) {
        this.setUnSub(unSub);
        return this;
    }

    public void setUnSub(UnSub unSub) {
        this.unSub = unSub;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Link)) {
            return false;
        }
        return id != null && id.equals(((Link) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Link{" +
            "id=" + getId() +
            ", theRole='" + getTheRole() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", theLink='" + getTheLink() + "'" +
            "}";
    }
}
