package com.store.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull

data class ProductDetails
@JsonCreator constructor(

    @JsonProperty("name")
    val name: String = "",

    @JsonProperty("type")
    @NotNull
    val type: String = "",

    @JsonProperty("inventory") val inventory: Int = 0,

    @JsonProperty("cost") val cost: Int? = 0
)

fun validateProductDetails(productDetails: ProductDetails) =
    (productDetails.name.matches(Regex("^[a-zA-Z ]+$"))
            && productDetails.name != "true"
            && productDetails.name != "false"
            && productDetails.inventory >= 1
            && productDetails.inventory <= 9999
            && productDetails.cost!=null)

data class ProductId(
    val id: Int
)

enum class ProductType(s: String) {
    book("book"), food("food"), gadget("gadget"), other("other")
}
