package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashok on 21/2/18.
 */

public class BussinessGroupCreateModel {


    /**
     * data : {"chat_group":{"chat_group_id":"8","chat_group_name":"GROUP08","chat_group_users":{"customer":{"chat_group_id":"8","c_user_id":"2","user_type":"customer","cg_user_status":"old_user"},"business_manager":[],"business_worker":[{"chat_group_id":"8","b_user_id":"5","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"6","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"39","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"}],"business_admin":[{"chat_group_id":"8","b_user_id":"2","b_ut_id":"1","user_type":"business_admin","cg_user_status":"old_user"}]},"business_detail":{"business_name":"sniper stop","b_id":"2"}},"message":"successfully get group.","resultCode":"1"}
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
         * chat_group : {"chat_group_id":"8","chat_group_name":"GROUP08","chat_group_users":{"customer":{"chat_group_id":"8","c_user_id":"2","user_type":"customer","cg_user_status":"old_user"},"business_manager":[],"business_worker":[{"chat_group_id":"8","b_user_id":"5","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"6","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"39","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"}],"business_admin":[{"chat_group_id":"8","b_user_id":"2","b_ut_id":"1","user_type":"business_admin","cg_user_status":"old_user"}]},"business_detail":{"business_name":"sniper stop","b_id":"2"}}
         * message : successfully get group.
         * resultCode : 1
         */

        @SerializedName("chat_group")
        private ChatGroupdata chatGroup;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;

        public ChatGroupdata getChatGroup() {
            return chatGroup;
        }

