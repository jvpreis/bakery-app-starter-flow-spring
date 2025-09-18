package com.vaadin.starter.bakery.backend.data;

import java.util.LinkedHashMap;
import java.util.List;

import com.vaadin.starter.bakery.backend.data.entity.Product;

/**
 * Represents the dashboard data for the bakery application.
 * <p>
 * This class holds various statistics and metrics used in the dashboard view,
 * such as delivery statistics, monthly and yearly deliveries, sales per month,
 * and product deliveries.
 * </p>
 */
public class DashboardData {
    /** Delivery statistics for the dashboard. */
    private DeliveryStats deliveryStats;
    /** List of deliveries made this month. */
    private List<Number> deliveriesThisMonth;
    /** List of deliveries made this year. */
    private List<Number> deliveriesThisYear;
    /** Sales data per month, organized as a 2D array. */
    private Number[][] salesPerMonth;
    /** Map of products to the number of deliveries for each product. */
    private LinkedHashMap<Product, Integer> productDeliveries;

    /**
     * Gets the delivery statistics.
     * @return the delivery statistics
     */
    public DeliveryStats getDeliveryStats() {
        return deliveryStats;
    }

    /**
     * Sets the delivery statistics.
     * @param deliveryStats the delivery statistics to set
     */
    public void setDeliveryStats(DeliveryStats deliveryStats) {
        this.deliveryStats = deliveryStats;
    }

    /**
     * Gets the list of deliveries made this month.
     * @return the deliveries this month
     */
    public List<Number> getDeliveriesThisMonth() {
        return deliveriesThisMonth;
    }

    /**
     * Sets the list of deliveries made this month.
     * @param deliveriesThisMonth the deliveries this month to set
     */
    public void setDeliveriesThisMonth(List<Number> deliveriesThisMonth) {
        this.deliveriesThisMonth = deliveriesThisMonth;
    }

    /**
     * Gets the list of deliveries made this year.
     * @return the deliveries this year
     */
    public List<Number> getDeliveriesThisYear() {
        return deliveriesThisYear;
    }

    /**
     * Sets the list of deliveries made this year.
     * @param deliveriesThisYear the deliveries this year to set
     */
    public void setDeliveriesThisYear(List<Number> deliveriesThisYear) {
        this.deliveriesThisYear = deliveriesThisYear;
    }

    /**
     * Sets the sales data per month.
     * @param salesPerMonth the sales data per month to set
     */
    public void setSalesPerMonth(Number[][] salesPerMonth) {
        this.salesPerMonth = salesPerMonth;
    }

    /**
     * Gets the sales data for a specific month.
     * @param i the month index
     * @return the sales data for the specified month
     */
    public Number[] getSalesPerMonth(int i) {
        return salesPerMonth[i];
    }

    /**
     * Gets the map of product deliveries.
     * @return the product deliveries map
     */
    public LinkedHashMap<Product, Integer> getProductDeliveries() {
        return productDeliveries;
    }

    /**
     * Sets the map of product deliveries.
     * @param productDeliveries the product deliveries map to set
     */
    public void setProductDeliveries(LinkedHashMap<Product, Integer> productDeliveries) {
        this.productDeliveries = productDeliveries;
    }

}
