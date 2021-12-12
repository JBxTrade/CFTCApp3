package com.cftc.app.domain;

import com.cftc.app.domain.enumeration.TheRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Main.
 */
@Entity
@Table(name = "main")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Main implements Serializable {

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

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "links", "linkData", "unSub" },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Sub subLink;

    @JsonIgnoreProperties(value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "sub", "unSub" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Link link;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private LinkData linkData;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Main id(Long id) {
        this.id = id;
        return this;
    }

    public TheRole getTheRole() {
        return this.theRole;
    }

    public Main theRole(TheRole theRole) {
        this.theRole = theRole;
        return this;
    }

    public void setTheRole(TheRole theRole) {
        this.theRole = theRole;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Main image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Main imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Sub getSubLink() {
        return this.subLink;
    }

    public Main subLink(Sub sub) {
        this.setSubLink(sub);
        return this;
    }

    public void setSubLink(Sub sub) {
        this.subLink = sub;
    }

    public Link getLink() {
        return this.link;
    }

    public Main link(Link link) {
        this.setLink(link);
        return this;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public LinkData getLinkData() {
        return this.linkData;
    }

    public Main linkData(LinkData linkData) {
        this.setLinkData(linkData);
        return this;
    }

    public void setLinkData(LinkData linkData) {
        this.linkData = linkData;
    }

    public I18n getTitleFr() {
        return this.titleFr;
    }

    public Main titleFr(I18n i18n) {
        this.setTitleFr(i18n);
        return this;
    }

    public void setTitleFr(I18n i18n) {
        this.titleFr = i18n;
    }

    public I18n getTitleEn() {
        return this.titleEn;
    }

    public Main titleEn(I18n i18n) {
        this.setTitleEn(i18n);
        return this;
    }

    public void setTitleEn(I18n i18n) {
        this.titleEn = i18n;
    }

    public I18n getDescriptionFr() {
        return this.descriptionFr;
    }

    public Main descriptionFr(I18n i18n) {
        this.setDescriptionFr(i18n);
        return this;
    }

    public void setDescriptionFr(I18n i18n) {
        this.descriptionFr = i18n;
    }

    public I18n getDescriptionEn() {
        return this.descriptionEn;
    }

    public Main descriptionEn(I18n i18n) {
        this.setDescriptionEn(i18n);
        return this;
    }

    public void setDescriptionEn(I18n i18n) {
        this.descriptionEn = i18n;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Main)) {
            return false;
        }
        return id != null && id.equals(((Main) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Main{" +
            "id=" + getId() +
            ", theRole='" + getTheRole() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
