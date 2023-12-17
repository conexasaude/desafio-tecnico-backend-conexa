package br.com.cleonildo.schedulingappoinment.logs.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductLogConstants {
    public static final String LIST_PRODUCT = "List of product returns successfully.";
    public static final String FOUND_PRODUCT_BY_ID = "Product id {} found.";
    public static final String PRODUCT_ID_NOT_FOUND_LOG = "Product with id {} not found!";
    public static final String PRODUCT_UPDATE_SUCCESSFULLY = "Product update successfully.";
    public static final String PRODUCT_SAVED_SUCCESSFULLY = "Product saved successfully.";
    public static final String PRODUCT_DELETED_SUCCESSFULLY = "Product deleted successfully.";

}
