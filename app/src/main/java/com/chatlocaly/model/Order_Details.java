package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashok on 17/4/18.
 */

public class Order_Details {

    /**
     * data : {"order_detail":{"order_id":"49","order_token":"PPJZJJQUN7K9TAN","b_user_id":"64","b_id":"35","c_user_id":"24","buyer_mobile":"4842484348","buyer_name":"rohan mehra","address_1":"new delhi","address_2":"","city":"delhi","pincode":"","bill_date":"January 1, 1970","bill_due_date":"January 1, 1970","sub_total":"552.50","total":"552.50","order_status":"unpaid","show_rounding_off":"0","use_icon":"0","use_location":"0","show_bill_date":"0","show_bill_due_date":"0","create_date":"2018-04-13 12:33:48","b_full_name":"vijay","b_cm_code":"91","b_mobile_number":"9422222222","business_name":"shiv Shakti bakery","business_logo":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-35/20180412070608ld4nsnmlzi.png","c_full_name":"ram ","c_cm_code":"91","c_mobile_number":"9090909099","products":[{"productorder_id":"49","order_id":"49","product_id":"42","product_name":"Mouse","product_description":"","product_qty":"1","product_unit":"lb.","product_price":"650.00","discount":"0","discount_price":"-97.50","tax_incusive":"0","cgst":"0","cgst_price":"0.00","sgst":"0","sgst_price":"0.00","total_price":"552.50"}],"send_orders":[{"send_order_id":"7","order_id":"49","b_user_id":"64","b_id":"35","c_user_id":"24","send_date":"2018-04-13 12:34:14","b_full_name":"vijay","send_by":"4 days ago By vijay"}]},"message":"Order detail successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata data;

    public Datadata getData() {
        return data;
    }

    public void setData(Datadata data) {
        this.data = data;
    }

    public static class Datadata {
        /**
         * order_detail : {"order_id":"49","order_token":"PPJZJJQUN7K9TAN","b_user_id":"64","b_id":"35","c_user_id":"24","buyer_mobile":"4842484348","buyer_name":"rohan mehra","address_1":"new delhi","address_2":"","city":"delhi","pincode":"","bill_date":"January 1, 1970","bill_due_date":"January 1, 1970","sub_total":"552.50","total":"552.50","order_status":"unpaid","show_rounding_off":"0","use_icon":"0","use_location":"0","show_bill_date":"0","show_bill_due_date":"0","create_date":"2018-04-13 12:33:48","b_full_name":"vijay","b_cm_code":"91","b_mobile_number":"9422222222","business_name":"shiv Shakti bakery","business_logo":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-35/20180412070608ld4nsnmlzi.png","c_full_name":"ram ","c_cm_code":"91","c_mobile_number":"9090909099","products":[{"productorder_id":"49","order_id":"49","product_id":"42","product_name":"Mouse","product_description":"","product_qty":"1","product_unit":"lb.","product_price":"650.00","discount":"0","discount_price":"-97.50","tax_incusive":"0","cgst":"0","cgst_price":"0.00","sgst":"0","sgst_price":"0.00","total_price":"552.50"}],"send_orders":[{"send_order_id":"7","order_id":"49","b_user_id":"64","b_id":"35","c_user_id":"24","send_date":"2018-04-13 12:34:14","b_full_name":"vijay","send_by":"4 days ago By vijay"}]}
         * message : Order detail successfully got.
         * resultCode : 1
         */

        @SerializedName("order_detail")
        private InvoiceOrderListModel.OrderList orderDetail;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;

        public InvoiceOrderListModel.OrderList getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(InvoiceOrderListModel.OrderList orderDetail) {
            this.orderDetail = orderDetail;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }


    }
}
