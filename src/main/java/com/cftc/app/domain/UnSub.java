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
 * A UnSub.
 */
@Entity
@Table(name = "un_sub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnSub implements Serializable {

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

    @OneToMany(mappedBy = "unSub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "links", "linkData", "unSub" },
        allowSetters = true
    )
    private Set<Sub> subs = new HashSet<>();

    @OneToMany(mappedBy = "unSub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "sub", "unSub" }, allowSetters = true)
    private Set<Link> links = new HashSet<>();

    @OneToMany(mappedBy = "unSub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    private Set<LinkData> linkData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnSub id(Long id) {
        this.id = id;
        return this;
    }

    public TheRole getTheRole() {
        return this.theRole;
    }

    public UnSub theRole(TheRole theRole) {
        this.theRole = theRole;
        return this;
    }

    public void setTheRole(TheRole theRole) {
        this.theRole = theRole;
    }

    public byte[] getImage() {
        return this.image;
    }

    public UnSub image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public UnSub imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public I18n getTitleFr() {
        return this.titleFr;
    }

    public UnSub titleFr(I18n i18n) {
        this.setTitleFr(i18n);
        return this;
    }

    public void setTitleFr(I18n i18n) {
        this.titleFr = i18n;
    }

    public I18n getTitleEn() {
        return this.titleEn;
    }

    public UnSub titleEn(I18n i18n) {
        this.setTitleEn(i18n);
        return this;
    }

    public void setTitleEn(I18n i18n) {
        this.titleEn = i18n;
    }

    public I18n getDescriptionFr() {
        return this.descriptionFr;
    }

    public UnSub descriptionFr(I18n i18n) {
        this.setDescriptionFr(i18n);
        return this;
    }

    public void setDescriptionFr(I18n i18n) {
        this.descriptionFr = i18n;
    }

    public I18n getDescriptionEn() {
        return this.descriptionEn;
    }

    public UnSub descriptionEn(I18n i18n) {
        this.setDescriptionEn(i18n);
        return this;
    }

    public void setDescriptionEn(I18n i18n) {
        this.descriptionEn = i18n;
    }

    public Set<Sub> getSubs() {
        return this.subs;
    }

    public UnSub subs(Set<Sub> subs) {
        this.setSubs(subs);
        return this;
    }

    public UnSub addSub(Sub sub) {
        this.subs.add(sub);
        sub.setUnSub(this);
        return this;
    }

    public UnSub removeSub(Sub sub) {
        this.subs.remove(sub);
        sub.setUnSub(null);
        return this;
    }

    public void setSubs(Set<Sub> subs) {
        if (this.subs != null) {
            this.subs.forEach(i -> i.setUnSub(null));
        }
        if (subs != null) {
            subs.forEach(i -> i.setUnSub(this));
        }
        this.subs = subs;
    }

    public Set<Link> getLinks() {
        return this.links;
    }

    public UnSub links(Set<Link> links) {
        this.setLinks(links);
        return this;
    }

    public UnSub addLink(Link link) {
        this.links.add(link);
        link.setUnSub(this);
        return this;
    }

    public UnSub removeLink(Link link) {
        this.links.remove(link);
        link.setUnSub(null);
        return this;
    }

    public void setLinks(Set<Link> links) {
        if (this.links != null) {
            this.links.forEach(i -> i.setUnSub(null));
        }
        if (links != null) {
            links.forEach(i -> i.setUnSub(this));
        }
        this.links = links;
    }

    public Set<LinkData> getLinkData() {
        return this.linkData;
    }

    public UnSub linkData(Set<LinkData> linkData) {
        this.setLinkData(linkData);
        return this;
    }

    public UnSub addLinkData(LinkData linkData) {
        this.linkData.add(linkData);
        linkData.setUnSub(this);
        return this;
    }

    public UnSub removeLinkData(LinkData linkData) {
        this.linkData.remove(linkData);
        linkData.setUnSub(null);
        return this;
    }

    public void setLinkData(Set<LinkData> linkData) {
        if (this.linkData != null) {
            this.linkData.forEach(i -> i.setUnSub(null));
        }
        if (linkData != null) {
            linkData.forEach(i -> i.setUnSub(this));
        }
        this.linkData = linkData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnSub)) {
            return false;
        }
        return id != null && id.equals(((UnSub) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnSub{" +
            "id=" + getId() +
            ", theRole='" + getTheRole() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
