package com.huhx0015.quickalphapost.models;

/**
 * Created by Michael Yoon Huh on 9/3/2015.
 */
public class Post {

    public AvatarImage avatarImage;
    public Counts counts;
    public CoverImage coverImage;
    public Datum datum;
    public Description description;
    public Entities entities;
    public Entities_ entities_;
    public Example example;
    public Hashtag hashtag;
    public Link link;
    public Meta meta;
    public Source source;
    public User user;

    /** GET METHODS ____________________________________________________________________________ **/

    public AvatarImage getAvatarImage() {
        return avatarImage;
    }

    public Counts getCounts() {
        return counts;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public Datum getDatum() {
        return datum;
    }

    public Description getDescription() {
        return description;
    }

    public Entities getEntities() {
        return entities;
    }

    public Entities_ getEntities_() {
        return entities_;
    }

    public Example getExample() {
        return example;
    }

    public Hashtag getHashtag() {
        return hashtag;
    }

    public Link getLink() {
        return link;
    }

    public Meta getMeta() {
        return meta;
    }

    public Source getSource() {
        return source;
    }

    public User getUser() {
        return user;
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setAvatarImage(AvatarImage avatarImage) {
        this.avatarImage = avatarImage;
    }

    public void setCounts(Counts counts) {
        this.counts = counts;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public void setEntities_(Entities_ entities_) {
        this.entities_ = entities_;
    }

    public void setExample(Example example) {
        this.example = example;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
