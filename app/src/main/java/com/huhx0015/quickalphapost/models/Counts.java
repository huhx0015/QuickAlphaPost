
package com.huhx0015.quickalphapost.models;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Counts {

    private int following;
    private int posts;
    private int followers;
    private int stars;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The following
     */
    public int getFollowing() {
        return following;
    }

    /**
     * 
     * @param following
     *     The following
     */
    public void setFollowing(int following) {
        this.following = following;
    }

    /**
     * 
     * @return
     *     The posts
     */
    public int getPosts() {
        return posts;
    }

    /**
     * 
     * @param posts
     *     The posts
     */
    public void setPosts(int posts) {
        this.posts = posts;
    }

    /**
     * 
     * @return
     *     The followers
     */
    public int getFollowers() {
        return followers;
    }

    /**
     * 
     * @param followers
     *     The followers
     */
    public void setFollowers(int followers) {
        this.followers = followers;
    }

    /**
     * 
     * @return
     *     The stars
     */
    public int getStars() {
        return stars;
    }

    /**
     * 
     * @param stars
     *     The stars
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
