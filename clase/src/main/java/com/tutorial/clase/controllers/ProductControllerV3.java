package com.tutorial.clase.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.clase.dto.ProductCollection;
import com.tutorial.clase.dto.ProductDTO;
import com.tutorial.clase.models.Product;


@RestController

@RequestMapping("/api/v3")

@CrossOrigin(origins = "*")

public class ProductControllerV3 {


// Simulación de datos

private List<Product> products = List.of(

new Product(1L, "Producto A", 10.0),

new Product(2L, "Producto B", 20.0),

new Product(3L, "Producto C", 30.0),

new Product(4L, "Producto D", 40.0),

new Product(5L, "Producto E", 50.0),

new Product(6L, "Producto F", 60.0)

);


// Endpoint para listar todos los productos con metadata adicional

@GetMapping("/products")

public ResponseEntity<ProductCollection> index() {

List<ProductDTO> dtos = products.stream()

.map(p -> new ProductDTO(p.getId(), p.getName(), p.getPrice()))

.collect(Collectors.toList());

ProductCollection collection = new ProductCollection(

dtos,

new ProductCollection.AdditionalData("Mega Store", "http://localhost:8080/api/v3/products")

);

return ResponseEntity.ok(collection);

}


// Endpoint para paginar los productos (ejemplo simple)

@GetMapping("/products/paginate")

public ResponseEntity<ProductCollection> paginate(@RequestParam(defaultValue = "1") int page) {

int pageSize = 5;

int start = (page - 1) * pageSize;

int end = Math.min(start + pageSize, products.size());

List<ProductDTO> dtos;

if (start >= products.size()) {

dtos = List.of();

} else {

dtos = products.subList(start, end).stream()

.map(p -> new ProductDTO(p.getId(), p.getName(), p.getPrice()))

.collect(Collectors.toList());

}

ProductCollection collection = new ProductCollection(

dtos,

new ProductCollection.AdditionalData("Mega Store", "http://localhost:8080/api/v3/products")

);

return ResponseEntity.ok(collection);

}

}