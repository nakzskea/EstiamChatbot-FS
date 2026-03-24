package edu.ban7.e3chatbotback.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.e3chatbotback.dao.ProductDao;
import edu.ban7.e3chatbotback.model.Product;
import edu.ban7.e3chatbotback.security.IsAdmin;
import edu.ban7.e3chatbotback.security.IsUser;
import edu.ban7.e3chatbotback.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    protected ProductDao productDao;

    @GetMapping("/product/list")
    @JsonView(ProductView.class)
    @IsUser
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productDao.findAll(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    @JsonView(ProductView.class)
    @IsUser
    public ResponseEntity<Product> get(@PathVariable int id) {
        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/product")
    @JsonView(ProductView.class)
    @IsAdmin
    public ResponseEntity<Product> create(@RequestBody Product product) {
        productDao.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Product productToBeUpdated) {
        productToBeUpdated.setId(id);

        Optional<Product> optionalProductDb = productDao.findById(id);

        if (optionalProductDb.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDao.save(productToBeUpdated);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
