package dev.rayhan.spring_store.common.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BaseFilterRequestQueryParam {
    private SortDirection sort = SortDirection.DESC;
    private Integer page = 1;
    private Integer limit = 10;
}
