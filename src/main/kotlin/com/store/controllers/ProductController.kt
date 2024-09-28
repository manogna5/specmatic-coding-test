package com.store.controllers

import com.store.dto.ErrorResponseBody
import com.store.dto.ProductDetails
import com.store.dto.ProductType
import com.store.dto.validateProductDetails
import com.store.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/products")
class ProductController(private val productService:ProductService) {
    @GetMapping
    fun getProductsByType(@RequestParam(name = "type", required = false) type: String?): Any? {
        return try {
            if (type == null)
                return productService.getAllProducts()

            val products = productService.getProductsByType(type)
            ResponseEntity.ok(products)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(
                ErrorResponseBody(
                    timestamp = LocalDateTime.now().toString(),
                    status = 400,
                    error = e.message ?: "Invalid product details",
                    path = "/products"
                )
            )
        }
    }

    @PostMapping
    fun createProduct(@RequestBody @Valid productDetails: ProductDetails): ResponseEntity<Any> {
        if (validateProductDetails(productDetails)) {
            val productType = ProductType.entries.map { it.name }
            if (productType.contains(productDetails.type)) {
              return ResponseEntity.status(201).body(productService.createProduct(productDetails))
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponseBody(
                timestamp = LocalDateTime.now().toString(),
                status = 400,
                error = "Invalid product details",
                path = "/products"
            )
        )
    }
}
