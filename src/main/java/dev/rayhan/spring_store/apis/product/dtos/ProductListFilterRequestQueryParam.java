package dev.rayhan.spring_store.apis.product.dtos;

import dev.rayhan.spring_store.common.dtos.BaseFilterRequestQueryParam;
import dev.rayhan.spring_store.common.dtos.ProductSortByColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter @Setter
public class ProductListFilterRequestQueryParam extends BaseFilterRequestQueryParam {
    private UUID categoryId;
    private ProductSortByColumn sortBy = ProductSortByColumn.createdAt;
}
