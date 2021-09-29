/*
 * Copyright 2021 Opcal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.opcal.cloud.commons.integration.controller;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.integration.entity.Product;
import xyz.opcal.cloud.commons.integration.entity.ProductResult;

@Slf4j
@RestController
public class ProductController {

    @GetMapping(value = "/product/{id}")
    public Product getProduct(@PathVariable long id) {

        Product product = new Product();
        product.setId(id);
        product.setName("Apple");
        product.setPrice(new BigDecimal("12.56"));
        product.setDescription("fruit");

        log.info("get product [{}] by id {}", product, id);
        return product;
    }

    @PostMapping(value = "/product/add")
    public ResponseEntity<ProductResult> addProduct(@RequestBody Product product) {
        ProductResult result = new ProductResult();
        result.setProduct(product);
        result.setMsg("success");
        log.info("add product [{}] success", product);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/product/{id}")
    public Mono<ProductResult> updateProduct(@PathVariable long id, @RequestParam String name, @RequestParam String price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(new BigDecimal(price));
        product.setDescription("update product");

        ProductResult result = new ProductResult();
        result.setProduct(product);
        result.setMsg("success");
        log.info("update product [{}] success", product);
        return Mono.just(result);
    }

}
