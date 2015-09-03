
package com.huhx0015.quickalphapost.models;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Meta {

    private String minId;
    private int code;
    private String maxId;
    private boolean more;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The minId
     */
    public String getMinId() {
        return minId;
    }

    /**
     * 
     * @param minId
     *     The min_id
     */
    public void setMinId(String minId) {
        this.minId = minId;
    }

    /**
     * 
     * @return
     *     The code
     */
    public int getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The maxId
     */
    public String getMaxId() {
        return maxId;
    }

    /**
     * 
     * @param maxId
     *     The max_id
     */
    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    /**
     * 
     * @return
     *     The more
     */
    public boolean isMore() {
        return more;
    }

    /**
     * 
     * @param more
     *     The more
     */
    public void setMore(boolean more) {
        this.more = more;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
