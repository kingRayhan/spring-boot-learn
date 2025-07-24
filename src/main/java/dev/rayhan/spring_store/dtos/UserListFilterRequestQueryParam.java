package dev.rayhan.spring_store.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserListFilterQueryParam extends BaseFilterPayload{
    private UserSortByColumn sortBy = UserSortByColumn.name;
}