        public void setChatGroup(ChatGroupdata chatGroup) {
            this.chatGroup = chatGroup;
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

        public static class ChatGroupdata {
            /**
             * chat_group_id : 8
             * chat_group_name : GROUP08
             * chat_group_users : {"customer":{"chat_group_id":"8","c_user_id":"2","user_type":"customer","cg_user_status":"old_user"},"business_manager":[],"business_worker":[{"chat_group_id":"8","b_user_id":"5","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"6","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"39","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"}],"business_admin":[{"chat_group_id":"8","b_user_id":"2","b_ut_id":"1","user_type":"business_admin","cg_user_status":"old_user"}]}
             * business_detail : {"business_name":"sniper stop","b_id":"2"}
             */

            @SerializedName("chat_group_id")
            private String chatGroupId;
            @SerializedName("chat_group_name")
            private String chatGroupName;
            @SerializedName("chat_group_users")
            private ChatGroupUsersdata chatGroupUsers;
            @SerializedName("business_detail")
            private BusinessDetaildata businessDetail;

            public String getChatGroupId() {
                return chatGroupId;
            }

            public void setChatGroupId(String chatGroupId) {
                this.chatGroupId = chatGroupId;
            }

            public String getChatGroupName() {
                return chatGroupName;
            }

            public void setChatGroupName(String chatGroupName) {
                this.chatGroupName = chatGroupName;
            }

            public ChatGroupUsersdata getChatGroupUsers() {
                return chatGroupUsers;
            }

            public void setChatGroupUsers(ChatGroupUsersdata chatGroupUsers) {
                this.chatGroupUsers = chatGroupUsers;
            }

            public BusinessDetaildata getBusinessDetail() {
                return businessDetail;
            }

            public void setBusinessDetail(BusinessDetaildata businessDetail) {
                this.businessDetail = businessDetail;
            }

            public static class ChatGroupUsersdata {
                /**
                 * customer : {"chat_group_id":"8","c_user_id":"2","user_type":"customer","cg_user_status":"old_user"}
                 * business_manager : []
                 * business_worker : [{"chat_group_id":"8","b_user_id":"5","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"6","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"},{"chat_group_id":"8","b_user_id":"39","b_ut_id":"3","user_type":"business_worker","cg_user_status":"old_user"}]
                 * business_admin : [{"chat_group_id":"8","b_user_id":"2","b_ut_id":"1","user_type":"business_admin","cg_user_status":"old_user"}]
                 */

                @SerializedName("customer")
                private Customerdata customer;
                @SerializedName("business_manager")
                private List<?> businessManager;
                @SerializedName("business_worker")
                private List<BusinessWorkerdata> businessWorker;
                @SerializedName("business_admin")
                private List<BusinessAdmindata> businessAdmin;

                public Customerdata getCustomer() {
                    return customer;
                }

                public void setCustomer(Customerdata customer) {
                    this.customer = customer;
                }

                public List<?> getBusinessManager() {
                    return businessManager;
                }

                public void setBusinessManager(List<?> businessManager) {
                    this.businessManager = businessManager;
                }

                public List<BusinessWorkerdata> getBusinessWorker() {
                    return businessWorker;
                }

                public void setBusinessWorker(List<BusinessWorkerdata> businessWorker) {
                    this.businessWorker = businessWorker;
                }

                public List<BusinessAdmindata> getBusinessAdmin() {
                    return businessAdmin;
                }

                public void setBusinessAdmin(List<BusinessAdmindata> businessAdmin) {
                    this.businessAdmin = businessAdmin;
                }

                public static class Customerdata {
                    /**
                     * chat_group_id : 8
                     * c_user_id : 2
                     * user_type : customer
                     * cg_user_status : old_user
                     */

                    @SerializedName("chat_group_id")
                    private String chatGroupId;
                    @SerializedName("c_user_id")
                    private String cUserId;
                    @SerializedName("user_type")
                    private String userType;
                    @SerializedName("cg_user_status")
                    private String cgUserStatus;

                    public String getChatGroupId() {
                        return chatGroupId;
                    }

                    public void setChatGroupId(String chatGroupId) {
                        this.chatGroupId = chatGroupId;
                    }

                    public String getCUserId() {
                        return cUserId;
                    }

                    public void setCUserId(String cUserId) {
                        this.cUserId = cUserId;
                    }

                    public String getUserType() {
                        return userType;
                    }

                    public void setUserType(String userType) {
                        this.userType = userType;
                    }

                    public String getCgUserStatus() {
                        return cgUserStatus;
                    }

                    public void setCgUserStatus(String cgUserStatus) {
                        this.cgUserStatus = cgUserStatus;
                    }
                }

                public static class BusinessWorkerdata {
                    /**
                     * chat_group_id : 8
                     * b_user_id : 5
                     * b_ut_id : 3
                     * user_type : business_worker
                     * cg_user_status : old_user
                     */

                    @SerializedName("chat_group_id")
                    private String chatGroupId;
                    @SerializedName("b_user_id")
                    private String bUserId;
                    @SerializedName("b_ut_id")
                    private String bUtId;
                    @SerializedName("user_type")
                    private String userType;
                    @SerializedName("cg_user_status")
                    private String cgUserStatus;

                    public String getChatGroupId() {
                        return chatGroupId;
                    }

                    public void setChatGroupId(String chatGroupId) {
                        this.chatGroupId = chatGroupId;
                    }

                    public String getBUserId() {
                        return bUserId;
                    }

                    public void setBUserId(String bUserId) {
                        this.bUserId = bUserId;
                    }

                    public String getBUtId() {
                        return bUtId;
                    }

                    public void setBUtId(String bUtId) {
                        this.bUtId = bUtId;
                    }

                    public String getUserType() {
                        return userType;
                    }

                    public void setUserType(String userType) {
                        this.userType = userType;
                    }

                    public String getCgUserStatus() {
                        return cgUserStatus;
                    }

                    public void setCgUserStatus(String cgUserStatus) {
                        this.cgUserStatus = cgUserStatus;
                    }
                }

                public static class BusinessAdmindata {
                    /**
                     * chat_group_id : 8
                     * b_user_id : 2
                     * b_ut_id : 1
                     * user_type : business_admin
                     * cg_user_status : old_user
                     */

                    @SerializedName("chat_group_id")
                    private String chatGroupId;
                    @SerializedName("b_user_id")
                    private String bUserId;
                    @SerializedName("b_ut_id")
                    private String bUtId;
                    @SerializedName("user_type")
                    private String userType;
                    @SerializedName("cg_user_status")
                    private String cgUserStatus;

                    public String getChatGroupId() {
                        return chatGroupId;
                    }

                    public void setChatGroupId(String chatGroupId) {
                        this.chatGroupId = chatGroupId;
                    }

                    public String getBUserId() {
                        return bUserId;
                    }

                    public void setBUserId(String bUserId) {
                        this.bUserId = bUserId;
                    }

                    public String getBUtId() {
                        return bUtId;
                    }

                    public void setBUtId(String bUtId) {
                        this.bUtId = bUtId;
                    }

                    public String getUserType() {
                        return userType;
                    }

                    public void setUserType(String userType) {
                        this.userType = userType;
                    }

                    public String getCgUserStatus() {
                        return cgUserStatus;
                    }

                    public void setCgUserStatus(String cgUserStatus) {
                        this.cgUserStatus = cgUserStatus;
                    }
                }
            }

            public static class BusinessDetaildata {
                /**
                 * business_name : sniper stop
                 * b_id : 2
                 */

                @SerializedName("business_name")
                private String businessName;
                @SerializedName("b_id")
                private String bId;

                public String getBusinessName() {
                    return businessName;
                }

                public void setBusinessName(String businessName) {
                    this.businessName = businessName;
                }

                public String getBId() {
                    return bId;
                }

                public void setBId(String bId) {
                    this.bId = bId;
                }
            }
        }
    }
}
