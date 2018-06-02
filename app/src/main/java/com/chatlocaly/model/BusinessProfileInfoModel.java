package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ashok on 7/4/18.
 */

public class BusinessProfileInfoModel implements Serializable {


    /**
     * data : {"business_detail":{"b_id":"34","business_name":"A2Z   kirana store","home_services":"YES","services_km":"25.00","country":"India","address":"Patel marg jaipur","landmark":"sgsushsisgs","locality_name":"Uttam Nagar","city_name":"Delhi/NCR","b_cm_code":"91","b_mobile_number":"9411111111","business_banner":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png","business_logo":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png","business_images":[{"bim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png"}],"rating_count":0,"avg_rating":"0.0","ratings":{"one_star":0,"two_star":0,"three_star":0,"four_star":0,"five_star":0},"products":[{"product_id":"31","b_id":"34","product_name":"Mouse","description":"this mouse is a product of Intel. Be the first to use it and experience the joy","price":"120.00","discount":"2","sku":"","product_status":"1","status":"1","brand_name":"Intel","brand_id":"24","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png"},{"pim_id":"164","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828zgmmcugr2x.png"}]},{"product_id":"32","b_id":"34","product_name":"Moniter","description":"Shop Dell monitor from compuindia.com a Dell express shipping affiliate shopping store in India . Buy a wife selection of flat screen and LCD computer monitors that increase efficiency, performance and style.","price":"25000.00","discount":"0","sku":"167","product_status":"1","status":"1","brand_name":"Dell","brand_id":"25","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407114720mrpb60nt1k.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407114720mrpb60nt1k.png"},{"pim_id":"165","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071147202yj0kgru0x.png"}]},{"product_id":"33","b_id":"34","product_name":"Alloy Wheels","description":"Aluminium Wheels are made from High strength Alloy, produced from world renowned suppliera. \n\nRotary Forged and spun for high strength from a single piece alloy billet. Subjected to special heat treatment gives Elongation.","price":"1200.00","discount":"5","sku":"","product_status":"1","status":"1","brand_name":"Wheels India Limited","brand_id":"26","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407115708mk5euejgo1.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407115708mk5euejgo1.png"},{"pim_id":"166","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071157087het82u8pn.png"}]},{"product_id":"34","b_id":"34","product_name":"Pulser","description":"A pulsar is a highly magnetized rotating neutron star or a white the beam of emissions is pointing toward Earth and is responsible for the pulaed appearance of emission. Neutron star are very dense and have short periods. This produces a very precise interval between pulses that range from milliseconds to microseconds. \n\nThe precise periods of pulsars make them vary useful tools.","price":"65000.00","discount":"0","sku":"","product_status":"1","status":"1","brand_name":"Bajaj","brand_id":"27","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228ebp10j86bk.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228ebp10j86bk.png"},{"pim_id":"167","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228bs8zj4bhou.png"}]},{"product_id":"35","b_id":"34","product_name":"Router","description":"Have a router and find the route of your life","price":"650.00","discount":"0","sku":"","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071237466ursxb52ij.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071237466ursxb52ij.png"},{"pim_id":"168","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407123746f14qxsp6oq.png"}]},{"product_id":"36","b_id":"34","product_name":"Pen Drive","description":"let's use pen and drive the car","price":"250.00","discount":"4","sku":"","product_status":"0","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hewxwx5rs4.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hewxwx5rs4.png"},{"pim_id":"169","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hjd7npeluk.png"}]},{"product_id":"37","b_id":"34","product_name":"Keyboard","description":"let's use keyboard","price":"900.00","discount":"5","sku":"ghh","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411oxn9rj4sey.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411oxn9rj4sey.png"},{"pim_id":"170","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070114111khsyil0w4.png"},{"pim_id":"171","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411qio9fca052.png"}]},{"product_id":"38","b_id":"34","product_name":"image1","description":"ok cool","price":"500.00","discount":"5","sku":"6785","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012840b1zsn0p2qr.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012840b1zsn0p2qr.png"},{"pim_id":"174","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070128418jffoy3y8z.png"},{"pim_id":"175","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841tyzkqrczhm.png"},{"pim_id":"176","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841nllsn5vxo9.png"},{"pim_id":"177","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841npg8vzm8dv.png"}]},{"product_id":"39","b_id":"34","product_name":"wi fi","description":"ggh","price":"41.00","discount":"10","sku":"gh","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013548hutkzsvs4t.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013548hutkzsvs4t.png"},{"pim_id":"178","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013549aawxsbh2jh.png"},{"pim_id":"179","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070135490t0r02m04q.png"}]},{"product_id":"40","b_id":"34","product_name":"Wse","description":"gfggg","price":"100.00","discount":"10","sku":"ff","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502s0mue5h7v0.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502s0mue5h7v0.png"},{"pim_id":"180","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502iczmj4pnop.png"},{"pim_id":"181","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502ol6l2am6hd.png"}]},{"product_id":"41","b_id":"34","product_name":"5 image","description":"5 images","price":"15000.00","discount":"6","sku":"","product_status":"0","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210472b2pkxcp0x.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210472b2pkxcp0x.png"},{"pim_id":"182","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048fsu4claz4o.png"},{"pim_id":"183","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048g2s143xc9o.png"},{"pim_id":"184","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210489xtdw8zyrz.png"},{"pim_id":"185","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048owhevuxkvf.png"},{"pim_id":"186","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048qrpwg16qtt.png"}]}],"services":[{"service_id":"1","service_name":"Pulsar","description":"let's ride the bike","service_price":"60000.00","service_label":"let's ride","discount":"10","service_status":"1","status":"APPROVED","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png"},{"sim_id":"1","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215491eyy82sfpk.png"},{"sim_id":"2","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549jazeqcy2t5.png"},{"sim_id":"3","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549da9gh7gwf9.png"},{"sim_id":"4","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549t7b07ne53e.png"}],"service_brands":[{"brand_id":"1","brand_name":"Patanjali"},{"brand_id":"27","brand_name":"Bajaj"}]}]},"message":"Business detail successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata data;

    public Datadata getData() {
        return data;
    }

    public void setData(Datadata data) {
        this.data = data;
    }

    public static class Datadata implements Serializable {
        /**
         * business_detail : {"b_id":"34","business_name":"A2Z   kirana store","home_services":"YES","services_km":"25.00","country":"India","address":"Patel marg jaipur","landmark":"sgsushsisgs","locality_name":"Uttam Nagar","city_name":"Delhi/NCR","b_cm_code":"91","b_mobile_number":"9411111111","business_banner":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png","business_logo":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png","business_images":[{"bim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png"}],"rating_count":0,"avg_rating":"0.0","ratings":{"one_star":0,"two_star":0,"three_star":0,"four_star":0,"five_star":0},"products":[{"product_id":"31","b_id":"34","product_name":"Mouse","description":"this mouse is a product of Intel. Be the first to use it and experience the joy","price":"120.00","discount":"2","sku":"","product_status":"1","status":"1","brand_name":"Intel","brand_id":"24","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png"},{"pim_id":"164","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828zgmmcugr2x.png"}]},{"product_id":"32","b_id":"34","product_name":"Moniter","description":"Shop Dell monitor from compuindia.com a Dell express shipping affiliate shopping store in India . Buy a wife selection of flat screen and LCD computer monitors that increase efficiency, performance and style.","price":"25000.00","discount":"0","sku":"167","product_status":"1","status":"1","brand_name":"Dell","brand_id":"25","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407114720mrpb60nt1k.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407114720mrpb60nt1k.png"},{"pim_id":"165","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071147202yj0kgru0x.png"}]},{"product_id":"33","b_id":"34","product_name":"Alloy Wheels","description":"Aluminium Wheels are made from High strength Alloy, produced from world renowned suppliera. \n\nRotary Forged and spun for high strength from a single piece alloy billet. Subjected to special heat treatment gives Elongation.","price":"1200.00","discount":"5","sku":"","product_status":"1","status":"1","brand_name":"Wheels India Limited","brand_id":"26","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407115708mk5euejgo1.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407115708mk5euejgo1.png"},{"pim_id":"166","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071157087het82u8pn.png"}]},{"product_id":"34","b_id":"34","product_name":"Pulser","description":"A pulsar is a highly magnetized rotating neutron star or a white the beam of emissions is pointing toward Earth and is responsible for the pulaed appearance of emission. Neutron star are very dense and have short periods. This produces a very precise interval between pulses that range from milliseconds to microseconds. \n\nThe precise periods of pulsars make them vary useful tools.","price":"65000.00","discount":"0","sku":"","product_status":"1","status":"1","brand_name":"Bajaj","brand_id":"27","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228ebp10j86bk.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228ebp10j86bk.png"},{"pim_id":"167","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228bs8zj4bhou.png"}]},{"product_id":"35","b_id":"34","product_name":"Router","description":"Have a router and find the route of your life","price":"650.00","discount":"0","sku":"","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071237466ursxb52ij.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071237466ursxb52ij.png"},{"pim_id":"168","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407123746f14qxsp6oq.png"}]},{"product_id":"36","b_id":"34","product_name":"Pen Drive","description":"let's use pen and drive the car","price":"250.00","discount":"4","sku":"","product_status":"0","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hewxwx5rs4.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hewxwx5rs4.png"},{"pim_id":"169","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hjd7npeluk.png"}]},{"product_id":"37","b_id":"34","product_name":"Keyboard","description":"let's use keyboard","price":"900.00","discount":"5","sku":"ghh","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411oxn9rj4sey.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411oxn9rj4sey.png"},{"pim_id":"170","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070114111khsyil0w4.png"},{"pim_id":"171","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411qio9fca052.png"}]},{"product_id":"38","b_id":"34","product_name":"image1","description":"ok cool","price":"500.00","discount":"5","sku":"6785","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012840b1zsn0p2qr.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012840b1zsn0p2qr.png"},{"pim_id":"174","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070128418jffoy3y8z.png"},{"pim_id":"175","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841tyzkqrczhm.png"},{"pim_id":"176","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841nllsn5vxo9.png"},{"pim_id":"177","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841npg8vzm8dv.png"}]},{"product_id":"39","b_id":"34","product_name":"wi fi","description":"ggh","price":"41.00","discount":"10","sku":"gh","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013548hutkzsvs4t.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013548hutkzsvs4t.png"},{"pim_id":"178","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013549aawxsbh2jh.png"},{"pim_id":"179","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070135490t0r02m04q.png"}]},{"product_id":"40","b_id":"34","product_name":"Wse","description":"gfggg","price":"100.00","discount":"10","sku":"ff","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502s0mue5h7v0.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502s0mue5h7v0.png"},{"pim_id":"180","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502iczmj4pnop.png"},{"pim_id":"181","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502ol6l2am6hd.png"}]},{"product_id":"41","b_id":"34","product_name":"5 image","description":"5 images","price":"15000.00","discount":"6","sku":"","product_status":"0","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210472b2pkxcp0x.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210472b2pkxcp0x.png"},{"pim_id":"182","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048fsu4claz4o.png"},{"pim_id":"183","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048g2s143xc9o.png"},{"pim_id":"184","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210489xtdw8zyrz.png"},{"pim_id":"185","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048owhevuxkvf.png"},{"pim_id":"186","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048qrpwg16qtt.png"}]}],"services":[{"service_id":"1","service_name":"Pulsar","description":"let's ride the bike","service_price":"60000.00","service_label":"let's ride","discount":"10","service_status":"1","status":"APPROVED","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png"},{"sim_id":"1","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215491eyy82sfpk.png"},{"sim_id":"2","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549jazeqcy2t5.png"},{"sim_id":"3","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549da9gh7gwf9.png"},{"sim_id":"4","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549t7b07ne53e.png"}],"service_brands":[{"brand_id":"1","brand_name":"Patanjali"},{"brand_id":"27","brand_name":"Bajaj"}]}]}
         * message : Business detail successfully got.
         * resultCode : 1
         */

