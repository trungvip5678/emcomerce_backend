package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.model.Category;
import com.springboot.ecommercewebsite.model.Product;
import com.springboot.ecommercewebsite.repository.CategoryRepository;
import com.springboot.ecommercewebsite.repository.ProductRepository;
import com.springboot.ecommercewebsite.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl  implements  ProductService{
    private ProductRepository productRepository;
    private UserService userService;

    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,UserService userService,CategoryRepository categoryRepository ){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }
    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
        System.out.println(topLevel + "+++++++++++++++++");
        if(topLevel == null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName((req.getTopLevelCategory()));
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        System.out.println("SECONDLEVEL +++++" + req.getSecondLevelCategory());
        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
        System.out.println(secondLevel + "+++++++++++++++++");

        if(secondLevel == null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        } 

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
        if(thirdLevel == null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setMemories(req.getMemory());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreateAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);


        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getMemories().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productID, Product req) throws ProductException {
        Product product = findProductById(productID);

        if(req.getQuantity() != 0 ){
            product.setQuantity(req.getQuantity());
        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productID) throws ProductException {
        Optional<Product> product = productRepository.findById(productID);
        if(product.isPresent()){
            return product.get();
        }
        throw new ProductException("Product not found with id - " + product);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public org.springframework.data.domain.Page<Product> getAllProduct(String category, List<String> colors, List<String> memories, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);
//        List<Product> products = productRepository.filterProducts();
        System.out.println("KKKKKKKKKKKKK" + products);

        // filter by colors
        if(!colors.isEmpty()){
            products = products.stream().filter(p-> colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList()); // Collect results after filter
        }

        // filter by memories
        if (!memories.isEmpty()) {

            products = products.stream()
                    .filter(p -> p.getMemories().stream()
                            .anyMatch(m -> memories.stream()
                                    .anyMatch(c -> c.equalsIgnoreCase(m.getName()))))
                    .collect(Collectors.toList());

        }

        // filter by stock
        if(stock!= null){
            if( stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }



        // dynamic pagination  with index
        int startIndex = (int)pageable.getOffset(); // get page index current
        System.out.println("PAGE CURRENT " + startIndex);
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

//        List<Product> pageContent = products;
        List<Product> pageContent = products.subList(startIndex,endIndex);

        Page<Product> filteredProducts = new PageImpl<Product>(pageContent,pageable, products.size());

        return filteredProducts;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }


    public org.springframework.data.domain.Page<Product> findProductByName(String nameProduct){
        List<Product> products = productRepository.findByTitleContaining(nameProduct);
        Pageable pageable = PageRequest.ofSize(products.size());
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        int startIndex = (int)pageable.getOffset(); // get page index current

        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

//        List<Product> pageContent = products;
        List<Product> pageContent = products.subList(startIndex,endIndex);

        Page<Product> filteredProducts = new PageImpl<Product>(pageContent,pageable, products.size());

        return filteredProducts;
    }


}
