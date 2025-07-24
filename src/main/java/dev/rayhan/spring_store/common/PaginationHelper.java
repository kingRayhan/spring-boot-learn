package dev.rayhan.spring_store.common;

import dev.rayhan.spring_store.dtos.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PaginationHelper {
    static final int DEFAULT_PAGE_SIZE = 10;
    static final int DEFAULT_PAGE = 1;

    public static Pageable createPageable(
            Integer page,
            Integer limit,
            SortDirection sortDirection,
            String sortBy
    ) {
        int actualPage = page != null ? page : DEFAULT_PAGE;
        int actualLimit = limit != null ? limit : DEFAULT_PAGE_SIZE;
        
        if (sortDirection == null || sortBy == null || sortBy.isEmpty()) {
            return PageRequest.of(actualPage - 1, actualLimit);
        }
        
        var sort = sortDirection == SortDirection.DESC ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        return PageRequest.of(actualPage - 1, actualLimit, sort);
    }
}