        @SerializedName("business_detail")
        private BusinessDetaildata businessDetail;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;

        public BusinessDetaildata getBusinessDetail() {
            return businessDetail;
        }

        public void setBusinessDetail(BusinessDetaildata businessDetail) {
            this.businessDetail = businessDetail;
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

        public static class BusinessDetaildata implements Serializable {
            /**
             * b_id : 34
             * business_name : A2Z   kirana store
             * home_services : YES
             * services_km : 25.00
             * country : India
             * address : Patel marg jaipur
             * landmark : sgsushsisgs
             * locality_name : Uttam Nagar
             * city_name : Delhi/NCR
             * b_cm_code : 91
             * b_mobile_number : 9411111111
             * business_banner : http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png
             * business_logo : http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png
             * business_images : [{"bim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png"}]
             * rating_count : 0
             * avg_rating : 0.0
             * ratings : {"one_star":0,"two_star":0,"three_star":0,"four_star":0,"five_star":0}
             * products : [{"product_id":"31","b_id":"34","product_name":"Mouse","description":"this mouse is a product of Intel. Be the first to use it and experience the joy","price":"120.00","discount":"2","sku":"","product_status":"1","status":"1","brand_name":"Intel","brand_id":"24","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png"},{"pim_id":"164","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828zgmmcugr2x.png"}]},{"product_id":"32","b_id":"34","product_name":"Moniter","description":"Shop Dell monitor from compuindia.com a Dell express shipping affiliate shopping store in India . Buy a wife selection of flat screen and LCD computer monitors that increase efficiency, performance and style.","price":"25000.00","discount":"0","sku":"167","product_status":"1","status":"1","brand_name":"Dell","brand_id":"25","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407114720mrpb60nt1k.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407114720mrpb60nt1k.png"},{"pim_id":"165","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071147202yj0kgru0x.png"}]},{"product_id":"33","b_id":"34","product_name":"Alloy Wheels","description":"Aluminium Wheels are made from High strength Alloy, produced from world renowned suppliera. \n\nRotary Forged and spun for high strength from a single piece alloy billet. Subjected to special heat treatment gives Elongation.","price":"1200.00","discount":"5","sku":"","product_status":"1","status":"1","brand_name":"Wheels India Limited","brand_id":"26","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407115708mk5euejgo1.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407115708mk5euejgo1.png"},{"pim_id":"166","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071157087het82u8pn.png"}]},{"product_id":"34","b_id":"34","product_name":"Pulser","description":"A pulsar is a highly magnetized rotating neutron star or a white the beam of emissions is pointing toward Earth and is responsible for the pulaed appearance of emission. Neutron star are very dense and have short periods. This produces a very precise interval between pulses that range from milliseconds to microseconds. \n\nThe precise periods of pulsars make them vary useful tools.","price":"65000.00","discount":"0","sku":"","product_status":"1","status":"1","brand_name":"Bajaj","brand_id":"27","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228ebp10j86bk.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228ebp10j86bk.png"},{"pim_id":"167","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407121228bs8zj4bhou.png"}]},{"product_id":"35","b_id":"34","product_name":"Router","description":"Have a router and find the route of your life","price":"650.00","discount":"0","sku":"","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071237466ursxb52ij.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804071237466ursxb52ij.png"},{"pim_id":"168","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407123746f14qxsp6oq.png"}]},{"product_id":"36","b_id":"34","product_name":"Pen Drive","description":"let's use pen and drive the car","price":"250.00","discount":"4","sku":"","product_status":"0","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hewxwx5rs4.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hewxwx5rs4.png"},{"pim_id":"169","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407125423hjd7npeluk.png"}]},{"product_id":"37","b_id":"34","product_name":"Keyboard","description":"let's use keyboard","price":"900.00","discount":"5","sku":"ghh","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411oxn9rj4sey.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411oxn9rj4sey.png"},{"pim_id":"170","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070114111khsyil0w4.png"},{"pim_id":"171","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407011411qio9fca052.png"}]},{"product_id":"38","b_id":"34","product_name":"image1","description":"ok cool","price":"500.00","discount":"5","sku":"6785","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012840b1zsn0p2qr.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012840b1zsn0p2qr.png"},{"pim_id":"174","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070128418jffoy3y8z.png"},{"pim_id":"175","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841tyzkqrczhm.png"},{"pim_id":"176","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841nllsn5vxo9.png"},{"pim_id":"177","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407012841npg8vzm8dv.png"}]},{"product_id":"39","b_id":"34","product_name":"wi fi","description":"ggh","price":"41.00","discount":"10","sku":"gh","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013548hutkzsvs4t.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013548hutkzsvs4t.png"},{"pim_id":"178","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407013549aawxsbh2jh.png"},{"pim_id":"179","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070135490t0r02m04q.png"}]},{"product_id":"40","b_id":"34","product_name":"Wse","description":"gfggg","price":"100.00","discount":"10","sku":"ff","product_status":"1","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502s0mue5h7v0.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502s0mue5h7v0.png"},{"pim_id":"180","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502iczmj4pnop.png"},{"pim_id":"181","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407020502ol6l2am6hd.png"}]},{"product_id":"41","b_id":"34","product_name":"5 image","description":"5 images","price":"15000.00","discount":"6","sku":"","product_status":"0","status":null,"brand_name":"unbranded","brand_id":"0","product_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210472b2pkxcp0x.png","product_images":[{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210472b2pkxcp0x.png"},{"pim_id":"182","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048fsu4claz4o.png"},{"pim_id":"183","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048g2s143xc9o.png"},{"pim_id":"184","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070210489xtdw8zyrz.png"},{"pim_id":"185","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048owhevuxkvf.png"},{"pim_id":"186","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021048qrpwg16qtt.png"}]}]
             * services : [{"service_id":"1","service_name":"Pulsar","description":"let's ride the bike","service_price":"60000.00","service_label":"let's ride","discount":"10","service_status":"1","status":"APPROVED","service_image":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png","service_images":[{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png"},{"sim_id":"1","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215491eyy82sfpk.png"},{"sim_id":"2","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549jazeqcy2t5.png"},{"sim_id":"3","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549da9gh7gwf9.png"},{"sim_id":"4","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549t7b07ne53e.png"}],"service_brands":[{"brand_id":"1","brand_name":"Patanjali"},{"brand_id":"27","brand_name":"Bajaj"}]}]
             */

            @SerializedName("b_id")
            private String bId;
            @SerializedName("business_name")
            private String businessName;
            @SerializedName("home_services")
            private String homeServices;
            @SerializedName("services_km")
            private String servicesKm;
            @SerializedName("country")
            private String country;
            @SerializedName("address")
            private String address;
            @SerializedName("landmark")
            private String landmark;
            @SerializedName("locality_name")
            private String localityName;
            @SerializedName("city_name")
            private String cityName;
            @SerializedName("b_cm_code")
            private String bCmCode;
            @SerializedName("b_mobile_number")
            private String bMobileNumber;
            @SerializedName("business_banner")
            private String businessBanner;
            @SerializedName("business_logo")
            private String businessLogo;
            @SerializedName("rating_count")
            private int ratingCount;
            @SerializedName("avg_rating")
            private String avgRating;
            @SerializedName("ratings")
            private Ratingsdata ratings;
            @SerializedName("business_images")
            private List<BusinessImagesdata> businessImages;
            @SerializedName("products")
            private List<ProductListModel.productdata.ProductListdata> products;
            @SerializedName("services")
            private List<ServiceModel.Datadata.ServiceListdata> services;


            @SerializedName("customer_notification")
            private String customer_notification;
            @SerializedName("customer_tag")
            private String customer_tag;

            @SerializedName("report_abuse_status")
            private String report_abuse_status;



            @SerializedName("blocked_by")
            private String blocked_by;
            @SerializedName("blocked_whom")
            private String blocked_whom;

            @SerializedName("block_side")
            private String block_side;
            @SerializedName("is_blocked")
            private boolean is_blocked;


            public String getBlocked_by() {
                return blocked_by;
            }

            public void setBlocked_by(String blocked_by) {
                this.blocked_by = blocked_by;
            }

            public String getBlocked_whom() {
                return blocked_whom;
            }

            public void setBlocked_whom(String blocked_whom) {
                this.blocked_whom = blocked_whom;
            }

            public String getBlock_side() {
                return block_side;
            }

            public void setBlock_side(String block_side) {
                this.block_side = block_side;
            }

            public boolean isIs_blocked() {
                return is_blocked;
            }

            public void setIs_blocked(boolean is_blocked) {
                this.is_blocked = is_blocked;
            }

            public String getReport_abuse_status() {
                return report_abuse_status;
            }

            public void setReport_abuse_status(String report_abuse_status) {
                this.report_abuse_status = report_abuse_status;
            }




            public String getCustomer_notification() {
                return customer_notification;
            }

            public void setCustomer_notification(String customer_notification) {
                this.customer_notification = customer_notification;
            }

            public String getCustomer_tag() {
                return customer_tag;
            }

            public void setCustomer_tag(String customer_tag) {
                this.customer_tag = customer_tag;
            }

            public String getBId() {
                return bId;
            }

            public void setBId(String bId) {
                this.bId = bId;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getHomeServices() {
                return homeServices;
            }

            public void setHomeServices(String homeServices) {
                this.homeServices = homeServices;
            }

            public String getServicesKm() {
                return servicesKm;
            }

            public void setServicesKm(String servicesKm) {
                this.servicesKm = servicesKm;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLandmark() {
                return landmark;
            }

            public void setLandmark(String landmark) {
                this.landmark = landmark;
            }

            public String getLocalityName() {
                return localityName;
            }

            public void setLocalityName(String localityName) {
                this.localityName = localityName;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getBCmCode() {
                return bCmCode;
            }

            public void setBCmCode(String bCmCode) {
                this.bCmCode = bCmCode;
            }

            public String getBMobileNumber() {
                return bMobileNumber;
            }

            public void setBMobileNumber(String bMobileNumber) {
                this.bMobileNumber = bMobileNumber;
            }

            public String getBusinessBanner() {
                return businessBanner;
            }

            public void setBusinessBanner(String businessBanner) {
                this.businessBanner = businessBanner;
            }

            public String getBusinessLogo() {
                return businessLogo;
            }

            public void setBusinessLogo(String businessLogo) {
                this.businessLogo = businessLogo;
            }

            public int getRatingCount() {
                return ratingCount;
            }

            public void setRatingCount(int ratingCount) {
                this.ratingCount = ratingCount;
            }

            public String getAvgRating() {
                return avgRating;
            }

            public void setAvgRating(String avgRating) {
                this.avgRating = avgRating;
            }

            public Ratingsdata getRatings() {
                return ratings;
            }

            public void setRatings(Ratingsdata ratings) {
                this.ratings = ratings;
            }

            public List<BusinessImagesdata> getBusinessImages() {
                return businessImages;
            }

            public void setBusinessImages(List<BusinessImagesdata> businessImages) {
                this.businessImages = businessImages;
            }

            public List<ProductListModel.productdata.ProductListdata> getProducts() {
                return products;
            }

            public void setProducts(List<ProductListModel.productdata.ProductListdata> products) {
                this.products = products;
            }

            public List<ServiceModel.Datadata.ServiceListdata> getServices() {
                return services;
            }

            public void setServices(List<ServiceModel.Datadata.ServiceListdata> services) {
                this.services = services;
            }

            public static class Ratingsdata implements Serializable {
                /**
                 * one_star : 0
                 * two_star : 0
                 * three_star : 0
                 * four_star : 0
                 * five_star : 0
                 */

                @SerializedName("one_star")
                private int oneStar;
                @SerializedName("two_star")
                private int twoStar;
                @SerializedName("three_star")
                private int threeStar;
                @SerializedName("four_star")
                private int fourStar;
                @SerializedName("five_star")
                private int fiveStar;

                public int getOneStar() {
                    return oneStar;
                }

                public void setOneStar(int oneStar) {
                    this.oneStar = oneStar;
                }

                public int getTwoStar() {
                    return twoStar;
                }

                public void setTwoStar(int twoStar) {
                    this.twoStar = twoStar;
                }

                public int getThreeStar() {
                    return threeStar;
                }

                public void setThreeStar(int threeStar) {
                    this.threeStar = threeStar;
                }

                public int getFourStar() {
                    return fourStar;
                }

                public void setFourStar(int fourStar) {
                    this.fourStar = fourStar;
                }

                public int getFiveStar() {
                    return fiveStar;
                }

                public void setFiveStar(int fiveStar) {
                    this.fiveStar = fiveStar;
                }
            }

            public static class BusinessImagesdata implements Serializable {
                /**
                 * bim_id : 0
                 * image_name : http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png
                 */

                @SerializedName("bim_id")
                private String bimId;
                @SerializedName("image_name")
                private String imageName;

                public String getBimId() {
                    return bimId;
                }

                public void setBimId(String bimId) {
                    this.bimId = bimId;
                }

                public String getImageName() {
                    return imageName;
                }

                public void setImageName(String imageName) {
                    this.imageName = imageName;
                }
            }

            public static class Productsdata implements Serializable {
                /**
                 * product_id : 31
                 * b_id : 34
                 * product_name : Mouse
                 * description : this mouse is a product of Intel. Be the first to use it and experience the joy
                 * price : 120.00
                 * discount : 2
                 * sku :
                 * product_status : 1
                 * status : 1
                 * brand_name : Intel
                 * brand_id : 24
                 * product_image : http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png
                 * product_images : [{"pim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png"},{"pim_id":"164","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828zgmmcugr2x.png"}]
                 */

                @SerializedName("product_id")
                private String productId;
                @SerializedName("b_id")
                private String bId;
                @SerializedName("product_name")
                private String productName;
                @SerializedName("description")
                private String description;
                @SerializedName("price")
                private String price;
                @SerializedName("discount")
                private String discount;
                @SerializedName("sku")
                private String sku;
                @SerializedName("product_status")
                private String productStatus;
                @SerializedName("status")
                private String status;
                @SerializedName("brand_name")
                private String brandName;
                @SerializedName("brand_id")
                private String brandId;
                @SerializedName("product_image")
                private String productImage;
                @SerializedName("product_images")
                private List<ProductImagesdata> productImages;

                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public String getBId() {
                    return bId;
                }

                public void setBId(String bId) {
                    this.bId = bId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getProductStatus() {
                    return productStatus;
                }

                public void setProductStatus(String productStatus) {
                    this.productStatus = productStatus;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getBrandName() {
                    return brandName;
                }

                public void setBrandName(String brandName) {
                    this.brandName = brandName;
                }

                public String getBrandId() {
                    return brandId;
                }

                public void setBrandId(String brandId) {
                    this.brandId = brandId;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public List<ProductImagesdata> getProductImages() {
                    return productImages;
                }

                public void setProductImages(List<ProductImagesdata> productImages) {
                    this.productImages = productImages;
                }

                public static class ProductImagesdata implements Serializable {
                    /**
                     * pim_id : 0
                     * image_name : http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407113828g28ouayk8z.png
                     */

                    @SerializedName("pim_id")
                    private String pimId;
                    @SerializedName("image_name")
                    private String imageName;

                    public String getPimId() {
                        return pimId;
                    }

                    public void setPimId(String pimId) {
                        this.pimId = pimId;
                    }

                    public String getImageName() {
                        return imageName;
                    }

                    public void setImageName(String imageName) {
                        this.imageName = imageName;
                    }
                }
            }

            public static class Servicesdata implements Serializable {
                /**
                 * service_id : 1
                 * service_name : Pulsar
                 * description : let's ride the bike
                 * service_price : 60000.00
                 * service_label : let's ride
                 * discount : 10
                 * service_status : 1
                 * status : APPROVED
                 * service_image : http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png
                 * service_images : [{"sim_id":"0","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png"},{"sim_id":"1","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215491eyy82sfpk.png"},{"sim_id":"2","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549jazeqcy2t5.png"},{"sim_id":"3","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549da9gh7gwf9.png"},{"sim_id":"4","image_name":"http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/20180407021549t7b07ne53e.png"}]
                 * service_brands : [{"brand_id":"1","brand_name":"Patanjali"},{"brand_id":"27","brand_name":"Bajaj"}]
                 */

                @SerializedName("service_id")
                private String serviceId;
                @SerializedName("service_name")
                private String serviceName;
                @SerializedName("description")
                private String description;
                @SerializedName("service_price")
                private String servicePrice;
                @SerializedName("service_label")
                private String serviceLabel;
                @SerializedName("discount")
                private String discount;
                @SerializedName("service_status")
                private String serviceStatus;
                @SerializedName("status")
                private String status;
                @SerializedName("service_image")
                private String serviceImage;
                @SerializedName("service_images")
                private List<ServiceImagesdata> serviceImages;
                @SerializedName("service_brands")
                private List<ServiceBrandsdata> serviceBrands;

                public String getServiceId() {
                    return serviceId;
                }

                public void setServiceId(String serviceId) {
                    this.serviceId = serviceId;
                }

                public String getServiceName() {
                    return serviceName;
                }

                public void setServiceName(String serviceName) {
                    this.serviceName = serviceName;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getServicePrice() {
                    return servicePrice;
                }

                public void setServicePrice(String servicePrice) {
                    this.servicePrice = servicePrice;
                }

                public String getServiceLabel() {
                    return serviceLabel;
                }

                public void setServiceLabel(String serviceLabel) {
                    this.serviceLabel = serviceLabel;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public String getServiceStatus() {
                    return serviceStatus;
                }

                public void setServiceStatus(String serviceStatus) {
                    this.serviceStatus = serviceStatus;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getServiceImage() {
                    return serviceImage;
                }

                public void setServiceImage(String serviceImage) {
                    this.serviceImage = serviceImage;
                }

                public List<ServiceImagesdata> getServiceImages() {
                    return serviceImages;
                }

                public void setServiceImages(List<ServiceImagesdata> serviceImages) {
                    this.serviceImages = serviceImages;
                }

                public List<ServiceBrandsdata> getServiceBrands() {
                    return serviceBrands;
                }

                public void setServiceBrands(List<ServiceBrandsdata> serviceBrands) {
                    this.serviceBrands = serviceBrands;
                }

                public static class ServiceImagesdata implements Serializable {
                    /**
                     * sim_id : 0
                     * image_name : http://192.168.0.60/chatlocaly/assets/uploads/businesses/business-34/201804070215497n8bq8cyfe.png
                     */

                    @SerializedName("sim_id")
                    private String simId;
                    @SerializedName("image_name")
                    private String imageName;

                    public String getSimId() {
                        return simId;
                    }

                    public void setSimId(String simId) {
                        this.simId = simId;
                    }

                    public String getImageName() {
                        return imageName;
                    }

                    public void setImageName(String imageName) {
                        this.imageName = imageName;
                    }
                }

                public static class ServiceBrandsdata implements Serializable {
                    /**
                     * brand_id : 1
                     * brand_name : Patanjali
                     */

                    @SerializedName("brand_id")
                    private String brandId;
                    @SerializedName("brand_name")
                    private String brandName;

                    public String getBrandId() {
                        return brandId;
                    }

                    public void setBrandId(String brandId) {
                        this.brandId = brandId;
                    }

                    public String getBrandName() {
                        return brandName;
                    }

                    public void setBrandName(String brandName) {
                        this.brandName = brandName;
                    }
                }
            }
        }
    }
}
