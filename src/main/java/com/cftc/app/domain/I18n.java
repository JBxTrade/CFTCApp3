package com.cftc.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A I18n.
 */
@Entity
@Table(name = "i_18_n")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class I18n implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fr")
    private String fr;

    @Column(name = "en")
    private String en;

    @JsonIgnoreProperties(
        value = { "subLink", "link", "linkData", "titleFr", "titleEn", "descriptionFr", "descriptionEn" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleFr")
    private Main mainTitleFr;

    @JsonIgnoreProperties(
        value = { "subLink", "link", "linkData", "titleFr", "titleEn", "descriptionFr", "descriptionEn" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleEn")
    private Main mainTitleEn;

    @JsonIgnoreProperties(
        value = { "subLink", "link", "linkData", "titleFr", "titleEn", "descriptionFr", "descriptionEn" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionFr")
    private Main mainDescriptionFr;

    @JsonIgnoreProperties(
        value = { "subLink", "link", "linkData", "titleFr", "titleEn", "descriptionFr", "descriptionEn" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionEn")
    private Main mainDescriptionEn;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "links", "linkData", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleFr")
    private Sub subTitleFr;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "links", "linkData", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleEn")
    private Sub subTitleEn;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "links", "linkData", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionFr")
    private Sub subDescriptionFr;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "links", "linkData", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionEn")
    private Sub subDescriptionEn;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "subs", "links", "linkData" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleFr")
    private UnSub unSubTitleFr;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "subs", "links", "linkData" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleEn")
    private UnSub unSubTitleEn;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "subs", "links", "linkData" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionFr")
    private UnSub unSubDescriptionFr;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "subs", "links", "linkData" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionEn")
    private UnSub unSubDescriptionEn;

    @JsonIgnoreProperties(value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "sub", "unSub" }, allowSetters = true)
    @OneToOne(mappedBy = "titleFr")
    private Link linkTitleFr;

    @JsonIgnoreProperties(value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "sub", "unSub" }, allowSetters = true)
    @OneToOne(mappedBy = "titleEn")
    private Link linkTitleEn;

    @JsonIgnoreProperties(value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "sub", "unSub" }, allowSetters = true)
    @OneToOne(mappedBy = "descriptionFr")
    private Link linkDescriptionFr;

    @JsonIgnoreProperties(value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "sub", "unSub" }, allowSetters = true)
    @OneToOne(mappedBy = "descriptionEn")
    private Link linkDescriptionEn;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleFr")
    private LinkData linkdataTitleFr;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "titleEn")
    private LinkData linkdataTitleEn;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionFr")
    private LinkData linkdataDescriptionFr;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "descriptionEn")
    private LinkData linkdataDescriptionEn;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "codeFr")
    private LinkData codeFr;

    @JsonIgnoreProperties(
        value = { "titleFr", "titleEn", "descriptionFr", "descriptionEn", "codeFr", "codeEn", "sub", "unSub" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "codeEn")
    private LinkData codeEn;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public I18n id(Long id) {
        this.id = id;
        return this;
    }

    public String getFr() {
        return this.fr;
    }

    public I18n fr(String fr) {
        this.fr = fr;
        return this;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getEn() {
        return this.en;
    }

    public I18n en(String en) {
        this.en = en;
        return this;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public Main getMainTitleFr() {
        return this.mainTitleFr;
    }

    public I18n mainTitleFr(Main main) {
        this.setMainTitleFr(main);
        return this;
    }

    public void setMainTitleFr(Main main) {
        if (this.mainTitleFr != null) {
            this.mainTitleFr.setTitleFr(null);
        }
        if (mainTitleFr != null) {
            mainTitleFr.setTitleFr(this);
        }
        this.mainTitleFr = main;
    }

    public Main getMainTitleEn() {
        return this.mainTitleEn;
    }

    public I18n mainTitleEn(Main main) {
        this.setMainTitleEn(main);
        return this;
    }

    public void setMainTitleEn(Main main) {
        if (this.mainTitleEn != null) {
            this.mainTitleEn.setTitleEn(null);
        }
        if (mainTitleEn != null) {
            mainTitleEn.setTitleEn(this);
        }
        this.mainTitleEn = main;
    }

    public Main getMainDescriptionFr() {
        return this.mainDescriptionFr;
    }

    public I18n mainDescriptionFr(Main main) {
        this.setMainDescriptionFr(main);
        return this;
    }

    public void setMainDescriptionFr(Main main) {
        if (this.mainDescriptionFr != null) {
            this.mainDescriptionFr.setDescriptionFr(null);
        }
        if (mainDescriptionFr != null) {
            mainDescriptionFr.setDescriptionFr(this);
        }
        this.mainDescriptionFr = main;
    }

    public Main getMainDescriptionEn() {
        return this.mainDescriptionEn;
    }

    public I18n mainDescriptionEn(Main main) {
        this.setMainDescriptionEn(main);
        return this;
    }

    public void setMainDescriptionEn(Main main) {
        if (this.mainDescriptionEn != null) {
            this.mainDescriptionEn.setDescriptionEn(null);
        }
        if (mainDescriptionEn != null) {
            mainDescriptionEn.setDescriptionEn(this);
        }
        this.mainDescriptionEn = main;
    }

    public Sub getSubTitleFr() {
        return this.subTitleFr;
    }

    public I18n subTitleFr(Sub sub) {
        this.setSubTitleFr(sub);
        return this;
    }

    public void setSubTitleFr(Sub sub) {
        if (this.subTitleFr != null) {
            this.subTitleFr.setTitleFr(null);
        }
        if (subTitleFr != null) {
            subTitleFr.setTitleFr(this);
        }
        this.subTitleFr = sub;
    }

    public Sub getSubTitleEn() {
        return this.subTitleEn;
    }

    public I18n subTitleEn(Sub sub) {
        this.setSubTitleEn(sub);
        return this;
    }

    public void setSubTitleEn(Sub sub) {
        if (this.subTitleEn != null) {
            this.subTitleEn.setTitleEn(null);
        }
        if (subTitleEn != null) {
            subTitleEn.setTitleEn(this);
        }
        this.subTitleEn = sub;
    }

    public Sub getSubDescriptionFr() {
        return this.subDescriptionFr;
    }

    public I18n subDescriptionFr(Sub sub) {
        this.setSubDescriptionFr(sub);
        return this;
    }

    public void setSubDescriptionFr(Sub sub) {
        if (this.subDescriptionFr != null) {
            this.subDescriptionFr.setDescriptionFr(null);
        }
        if (subDescriptionFr != null) {
            subDescriptionFr.setDescriptionFr(this);
        }
        this.subDescriptionFr = sub;
    }

    public Sub getSubDescriptionEn() {
        return this.subDescriptionEn;
    }

    public I18n subDescriptionEn(Sub sub) {
        this.setSubDescriptionEn(sub);
        return this;
    }

    public void setSubDescriptionEn(Sub sub) {
        if (this.subDescriptionEn != null) {
            this.subDescriptionEn.setDescriptionEn(null);
        }
        if (subDescriptionEn != null) {
            subDescriptionEn.setDescriptionEn(this);
        }
        this.subDescriptionEn = sub;
    }

    public UnSub getUnSubTitleFr() {
        return this.unSubTitleFr;
    }

    public I18n unSubTitleFr(UnSub unSub) {
        this.setUnSubTitleFr(unSub);
        return this;
    }

    public void setUnSubTitleFr(UnSub unSub) {
        if (this.unSubTitleFr != null) {
            this.unSubTitleFr.setTitleFr(null);
        }
        if (unSubTitleFr != null) {
            unSubTitleFr.setTitleFr(this);
        }
        this.unSubTitleFr = unSub;
    }

    public UnSub getUnSubTitleEn() {
        return this.unSubTitleEn;
    }

    public I18n unSubTitleEn(UnSub unSub) {
        this.setUnSubTitleEn(unSub);
        return this;
    }

    public void setUnSubTitleEn(UnSub unSub) {
        if (this.unSubTitleEn != null) {
            this.unSubTitleEn.setTitleEn(null);
        }
        if (unSubTitleEn != null) {
            unSubTitleEn.setTitleEn(this);
        }
        this.unSubTitleEn = unSub;
    }

    public UnSub getUnSubDescriptionFr() {
        return this.unSubDescriptionFr;
    }

    public I18n unSubDescriptionFr(UnSub unSub) {
        this.setUnSubDescriptionFr(unSub);
        return this;
    }

    public void setUnSubDescriptionFr(UnSub unSub) {
        if (this.unSubDescriptionFr != null) {
            this.unSubDescriptionFr.setDescriptionFr(null);
        }
        if (unSubDescriptionFr != null) {
            unSubDescriptionFr.setDescriptionFr(this);
        }
        this.unSubDescriptionFr = unSub;
    }

    public UnSub getUnSubDescriptionEn() {
        return this.unSubDescriptionEn;
    }

    public I18n unSubDescriptionEn(UnSub unSub) {
        this.setUnSubDescriptionEn(unSub);
        return this;
    }

    public void setUnSubDescriptionEn(UnSub unSub) {
        if (this.unSubDescriptionEn != null) {
            this.unSubDescriptionEn.setDescriptionEn(null);
        }
        if (unSubDescriptionEn != null) {
            unSubDescriptionEn.setDescriptionEn(this);
        }
        this.unSubDescriptionEn = unSub;
    }

    public Link getLinkTitleFr() {
        return this.linkTitleFr;
    }

    public I18n linkTitleFr(Link link) {
        this.setLinkTitleFr(link);
        return this;
    }

    public void setLinkTitleFr(Link link) {
        if (this.linkTitleFr != null) {
            this.linkTitleFr.setTitleFr(null);
        }
        if (linkTitleFr != null) {
            linkTitleFr.setTitleFr(this);
        }
        this.linkTitleFr = link;
    }

    public Link getLinkTitleEn() {
        return this.linkTitleEn;
    }

    public I18n linkTitleEn(Link link) {
        this.setLinkTitleEn(link);
        return this;
    }

    public void setLinkTitleEn(Link link) {
        if (this.linkTitleEn != null) {
            this.linkTitleEn.setTitleEn(null);
        }
        if (linkTitleEn != null) {
            linkTitleEn.setTitleEn(this);
        }
        this.linkTitleEn = link;
    }

    public Link getLinkDescriptionFr() {
        return this.linkDescriptionFr;
    }

    public I18n linkDescriptionFr(Link link) {
        this.setLinkDescriptionFr(link);
        return this;
    }

    public void setLinkDescriptionFr(Link link) {
        if (this.linkDescriptionFr != null) {
            this.linkDescriptionFr.setDescriptionFr(null);
        }
        if (linkDescriptionFr != null) {
            linkDescriptionFr.setDescriptionFr(this);
        }
        this.linkDescriptionFr = link;
    }

    public Link getLinkDescriptionEn() {
        return this.linkDescriptionEn;
    }

    public I18n linkDescriptionEn(Link link) {
        this.setLinkDescriptionEn(link);
        return this;
    }

    public void setLinkDescriptionEn(Link link) {
        if (this.linkDescriptionEn != null) {
            this.linkDescriptionEn.setDescriptionEn(null);
        }
        if (linkDescriptionEn != null) {
            linkDescriptionEn.setDescriptionEn(this);
        }
        this.linkDescriptionEn = link;
    }

    public LinkData getLinkdataTitleFr() {
        return this.linkdataTitleFr;
    }

    public I18n linkdataTitleFr(LinkData linkData) {
        this.setLinkdataTitleFr(linkData);
        return this;
    }

    public void setLinkdataTitleFr(LinkData linkData) {
        if (this.linkdataTitleFr != null) {
            this.linkdataTitleFr.setTitleFr(null);
        }
        if (linkdataTitleFr != null) {
            linkdataTitleFr.setTitleFr(this);
        }
        this.linkdataTitleFr = linkData;
    }

    public LinkData getLinkdataTitleEn() {
        return this.linkdataTitleEn;
    }

    public I18n linkdataTitleEn(LinkData linkData) {
        this.setLinkdataTitleEn(linkData);
        return this;
    }

    public void setLinkdataTitleEn(LinkData linkData) {
        if (this.linkdataTitleEn != null) {
            this.linkdataTitleEn.setTitleEn(null);
        }
        if (linkdataTitleEn != null) {
            linkdataTitleEn.setTitleEn(this);
        }
        this.linkdataTitleEn = linkData;
    }

    public LinkData getLinkdataDescriptionFr() {
        return this.linkdataDescriptionFr;
    }

    public I18n linkdataDescriptionFr(LinkData linkData) {
        this.setLinkdataDescriptionFr(linkData);
        return this;
    }

    public void setLinkdataDescriptionFr(LinkData linkData) {
        if (this.linkdataDescriptionFr != null) {
            this.linkdataDescriptionFr.setDescriptionFr(null);
        }
        if (linkdataDescriptionFr != null) {
            linkdataDescriptionFr.setDescriptionFr(this);
        }
        this.linkdataDescriptionFr = linkData;
    }

    public LinkData getLinkdataDescriptionEn() {
        return this.linkdataDescriptionEn;
    }

    public I18n linkdataDescriptionEn(LinkData linkData) {
        this.setLinkdataDescriptionEn(linkData);
        return this;
    }

    public void setLinkdataDescriptionEn(LinkData linkData) {
        if (this.linkdataDescriptionEn != null) {
            this.linkdataDescriptionEn.setDescriptionEn(null);
        }
        if (linkdataDescriptionEn != null) {
            linkdataDescriptionEn.setDescriptionEn(this);
        }
        this.linkdataDescriptionEn = linkData;
    }

    public LinkData getCodeFr() {
        return this.codeFr;
    }

    public I18n codeFr(LinkData linkData) {
        this.setCodeFr(linkData);
        return this;
    }

    public void setCodeFr(LinkData linkData) {
        if (this.codeFr != null) {
            this.codeFr.setCodeFr(null);
        }
        if (codeFr != null) {
            codeFr.setCodeFr(this);
        }
        this.codeFr = linkData;
    }

    public LinkData getCodeEn() {
        return this.codeEn;
    }

    public I18n codeEn(LinkData linkData) {
        this.setCodeEn(linkData);
        return this;
    }

    public void setCodeEn(LinkData linkData) {
        if (this.codeEn != null) {
            this.codeEn.setCodeEn(null);
        }
        if (codeEn != null) {
            codeEn.setCodeEn(this);
        }
        this.codeEn = linkData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof I18n)) {
            return false;
        }
        return id != null && id.equals(((I18n) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "I18n{" +
            "id=" + getId() +
            ", fr='" + getFr() + "'" +
            ", en='" + getEn() + "'" +
            "}";
    }
}
