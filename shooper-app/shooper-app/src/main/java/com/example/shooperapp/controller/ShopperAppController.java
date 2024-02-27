package com.example.shooperapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shooperapp.dto.ResponseStructure;
import com.example.shooperapp.entity.Orders;
import com.example.shooperapp.entity.Product;
import com.example.shooperapp.entity.Review;
import com.example.shooperapp.entity.Users;
import com.example.shooperapp.service.OrderService;
import com.example.shooperapp.service.ProductService;
import com.example.shooperapp.service.ReviewService;
import com.example.shooperapp.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
public class ShopperAppController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private OrderService orderService;
	
	//to save the user in data base by admin
	@Operation(description="To Save User Info",summary="User will be saved In the Database")
	@ApiResponses(value = {@ApiResponse(description="User Created",responseCode="201"),@ApiResponse(description="User not Created",responseCode="404",content=@Content())})
	@PostMapping(produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Users>> saveUser(@RequestBody Users user) {
		return usersService.saveUser(user);
	}
	
	//to save the product in database by merchant
	@Operation(description="To Save the product by merchant",summary="Product will be saved In the Database By Merchant")
	@ApiResponses(value = {@ApiResponse(description="Product Created",responseCode="201"),@ApiResponse(description="User Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@PostMapping(value="/{id}/products",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Product>> saveProduct(@RequestBody Product product,@PathVariable int id){
		return productService.saveProduct(product,id);
	}
	
	//to update the product in the database by merchant
	@Operation(description="To Update the product by merchant",summary="Product will be Updated In the Database By Merchant")
	@ApiResponses(value = {@ApiResponse(description="Success",responseCode="200"),@ApiResponse(description="Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@PatchMapping(value="/{id}/products/{product_id}",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody Product product,@PathVariable int id,@PathVariable int product_id){
		return productService.updateProduct(product,id,product_id);
	}
	
	//to delete the product in the database
	@Operation(description="To Delete the product by merchant",summary="Product will be Deleted In the Database By Merchant")
	@ApiResponses(value = {@ApiResponse(description="Success",responseCode="200"),@ApiResponse(description="Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@DeleteMapping(value="/{id}/products/{product_id}",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Product>> deleteProduct(@PathVariable int id,@PathVariable int product_id){
		return productService.deleteProduct(id,product_id);
	}
	
	//to add a review to the product
	@Operation(description="To Add the Review to product by Customer",summary="Review will be added to product by customer")
	@ApiResponses(value = {@ApiResponse(description="Success",responseCode="200"),@ApiResponse(description="Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@PostMapping(value="/{id}/reviews/{product_id}",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Review>> saveReview(@RequestBody Review review, @PathVariable int id,@PathVariable int product_id){
		return reviewService.saveReview(review,id,product_id);
	}
	
	
	//to see the reviews for the product
	@Operation(description="To get the reviews for a product by merchant",summary="Fetching reviews of a product by merchant")
	@ApiResponses(value = {@ApiResponse(description="Success",responseCode="200"),@ApiResponse(description="Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@GetMapping(value="/{id}/reviews/{product_id}",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<List<Review>>> getReview(@PathVariable int id,@PathVariable int product_id){
		return reviewService.getReviews(id,product_id);
	}
	
	//to delete the review for the product
	@Operation(description="To Delete the Review of product by Merchant",summary="Review will be deleted to product by merchant")
	@ApiResponses(value = {@ApiResponse(description="Success",responseCode="200"),@ApiResponse(description="Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@DeleteMapping(value="/{id}/reviews/{review_id}",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Review>> deleteReview(@PathVariable int id,@PathVariable int review_id){
		return reviewService.deleteReview(id,review_id);
	}
	
	//to create the order by product ids
	@Operation(description="To create the order by customer",summary="Order will be saved in the database by customer")
	@ApiResponses(value = {@ApiResponse(description="Created",responseCode="201"),@ApiResponse(description="Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@PostMapping(value="/{id}/orders",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Orders>> saveOrder(@RequestBody Orders order, @PathVariable int id){
		return orderService.saveOrder(order,id);
	}
	
	//to see the order list for a customer
	@Operation(description="To get the list of orders by the customer",summary="Orders will be fetched by customer")
	@ApiResponses(value = {@ApiResponse(description="Success",responseCode="200"),@ApiResponse(description="Not Found",responseCode="404",content=@Content()),@ApiResponse(description="User Not Authorizable",responseCode="416",content=@Content())})
	@GetMapping(value="/{id}/orders",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<List<Orders>>> getAllOrders(@PathVariable int id){
		return orderService.getAllOrders(id);
	}
	
	
	
	
	
}
