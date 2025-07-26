package dev.rayhan.spring_store.apis.cart.mappers;

import dev.rayhan.spring_store.apis.cart.dtos.CartDto;
import dev.rayhan.spring_store.apis.cart.dtos.CartItemDto;
import dev.rayhan.spring_store.apis.cart.entities.Cart;
import dev.rayhan.spring_store.apis.cart.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
  @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
  CartDto toDto(Cart cart);

  @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
  CartItemDto toDto(CartItem cart);
}
