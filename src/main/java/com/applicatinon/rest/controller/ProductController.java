package com.applicatinon.rest.controller;

import com.applicatinon.rest.dto.ProductDTO;
import com.applicatinon.rest.entities.Product;
import com.applicatinon.rest.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping("/findAllProducts")
    public ResponseEntity<?> getProducts(){
        List<Product> productList = productService.findAll();
        if(productList.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<ProductDTO> productDTOList = productList.stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .maker(product.getMaker())
                        .build())
                .toList();

        return ResponseEntity.ok(productDTOList);
    }

    @GetMapping("/findProductById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if(productOptional.isPresent()){
            ProductDTO productDTO = ProductDTO.builder()
                   .id(productOptional.get().getId())
                   .name(productOptional.get().getName())
                   .price(productOptional.get().getPrice())
                   .description(productOptional.get().getDescription())
                   .maker(productOptional.get().getMaker())
                   .build();
            return ResponseEntity.ok(productDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO){
        if(!productDTO.getName().isBlank()
                && !productDTO.getDescription().isBlank()){
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setMaker(productDTO.getMaker());

            productService.save(product);
            return ResponseEntity.ok("Save successfully");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateProductById/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Optional<Product> productOptional = productService.findById(id);
        if(productOptional.isPresent()){
            Product product = new Product();
            product.setId(productOptional.get().getId());
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setMaker(productDTO.getMaker());

            productService.save(product);
            return ResponseEntity.ok("Save product successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if(productOptional.isPresent()){
            productService.deleteById(id);
            return ResponseEntity.ok("Delete product successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
