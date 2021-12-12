package com.cftc.app.domain;

import com.cftc.app.domain.enumeration.TheRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sub.
 */
@Entity
@Table(name = "sub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sub implements Serializable {

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

    @OneToMany(mappedBy = "sub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "sub", "unSub" }, allowSetters = true)
    private Set<Link> links = new HashSet<>();

    @OneToMany(mappedBy = "sub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    private Set<LinkData> linkData = new HashSet<>();

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

    public Sub id(Long id) {
        this.id = id;
        return this;
    }

    public TheRole getTheRole() {
        return this.theRole;
    }

    public Sub theRole(TheRole theRole) {
        this.theRole = theRole;
        return this;
    }

    public void setTheRole(TheRole theRole) {
        this.theRole = theRole;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Sub image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Sub imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public I18n getTitleFr() {
        return this.titleFr;
    }

    public Sub titleFr(I18n i18n) {
        this.setTitleFr(i18n);
        return this;
    }

    public void setTitleFr(I18n i18n) {
        this.titleFr = i18n;
    }

    public I18n getTitleEn() {
        return this.titleEn;
    }

    public Sub titleEn(I18n i18n) {
        this.setTitleEn(i18n);
        return this;
    }

    public void setTitleEn(I18n i18n) {
        this.titleEn = i18n;
    }

    public I18n getDescriptionFr() {
        return this.descriptionFr;
    }

    public Sub descriptionFr(I18n i18n) {
        this.setDescriptionFr(i18n);
        return this;
    }

    public void setDescriptionFr(I18n i18n) {
        this.descriptionFr = i18n;
    }

    public I18n getDescriptionEn() {
        return this.descriptionEn;
    }

    public Sub descriptionEn(I18n i18n) {
        this.setDescriptionEn(i18n);
        return this;
    }

    public void setDescriptionEn(I18n i18n) {
        this.descriptionEn = i18n;
    }

    public Set<Link> getLinks() {
        return this.links;
    }

    public Sub links(Set<Link> links) {
        this.setLinks(links);
        return this;
    }

    public Sub addLink(Link link) {
        this.links.add(link);
        link.setSub(this);
        return this;
    }

    public Sub removeLink(Link link) {
        this.links.remove(link);
        link.setSub(null);
        return this;
    }

    public void setLinks(Set<Link> links) {
        if (this.links != null) {
            this.links.forEach(i -> i.setSub(null));
        }
        if (links != null) {
            links.forEach(i -> i.setSub(this));
        }
        this.links = links;
    }

    public Set<LinkData> getLinkData() {
        return this.linkData;
    }

    public Sub linkData(Set<LinkData> linkData) {
        this.setLinkData(linkData);
        return this;
    }

    public Sub addLinkData(LinkData linkData) {
        this.linkData.add(linkData);
        linkData.setSub(this);
        return this;
    }

    public Sub removeLinkData(LinkData linkData) {
        this.linkData.remove(linkData);
        linkData.setSub(null);
        return this;
    }

    public void setLinkData(Set<LinkData> linkData) {
        if (this.linkData != null) {
            this.linkData.forEach(i -> i.setSub(null));
        }
        if (linkData != null) {
            linkData.forEach(i -> i.setSub(this));
        }
        this.linkData = linkData;
    }

    public UnSub getUnSub() {
        return this.unSub;
    }

    public Sub unSub(UnSub unSub) {
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
        if (!(o instanceof Sub)) {
            return false;
        }
        return id != null && id.equals(((Sub) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sub{" +
            "id=" + getId() +
            ", theRole='" + getTheRole() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
