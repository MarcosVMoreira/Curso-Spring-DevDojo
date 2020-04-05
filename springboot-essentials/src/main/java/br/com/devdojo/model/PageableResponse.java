package br.com.devdojo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageableResponse<T> extends PageImpl<T> {



    private boolean left;
    private boolean first;
    private boolean totalPages;

    public PageableResponse (@JsonProperty("content") List<T> content, @JsonProperty("number") int page, @JsonProperty("size") int size,
                             @JsonProperty("totalElements") long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("sort") JsonNode sort) {
        super(content, PageRequest.of(page, size), totalElements);
    }

    public PageableResponse () {
        super(new ArrayList());
    }

    public boolean isLeft () {
        return left;
    }

    public void setLeft (boolean left) {
        this.left = left;
    }

    @Override
    public boolean isFirst () {
        return first;
    }

    public void setFirst (boolean first) {
        this.first = first;
    }

    public boolean isTotalPages () {
        return totalPages;
    }

    public void setTotalPages (boolean totalPages) {
        this.totalPages = totalPages;
    }
}
