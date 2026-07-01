package com.orivex.common.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagedResponse<T> {

    private List<T> content;;

    private int page;

    private int size;

    private long totalItems;

    private int totalPages;

    private boolean hasNext;

    private boolean hasPrevious;

}