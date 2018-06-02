package com.chatlocaly.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashok on 23/3/18.
 */

public class ThreadModel {


    /**
     * data : {"message_list":[{"contentType":0,"message":"Hell","createdAtTime":1521716468812,"to":"0C12","to_user_id":"12","to_full_name":"ashok","to_profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/user-default.png","chat_group_id":"44","chat_group_name":"GROUP044","b_id":"11","business_name":"Nothing before cofee","business_logo":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png","chat_group_users":[{"cgu_user_id":"12","full_name":"ashok","profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/user-default.png"},{"cgu_user_id":"19","full_name":"rahul","profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/20180319024155ks7285ubub.png"}]}],"message":"message list successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata Thread;

    public Datadata getThread() {
        return Thread;
    }

    public void setThread(Datadata Thread) {
        this.Thread = Thread;
    }

    public static class Datadata {


        /**
         * message_list : [{"contentType":0,"message":"Hell","createdAtTime":1521716468812,"to":"0C12","to_user_id":"12","to_full_name":"ashok","to_profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/user-default.png","chat_group_id":"44","chat_group_name":"GROUP044","b_id":"11","business_name":"Nothing before cofee","business_logo":"http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png","chat_group_users":[{"cgu_user_id":"12","full_name":"ashok","profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/user-default.png"},{"cgu_user_id":"19","full_name":"rahul","profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/20180319024155ks7285ubub.png"}]}]
         * message : message list successfully got.
         * resultCode : 1
         */

        @Expose
        @SerializedName("total_unreadCount")
        private int total_unreadCount;

        @Expose
        @SerializedName("tag_total_unreadCount")
        private int tag_total_unreadCount;


        @Expose
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("message_list")
        private List<MessageListdata> messageList;

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

