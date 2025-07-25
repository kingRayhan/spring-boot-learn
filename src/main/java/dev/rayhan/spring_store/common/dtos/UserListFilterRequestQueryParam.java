package dev.rayhan.spring_store.common.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserListFilterRequestQueryParam extends BaseFilterRequestQueryParam {
    private UserSortByColumn sortBy = UserSortByColumn.created_at;
}
