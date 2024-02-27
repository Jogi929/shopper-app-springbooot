package com.example.shooperapp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.shooperapp.configuraton.ProductRepositroy;
import com.example.shooperapp.entity.Orders;
import com.example.shooperapp.entity.Product;
import com.example.shooperapp.exception.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class OrdersDeserializer extends StdDeserializer<Orders> {
	
	
	private ProductRepositroy productRepository;

	public OrdersDeserializer(ProductRepositroy productRepository) {
		super(Orders.class);
		this.productRepository = productRepository;
	}

	@Override
	public Orders deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectCodec codec = jp.getCodec();
		JsonNode node = codec.readTree(jp);

		Orders order = new Orders();
		// Deserialize other fields
		order.setOrder_status(node.get("order_status").asText());
		order.setDescription(node.get("description").asText());
		// Deserialize other fields as needed

		// Deserialize products from product IDs
		List<Product> products = new ArrayList<>();
		JsonNode productIdsNode = node.get("products");
		if (productIdsNode != null && productIdsNode.isArray()) {
			for (JsonNode productIdNode : productIdsNode) {
				Integer productId = productIdNode.asInt();
				Product product = productRepository.findById(productId)
						.orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
				products.add(product);
			}
		}
		order.setProducts(products);

		return order;
	}

}
