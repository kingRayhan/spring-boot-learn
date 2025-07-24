package dev.rayhan.spring_store.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserListFilter {
    private UserSortByColumn sortBy = UserSortByColumn.name;
    private SortDirection sort = SortDirection.DESC;
    private Integer page = 1;
    private Integer limit = 10;
}