        public List<MessageListdata> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListdata> messageList) {
            this.messageList = messageList;
        }

        public static class MessageListdata {
            /**
             * contentType : 0
             * message : Hell
             * createdAtTime : 1521716468812
             * to : 0C12
             * to_user_id : 12
             * to_full_name : ashok
             * to_profile_image : http://192.168.0.60/chatlocaly/assets/uploads/customers/user-default.png
             * chat_group_id : 44
             * chat_group_name : GROUP044
             * b_id : 11
             * business_name : Nothing before cofee
             * business_logo : http://192.168.0.60/chatlocaly/assets/uploads/default-bussiness-image.png
             * chat_group_users : [{"cgu_user_id":"12","full_name":"ashok","profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/user-default.png"},{"cgu_user_id":"19","full_name":"rahul","profile_image":"http://192.168.0.60/chatlocaly/assets/uploads/customers/20180319024155ks7285ubub.png"}]
             */




            @SerializedName("designation")
            private String designation;

            @SerializedName("groupId")
            private int  groupId;
            /**
             * file : {"key":"03dbe0f2-fc7a-4282-8ff6-255cc7ed1589","blobKey":"AMIfv966FGOCbRdUA1uoG9a0Y8KcXhQviRhKcSxKKycw5z7JFMlXIAakUMz637pF8S2RKL2u95IhAYekwmxvEFEUzAPAkTyuA9Y5T7MMoTA7kVNnAV2MJS78BwCM7-3IpgVq-Z1JXVMuOMX6Ahzi-u0Oi_uJyklOsA","name":"share.png","size":42290,"contentType":"image/png","thumbnailUrl":"https://lh3.googleusercontent.com/i2PcolgOA2EhqZbks_1tJ21IuOJ_lDrEoQo_UKQNlNCPf1wqpV_u7mBrKtnVaxWsO8jNPd2sEnO0KIfp6jg=s120"}
             */

            @SerializedName("file")
            private Filedata fileX;

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            @SerializedName("home_services")
            private String home_services;
            @SerializedName("services_km")
            private String services_km;
            @SerializedName("business_rating")
            private String business_rating;
            @SerializedName("customer_notification")
            private String customer_notification;
            @SerializedName("customer_tag")
            private String customer_tag;
            @SerializedName("block_chat_group")
            private String block_chat_group;

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


            public String getBlock_chat_group() {
                return block_chat_group;
            }

            public void setBlock_chat_group(String block_chat_group) {
                this.block_chat_group = block_chat_group;
            }

            public String getDesignation() {
                return designation;
            }

            public void setDesignation(String designation) {
                this.designation = designation;
            }

            public String getHome_services() {
                return home_services;
            }

            public void setHome_services(String home_services) {
                this.home_services = home_services;
            }

            public String getServices_km() {
                return services_km;
            }

            public void setServices_km(String services_km) {
                this.services_km = services_km;
            }

            public String getBusiness_rating() {
                return business_rating;
            }

            public void setBusiness_rating(String business_rating) {
                this.business_rating = business_rating;
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

            public long getRating_count() {
                return rating_count;
            }

            public void setRating_count(long rating_count) {
                this.rating_count = rating_count;
            }

            public String getbId() {
                return bId;
            }

            public void setbId(String bId) {
                this.bId = bId;
            }

            @SerializedName("rating_count")
            private long rating_count;

            @SerializedName("contentType")
            private int contentType;
            @SerializedName("message")
            private String message;
            @SerializedName("createdAtTime")
            private long createdAtTime;
            @SerializedName("to")
            private String to;
            @SerializedName("to_user_id")
            private String toUserId;
            @SerializedName("to_full_name")
            private String toFullName;
            @SerializedName("to_profile_image")
            private String toProfileImage;
            @SerializedName("chat_group_id")
            private String chatGroupId;
            @SerializedName("chat_group_name")
            private String chatGroupName;
            @SerializedName("b_id")
            private String bId;
            @SerializedName("business_name")
            private String businessName;
            @SerializedName("business_logo")
            private String businessLogo;
            @Expose
            @SerializedName("chat_group_users")
            private List<ChatGroupUsersdata> chatGroupUsers=null;

            public int getContentType() {
                return contentType;
            }

            public void setContentType(int contentType) {
                this.contentType = contentType;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public long getCreatedAtTime() {
                return createdAtTime;
            }

            public void setCreatedAtTime(long createdAtTime) {
                this.createdAtTime = createdAtTime;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getToUserId() {
                return toUserId;
            }

            public void setToUserId(String toUserId) {
                this.toUserId = toUserId;
            }

            public String getToFullName() {
                return toFullName;
            }

            public void setToFullName(String toFullName) {
                this.toFullName = toFullName;
            }

            public String getToProfileImage() {
                return toProfileImage;
            }

            public void setToProfileImage(String toProfileImage) {
                this.toProfileImage = toProfileImage;
            }

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

            public String getBusinessLogo() {
                return businessLogo;
            }

            public void setBusinessLogo(String businessLogo) {
                this.businessLogo = businessLogo;
            }

            public List<ChatGroupUsersdata> getChatGroupUsers() {
                return chatGroupUsers;
            }

            public void setChatGroupUsers(List<ChatGroupUsersdata> chatGroupUsers) {
                this.chatGroupUsers = chatGroupUsers;
            }

            public Filedata getFileX() {
                return fileX;
            }

            public void setFileX(Filedata fileX) {
                this.fileX = fileX;
            }

            public static class ChatGroupUsersdata {
                /**
                 * cgu_user_id : 12
                 * full_name : ashok
                 * profile_image : http://192.168.0.60/chatlocaly/assets/uploads/customers/user-default.png
                 */

                @SerializedName("cgu_user_id")
                private String cguUserId;
                @SerializedName("full_name")
                private String fullName;
                @SerializedName("profile_image")
                private String profileImage;

                public String getCguUserId() {
                    return cguUserId;
                }

                public void setCguUserId(String cguUserId) {
                    this.cguUserId = cguUserId;
                }

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }

                public String getProfileImage() {
                    return profileImage;
                }

                public void setProfileImage(String profileImage) {
                    this.profileImage = profileImage;
                }
            }


            public static class Filedata {
                /**
                 * key : 03dbe0f2-fc7a-4282-8ff6-255cc7ed1589
                 * blobKey : AMIfv966FGOCbRdUA1uoG9a0Y8KcXhQviRhKcSxKKycw5z7JFMlXIAakUMz637pF8S2RKL2u95IhAYekwmxvEFEUzAPAkTyuA9Y5T7MMoTA7kVNnAV2MJS78BwCM7-3IpgVq-Z1JXVMuOMX6Ahzi-u0Oi_uJyklOsA
                 * name : share.png
                 * size : 42290
                 * contentType : image/png
                 * thumbnailUrl : https://lh3.googleusercontent.com/i2PcolgOA2EhqZbks_1tJ21IuOJ_lDrEoQo_UKQNlNCPf1wqpV_u7mBrKtnVaxWsO8jNPd2sEnO0KIfp6jg=s120
                 */

                @SerializedName("key")
                private String key;
                @SerializedName("blobKey")
                private String blobKey;
                @SerializedName("name")
                private String name;
                @SerializedName("size")
                private int size;
                @SerializedName("contentType")
                private String contentTypeX;
                @SerializedName("thumbnailUrl")
                private String thumbnailUrl;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getBlobKey() {
                    return blobKey;
                }

                public void setBlobKey(String blobKey) {
                    this.blobKey = blobKey;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSize() {
                    return size;
                }

                public void setSize(int size) {
                    this.size = size;
                }

                public String getContentTypeX() {
                    return contentTypeX;
                }

                public void setContentTypeX(String contentTypeX) {
                    this.contentTypeX = contentTypeX;
                }

                public String getThumbnailUrl() {
                    return thumbnailUrl;
                }

                public void setThumbnailUrl(String thumbnailUrl) {
                    this.thumbnailUrl = thumbnailUrl;
                }
            }
        }

    }

}
