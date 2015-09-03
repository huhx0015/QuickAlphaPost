
package com.huhx0015.quickalphapost.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Entities {

    private List<Object> mentions = new ArrayList<Object>();
    private List<Object> hashtags = new ArrayList<Object>();
    private List<Link> links = new ArrayList<Link>();
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
    public List<Object> getHashtags() {
        return hashtags;
    }

    /**
     * 
     * @param hashtags
     *     The hashtags
     */
    public void setHashtags(List<Object> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     * 
     * @return
     *     The links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
