package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anjani on 14/12/17.
 */

public class CategoryListModel {


    /**
     * data : {"category_list":[{"cat_id":"1","cat_name":"Common Needs","icon":"","sub_categories":[{"sub_cat_id":"1","sub_cat_name":"Departmental Store","icon":""},{"sub_cat_id":"2","sub_cat_name":"Fruit Vegetable Retail","icon":""},{"sub_cat_id":"3","sub_cat_name":"Milk Dairy","icon":""},{"sub_cat_id":"4","sub_cat_name":"Sweet Shops","icon":""},{"sub_cat_id":"5","sub_cat_name":"Bakeries","icon":""}]},{"cat_id":"2","cat_name":"Personal Needs","icon":"","sub_categories":[{"sub_cat_id":"6","sub_cat_name":" Boutiques","icon":""},{"sub_cat_id":"7","sub_cat_name":"Beauty Salons","icon":""},{"sub_cat_id":"8","sub_cat_name":"Spa & Massage","icon":""},{"sub_cat_id":"9","sub_cat_name":"Book Stores","icon":""}]},{"cat_id":"3","cat_name":"Utility Needs","icon":"","sub_categories":[{"sub_cat_id":"10","sub_cat_name":"Fashion Stores","icon":""},{"sub_cat_id":"11","sub_cat_name":"Electronics Stores","icon":""},{"sub_cat_id":"12","sub_cat_name":"Furniture Stores","icon":""},{"sub_cat_id":"13","sub_cat_name":"Decor Stores","icon":""},{"sub_cat_id":"14","sub_cat_name":"Electrical Stores","icon":""},{"sub_cat_id":"15","sub_cat_name":"Hardware Stores","icon":""}]},{"cat_id":"4","cat_name":"Skilled Needs","icon":"","sub_categories":[{"sub_cat_id":"16","sub_cat_name":"Mobile Repairs","icon":""},{"sub_cat_id":"17","sub_cat_name":"Computer Repairs","icon":""},{"sub_cat_id":"18","sub_cat_name":"AC Repairs","icon":""},{"sub_cat_id":"19","sub_cat_name":"Motorcycle Repairs","icon":""},{"sub_cat_id":"20","sub_cat_name":"Car Repairs","icon":""}]},{"cat_id":"5","cat_name":"Medicals Needs","icon":"","sub_categories":[{"sub_cat_id":"21","sub_cat_name":"Pharmacy Stores","icon":""},{"sub_cat_id":"22","sub_cat_name":"Pathology Labs","icon":""}]}],"message":"Category list successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private DataData category;

    public DataData getCategory() {
        return category;
    }

    public void setCategory(DataData category) {
        this.category = category;
    }

    public static class DataData {
        /**
         * category_list : [{"cat_id":"1","cat_name":"Common Needs","icon":"","sub_categories":[{"sub_cat_id":"1","sub_cat_name":"Departmental Store","icon":""},{"sub_cat_id":"2","sub_cat_name":"Fruit Vegetable Retail","icon":""},{"sub_cat_id":"3","sub_cat_name":"Milk Dairy","icon":""},{"sub_cat_id":"4","sub_cat_name":"Sweet Shops","icon":""},{"sub_cat_id":"5","sub_cat_name":"Bakeries","icon":""}]},{"cat_id":"2","cat_name":"Personal Needs","icon":"","sub_categories":[{"sub_cat_id":"6","sub_cat_name":" Boutiques","icon":""},{"sub_cat_id":"7","sub_cat_name":"Beauty Salons","icon":""},{"sub_cat_id":"8","sub_cat_name":"Spa & Massage","icon":""},{"sub_cat_id":"9","sub_cat_name":"Book Stores","icon":""}]},{"cat_id":"3","cat_name":"Utility Needs","icon":"","sub_categories":[{"sub_cat_id":"10","sub_cat_name":"Fashion Stores","icon":""},{"sub_cat_id":"11","sub_cat_name":"Electronics Stores","icon":""},{"sub_cat_id":"12","sub_cat_name":"Furniture Stores","icon":""},{"sub_cat_id":"13","sub_cat_name":"Decor Stores","icon":""},{"sub_cat_id":"14","sub_cat_name":"Electrical Stores","icon":""},{"sub_cat_id":"15","sub_cat_name":"Hardware Stores","icon":""}]},{"cat_id":"4","cat_name":"Skilled Needs","icon":"","sub_categories":[{"sub_cat_id":"16","sub_cat_name":"Mobile Repairs","icon":""},{"sub_cat_id":"17","sub_cat_name":"Computer Repairs","icon":""},{"sub_cat_id":"18","sub_cat_name":"AC Repairs","icon":""},{"sub_cat_id":"19","sub_cat_name":"Motorcycle Repairs","icon":""},{"sub_cat_id":"20","sub_cat_name":"Car Repairs","icon":""}]},{"cat_id":"5","cat_name":"Medicals Needs","icon":"","sub_categories":[{"sub_cat_id":"21","sub_cat_name":"Pharmacy Stores","icon":""},{"sub_cat_id":"22","sub_cat_name":"Pathology Labs","icon":""}]}]
         * message : Category list successfully got.
         * resultCode : 1
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("category_list")
        private List<CategoryListData> categoryList;

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

        public List<CategoryListData> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListData> categoryList) {
            this.categoryList = categoryList;
        }

        public static class CategoryListData {
            /**
             * cat_id : 1
             * cat_name : Common Needs
             * icon :
             * sub_categories : [{"sub_cat_id":"1","sub_cat_name":"Departmental Store","icon":""},{"sub_cat_id":"2","sub_cat_name":"Fruit Vegetable Retail","icon":""},{"sub_cat_id":"3","sub_cat_name":"Milk Dairy","icon":""},{"sub_cat_id":"4","sub_cat_name":"Sweet Shops","icon":""},{"sub_cat_id":"5","sub_cat_name":"Bakeries","icon":""}]
             */

            @SerializedName("cat_id")
            private String catId;
            @SerializedName("cat_name")
            private String catName;
            @SerializedName("icon")
            private String icon;
            @SerializedName("sub_categories")
            private List<SubCategoriesData> subCategories;

            public String getCatId() {
                return catId;
            }

            public void setCatId(String catId) {
                this.catId = catId;
            }

            public String getCatName() {
                return catName;
            }

            public void setCatName(String catName) {
                this.catName = catName;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<SubCategoriesData> getSubCategories() {
                return subCategories;
            }

            public void setSubCategories(List<SubCategoriesData> subCategories) {
                this.subCategories = subCategories;
            }

            public static class SubCategoriesData {
                /**
                 * sub_cat_id : 1
                 * sub_cat_name : Departmental Store
                 * icon :
                 */

                @SerializedName("sub_cat_id")
                private String subCatId;
                @SerializedName("sub_cat_name")
                private String subCatName;
                @SerializedName("icon")
                private String icon;
                @SerializedName("select")
                private boolean  is_selected=false;


                public boolean is_selected() {
                    return is_selected;
                }

                public void setIs_selected(boolean is_selected) {
                    this.is_selected = is_selected;
                }

                public String getSubCatId() {
                    return subCatId;
                }

                public void setSubCatId(String subCatId) {
                    this.subCatId = subCatId;
                }

                public String getSubCatName() {
                    return subCatName;
                }

                public void setSubCatName(String subCatName) {
                    this.subCatName = subCatName;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }
            }
        }
    }
}
