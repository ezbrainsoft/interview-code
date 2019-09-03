package com.jlesoft.civilservice.koreanhistoryexam9.util;

import com.gustavofao.jsonapi.Models.ErrorModel;
import com.gustavofao.jsonapi.Models.Links;
import com.gustavofao.jsonapi.Models.Resource;

public class JSONApiObjectPost {
    private Resource data;
    private Links links;
    private boolean hasErrors = true;
    private ErrorModel errors;

    public JSONApiObjectPost() {}

    public Resource getData() {
        return data;
    }

    public void setData(Resource data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public ErrorModel getErrors() {
        return errors;
    }

}
