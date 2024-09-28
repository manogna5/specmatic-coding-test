package com.store.service

import com.store.dto.Product
import com.store.dto.ProductDetails
import com.store.dto.ProductId
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class ProductService {

    private val products = mutableListOf<Product>()
    private val idGenerator = AtomicInteger(1)

    fun getAllProducts(): List<Product> {
        return products.toList()
    }

    fun getProductsByType(type: String): List<Product> {
        val allowedTypes = listOf("gadget", "book", "food", "other")
        if (type !in allowedTypes) {
            throw IllegalArgumentException("Type must be one of $allowedTypes")
        }
        return products.filter { it.type == type }
    }

    fun createProduct(productDetails: ProductDetails): ProductId {
        val newId = idGenerator.getAndIncrement()
        val newProduct = Product(
            id = newId,
            name = productDetails.name,
            type = productDetails.type,
            inventory = productDetails.inventory,
            cost = productDetails.cost
        )
        products.add(newProduct)
        return ProductId(id = newId)
    }
}