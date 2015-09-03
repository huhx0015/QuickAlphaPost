
package com.huhx0015.quickalphapost.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Entities_ {

    private List<Object> mentions = new ArrayList<Object>();
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();
    private List<Object> links = new ArrayList<Object>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The mentions
     */
    public List<Object> getMentions() {
        return mentions;
    }

    /**
     * 
     * @param mentions
     *     The mentions
     */
    public void setMentions(List<Object> mentions) {
        this.mentions = mentions;
    }

    /**
     * 
     * @return
     *     The hashtags
     */
    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    /**
     * 
     * @param hashtags
     *     The hashtags
     */
    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     * 
     * @return
     *     The links
     */
    public List<Object> getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(List<Object> links) {
        this.links = links;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
