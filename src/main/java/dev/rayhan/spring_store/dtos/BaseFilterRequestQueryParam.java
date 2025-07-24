package dev.rayhan.spring_store.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseFilterRequestQueryParam {
    private SortDirection sort = SortDirection.DESC;
    private Integer page = 1;
    private Integer limit = 10;
}
