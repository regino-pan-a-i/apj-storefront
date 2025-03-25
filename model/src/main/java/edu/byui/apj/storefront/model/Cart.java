package edu.byui.apj.storefront.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class Cart {
    String id;
    String personId;
    List<Item> items;
}
