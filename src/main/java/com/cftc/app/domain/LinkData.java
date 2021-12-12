package com.cftc.app.domain;

import com.cftc.app.domain.enumeration.TheRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LinkData.
 */
@Entity
@Table(name = "link_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LinkData implements Serializable {

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
    @Column(name = "image_card", nullable = false)
    private byte[] imageCard;

    @Column(name = "image_card_content_type", nullable = false)
    private String imageCardContentType;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "image_2")
    private byte[] image2;

    @Column(name = "image_2_content_type")
    private String image2ContentType;

    @Lob
    @Column(name = "image_3")
    private byte[] image3;

    @Column(name = "image_3_content_type")
    private String image3ContentType;

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
    private I18n codeFr;

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
    private I18n codeEn;

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

    public LinkData id(Long id) {
        this.id = id;
        return this;
    }

    public TheRole getTheRole() {
        return this.theRole;
    }

    public LinkData theRole(TheRole theRole) {
        this.theRole = theRole;
        return this;
    }

    public void setTheRole(TheRole theRole) {
        this.theRole = theRole;
    }

    public byte[] getImageCard() {
        return this.imageCard;
    }

    public LinkData imageCard(byte[] imageCard) {
        this.imageCard = imageCard;
        return this;
    }

    public void setImageCard(byte[] imageCard) {
        this.imageCard = imageCard;
    }

    public String getImageCardContentType() {
        return this.imageCardContentType;
    }

    public LinkData imageCardContentType(String imageCardContentType) {
        this.imageCardContentType = imageCardContentType;
        return this;
    }

    public void setImageCardContentType(String imageCardContentType) {
        this.imageCardContentType = imageCardContentType;
    }

    public byte[] getImage() {
        return this.image;
    }

    public LinkData image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public LinkData imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getImage2() {
        return this.image2;
    }

    public LinkData image2(byte[] image2) {
        this.image2 = image2;
        return this;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public String getImage2ContentType() {
        return this.image2ContentType;
    }

    public LinkData image2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
        return this;
    }

    public void setImage2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
    }

    public byte[] getImage3() {
        return this.image3;
    }

    public LinkData image3(byte[] image3) {
        this.image3 = image3;
        return this;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getImage3ContentType() {
        return this.image3ContentType;
    }

    public LinkData image3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
        return this;
    }

    public void setImage3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
    }

    public I18n getTitleFr() {
        return this.titleFr;
    }

    public LinkData titleFr(I18n i18n) {
        this.setTitleFr(i18n);
        return this;
    }

    public void setTitleFr(I18n i18n) {
        this.titleFr = i18n;
    }

    public I18n getTitleEn() {
        return this.titleEn;
    }

    public LinkData titleEn(I18n i18n) {
        this.setTitleEn(i18n);
        return this;
    }

    public void setTitleEn(I18n i18n) {
        this.titleEn = i18n;
    }

    public I18n getDescriptionFr() {
        return this.descriptionFr;
    }

    public LinkData descriptionFr(I18n i18n) {
        this.setDescriptionFr(i18n);
        return this;
    }

    public void setDescriptionFr(I18n i18n) {
        this.descriptionFr = i18n;
    }

    public I18n getDescriptionEn() {
        return this.descriptionEn;
    }

    public LinkData descriptionEn(I18n i18n) {
        this.setDescriptionEn(i18n);
        return this;
    }

    public void setDescriptionEn(I18n i18n) {
        this.descriptionEn = i18n;
    }

    public I18n getCodeFr() {
        return this.codeFr;
    }

    public LinkData codeFr(I18n i18n) {
        this.setCodeFr(i18n);
        return this;
    }

    public void setCodeFr(I18n i18n) {
        this.codeFr = i18n;
    }

    public I18n getCodeEn() {
        return this.codeEn;
    }

    public LinkData codeEn(I18n i18n) {
        this.setCodeEn(i18n);
        return this;
    }

    public void setCodeEn(I18n i18n) {
        this.codeEn = i18n;
    }

    public Sub getSub() {
        return this.sub;
    }

    public LinkData sub(Sub sub) {
        this.setSub(sub);
        return this;
    }

    public void setSub(Sub sub) {
        this.sub = sub;
    }

    public UnSub getUnSub() {
        return this.unSub;
    }

    public LinkData unSub(UnSub unSub) {
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
        if (!(o instanceof LinkData)) {
            return false;
        }
        return id != null && id.equals(((LinkData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkData{" +
            "id=" + getId() +
            ", theRole='" + getTheRole() + "'" +
            ", imageCard='" + getImageCard() + "'" +
            ", imageCardContentType='" + getImageCardContentType() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", image2='" + getImage2() + "'" +
            ", image2ContentType='" + getImage2ContentType() + "'" +
            ", image3='" + getImage3() + "'" +
            ", image3ContentType='" + getImage3ContentType() + "'" +
            "}";
    }
}
