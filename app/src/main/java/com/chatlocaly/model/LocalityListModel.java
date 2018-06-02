package com.chatlocaly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anjani on 15/1/18.
 */

public class LocalityListModel {

    /**
     * data : {"locality_list":[{"loc_id":"1","name":"Uttam Nagar"},{"loc_id":"2","name":"Greater Noida West"},{"loc_id":"3","name":"Noida Extension"},{"loc_id":"4","name":"Indirapuram"},{"loc_id":"5","name":"Raj Nagar Extension"},{"loc_id":"6","name":"Sector 88 Faridabad"},{"loc_id":"7","name":"Sector 1 Greater Noida West"},{"loc_id":"8","name":"Sector-67 Gurgaon"},{"loc_id":"9","name":"Sector-137 Noida"},{"loc_id":"10","name":"Govind Puram"},{"loc_id":"11","name":"Sector-65 Gurgaon"},{"loc_id":"12","name":"Dwarka Mor"},{"loc_id":"13","name":"DLF CITY PHASE 2"},{"loc_id":"14","name":"Crossing Republik"},{"loc_id":"15","name":"Sector-150 Noida"},{"loc_id":"16","name":"Sector-6 Dwarka"},{"loc_id":"17","name":"Sector-78 Noida"},{"loc_id":"18","name":"Sector-57 Gurgaon"},{"loc_id":"19","name":"Sector-24 Rohini"},{"loc_id":"20","name":"Shahberi"},{"loc_id":"21","name":"Vasundhara"},{"loc_id":"22","name":"Sector-48 Gurgaon"},{"loc_id":"23","name":"Chattarpur"},{"loc_id":"24","name":"Sohna"},{"loc_id":"25","name":"Sector-66 Gurgaon"},{"loc_id":"26","name":"Sector 86 Faridabad"},{"loc_id":"27","name":"Sector-19 Dwarka"},{"loc_id":"28","name":"sector-121 Noida"},{"loc_id":"29","name":"Uttam Nagar West"},{"loc_id":"30","name":"Sector-12 Dwarka"},{"loc_id":"31","name":"Sector-54 Gurgaon"},{"loc_id":"32","name":"Techzone 4 Greater Noida West"},{"loc_id":"33","name":"Sector-10 Dwarka"},{"loc_id":"34","name":"Greenfield Colony"},{"loc_id":"35","name":"Greater Kailash I"},{"loc_id":"36","name":"Sector-128 Noida"},{"loc_id":"37","name":"Green Field"},{"loc_id":"38","name":"Vasant Kunj"},{"loc_id":"39","name":"DLF CITY PHASE 1"},{"loc_id":"40","name":"Sector-4 Dwarka"},{"loc_id":"41","name":"Sector-11 Dwarka"},{"loc_id":"42","name":"Sector-82 Gurgaon"},{"loc_id":"43","name":"NH-24 Highway"},{"loc_id":"44","name":"Sector-72 Gurgaon"},{"loc_id":"45","name":"Sector-168 Noida"},{"loc_id":"46","name":"Nirvana Country"},{"loc_id":"47","name":"Sector-16 B Gr Noida"},{"loc_id":"48","name":" Sector-45 Noida"},{"loc_id":"49","name":"Sector-134 Noida"},{"loc_id":"50","name":"Sector-74 Noida,"},{"loc_id":"51","name":"Sector-75 Noida"},{"loc_id":"52","name":"Niti Khand 1"},{"loc_id":"53","name":"Sector-5 Dwarka"},{"loc_id":"54","name":"DLF CITY PHASE 4"},{"loc_id":"55","name":"Sector-49 Gurgaon,"},{"loc_id":"56","name":"Palam Vihar,"},{"loc_id":"57","name":"Sector-23 Dwarka"},{"loc_id":"58","name":"Sector-7 Dwarka"},{"loc_id":"59","name":"Sector-22 Dwarka"},{"loc_id":"60","name":"Sector-60 Gurgaon"},{"loc_id":"61","name":"Vasant Vihar"},{"loc_id":"62","name":"Yamuna Expressway"},{"loc_id":"63","name":"Sector-107 Noida"},{"loc_id":"64","name":"Sector-109 Gurgaon"},{"loc_id":"65","name":"Greater Kailash II"},{"loc_id":"66","name":"Paschim Vihar"},{"loc_id":"67","name":"Sector-86 Gurgaon"},{"loc_id":"68","name":"Defence Colony"},{"loc_id":"69","name":"Sector-143 Noida"},{"loc_id":"70","name":" Sector-77 Noida"},{"loc_id":"71","name":"I P Extension"},{"loc_id":"72","name":"Sector-90 Gurgaon"},{"loc_id":"73","name":"Sector 84 Faridabad"},{"loc_id":"74","name":"Vaibhav Khand"},{"loc_id":"75","name":"Sector-100 Noida"},{"loc_id":"76","name":"Sector-93 A Noida,"},{"loc_id":"77","name":"Sector-83 Gurgaon"},{"loc_id":"78","name":"Sector-69 Gurgaon"},{"loc_id":"79","name":"Sector-92 Gurgaon"},{"loc_id":"80","name":"Sector-25 Rohini"},{"loc_id":"81","name":"Sector-104 Noida"},{"loc_id":"82","name":"Sector-81 Gurgaon"},{"loc_id":"83","name":"Gyan Khand 1"},{"loc_id":"84","name":"Sector-82 Noida"},{"loc_id":"85","name":"Sushant Lok Phase - 1"},{"loc_id":"86","name":"Malibu Town"},{"loc_id":"87","name":"Sector-135 Noida"},{"loc_id":"88","name":"Sector 82 Faridabad"},{"loc_id":"89","name":"Safdarjung Enclave"},{"loc_id":"90","name":"Sector 89 Faridabad"},{"loc_id":"91","name":"Shakti Khand 3"},{"loc_id":"92","name":"Sector-46 Gurgaon"},{"loc_id":"93","name":"Ankur Vihar"},{"loc_id":"94","name":"Sector-84 Gurgaon"},{"loc_id":"95","name":"South City 1"},{"loc_id":"96","name":"Saket"},{"loc_id":"97","name":"Sector 78 Faridabad"},{"loc_id":"98","name":"Sector-13 Dwarka"},{"loc_id":"99","name":"DLF CITY PHASE 5"},{"loc_id":"100","name":"Vaishali"},{"loc_id":"101","name":"Mayur Vihar - I"},{"loc_id":"102","name":"Sector-56 Gurgaon"},{"loc_id":"103","name":"Sector-104 Gurgaon"},{"loc_id":"104","name":"Mahavir Enclave"},{"loc_id":"105","name":"Shalimar garden"},{"loc_id":"106","name":"Sector-70A Gurgaon"},{"loc_id":"107","name":"Sainik Colony"},{"loc_id":"108","name":"Sector-18A Dwarka"},{"loc_id":"109","name":"Sector-4 Vaishali"},{"loc_id":"110","name":"Sector-3 Dwarka"},{"loc_id":"111","name":"Sector-62 Gurgaon"},{"loc_id":"112","name":"L Zone"},{"loc_id":"113","name":"Sector-103 Gurgaon"},{"loc_id":"114","name":"Sector-51 Gurgaon"},{"loc_id":"115","name":"Sector-4 Gr Noida"},{"loc_id":"116","name":"Sector-52 Gurgaon"},{"loc_id":"117","name":"East of Kailash"},{"loc_id":"118","name":"Sector-5 Vaishali"},{"loc_id":"119","name":"Sector-1 Vasundhara"},{"loc_id":"120","name":"Sector ZETA Gr Noida"},{"loc_id":"121","name":"Vikas Puri"},{"loc_id":"122","name":"Sector-8 Dwarka"},{"loc_id":"123","name":"Sarita Vihar"},{"loc_id":"124","name":"Sector 85 Faridabad"},{"loc_id":"125","name":"Sector 77 Faridabad"},{"loc_id":"126","name":"Sector-68 Gurgaon"},{"loc_id":"127","name":"Sector-50 Noida"},{"loc_id":"128","name":"Sector-16 Gr Noida"},{"loc_id":"129","name":"Sector-45 Gurgaon"},{"loc_id":"130","name":"Sector-76 Noida"},{"loc_id":"131","name":"C R Park"},{"loc_id":"132","name":"Sector-102 Gurgaon"},{"loc_id":"133","name":"Sector 81 Faridabad"},{"loc_id":"134","name":"Sector-108 Gurgaon"},{"loc_id":"135","name":"Sector-110 Noida"},{"loc_id":"136","name":"Sector-3 Vasundhara"},{"loc_id":"137","name":"Maruti Kunj"},{"loc_id":"138","name":"Sector 87 Faridabad"},{"loc_id":"139","name":"Ahinsa Khand 1"},{"loc_id":"140","name":"Bhopura"},{"loc_id":"141","name":"Sector-79 Noida"},{"loc_id":"142","name":"Sector ZETA I Gr Noida"},{"loc_id":"143","name":"Sector-91 Gurgaon"},{"loc_id":"144","name":"Sector-82A Gurgaon"},{"loc_id":"145","name":"Sector-28 Rohini"},{"loc_id":"146","name":"Sector-93 Noida"},{"loc_id":"147","name":"Shakti Khand 2"},{"loc_id":"148","name":"Golf Course Road"},{"loc_id":"149","name":"Sector-2 Gr Noida"},{"loc_id":"150","name":"Nehar Par"},{"loc_id":"151","name":"Sector-120 Noida"},{"loc_id":"152","name":"C Block Sushant Lok Phase - I"},{"loc_id":"153","name":"Burari"},{"loc_id":"154","name":"Sector-70 Gurgaon"},{"loc_id":"155","name":"Sector-5 Vasundhara"},{"loc_id":"156","name":"Lal Kuan"},{"loc_id":"157","name":"DLF CITY PHASE 3"},{"loc_id":"158","name":"South City 2"},{"loc_id":"159","name":"Janakpuri"},{"loc_id":"160","name":"Sector-93 Gurgaon"},{"loc_id":"161","name":"Sector-129 Noida"},{"loc_id":"162","name":"Ranjit Vihar I"},{"loc_id":"163","name":"Sector-42 Gurgaon"},{"loc_id":"164","name":"Sector-37C Gurgaon"},{"loc_id":"165","name":"Sector-50 Gurgaon"},{"loc_id":"166","name":"Sector-2 Dwarka"},{"loc_id":"167","name":"Sector-18B Dwarka"},{"loc_id":"168","name":"Pari Chowk"},{"loc_id":"169","name":"Sector-93 B Noida"},{"loc_id":"170","name":"Mohan Garden"},{"loc_id":"171","name":"Sector-3 Gr Noida"},{"loc_id":"172","name":"Sector-58 Gurgaon"},{"loc_id":"173","name":"Sector-23 Rohini"},{"loc_id":"174","name":"Sushant Lok Phase - 3"},{"loc_id":"175","name":"Panchsheel Park"},{"loc_id":"176","name":"Hauj Khas"},{"loc_id":"177","name":"Sector Omicron III Greater Noida"},{"loc_id":"178","name":"Pitampura"},{"loc_id":"179","name":"Sector-14 Dwarka"},{"loc_id":"180","name":"BPTP"},{"loc_id":"181","name":"Panchsheel Enclave"},{"loc_id":"182","name":"Sector Chi 5 Gr Noida"},{"loc_id":"183","name":"Moti Nagar"},{"loc_id":"184","name":"Sector-43 Gurgaon"},{"loc_id":"185","name":"Shalimar Garden Extension I"},{"loc_id":"186","name":"Jaypee Greens"},{"loc_id":"187","name":"Sector-70 Noida"},{"loc_id":"188","name":"Sector-85 Gurgaon"},{"loc_id":"189","name":"Sushant Lok Phase - 2"},{"loc_id":"190","name":"Shalimar Garden Extension II"},{"loc_id":"191","name":"Suraj Kund"},{"loc_id":"192","name":"Green Park"},{"loc_id":"193","name":"Sector Omicron I Greater Noida"},{"loc_id":"194","name":"Mehrauli"},{"loc_id":"195","name":"Laxmi Nagar"}],"message":"Locality list successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private LocalityData data;

    public LocalityData getData() {
        return data;
    }

    public void setData(LocalityData data) {
        this.data = data;
    }

    public static class LocalityData {
        /**
         * locality_list : [{"loc_id":"1","name":"Uttam Nagar"},{"loc_id":"2","name":"Greater Noida West"},{"loc_id":"3","name":"Noida Extension"},{"loc_id":"4","name":"Indirapuram"},{"loc_id":"5","name":"Raj Nagar Extension"},{"loc_id":"6","name":"Sector 88 Faridabad"},{"loc_id":"7","name":"Sector 1 Greater Noida West"},{"loc_id":"8","name":"Sector-67 Gurgaon"},{"loc_id":"9","name":"Sector-137 Noida"},{"loc_id":"10","name":"Govind Puram"},{"loc_id":"11","name":"Sector-65 Gurgaon"},{"loc_id":"12","name":"Dwarka Mor"},{"loc_id":"13","name":"DLF CITY PHASE 2"},{"loc_id":"14","name":"Crossing Republik"},{"loc_id":"15","name":"Sector-150 Noida"},{"loc_id":"16","name":"Sector-6 Dwarka"},{"loc_id":"17","name":"Sector-78 Noida"},{"loc_id":"18","name":"Sector-57 Gurgaon"},{"loc_id":"19","name":"Sector-24 Rohini"},{"loc_id":"20","name":"Shahberi"},{"loc_id":"21","name":"Vasundhara"},{"loc_id":"22","name":"Sector-48 Gurgaon"},{"loc_id":"23","name":"Chattarpur"},{"loc_id":"24","name":"Sohna"},{"loc_id":"25","name":"Sector-66 Gurgaon"},{"loc_id":"26","name":"Sector 86 Faridabad"},{"loc_id":"27","name":"Sector-19 Dwarka"},{"loc_id":"28","name":"sector-121 Noida"},{"loc_id":"29","name":"Uttam Nagar West"},{"loc_id":"30","name":"Sector-12 Dwarka"},{"loc_id":"31","name":"Sector-54 Gurgaon"},{"loc_id":"32","name":"Techzone 4 Greater Noida West"},{"loc_id":"33","name":"Sector-10 Dwarka"},{"loc_id":"34","name":"Greenfield Colony"},{"loc_id":"35","name":"Greater Kailash I"},{"loc_id":"36","name":"Sector-128 Noida"},{"loc_id":"37","name":"Green Field"},{"loc_id":"38","name":"Vasant Kunj"},{"loc_id":"39","name":"DLF CITY PHASE 1"},{"loc_id":"40","name":"Sector-4 Dwarka"},{"loc_id":"41","name":"Sector-11 Dwarka"},{"loc_id":"42","name":"Sector-82 Gurgaon"},{"loc_id":"43","name":"NH-24 Highway"},{"loc_id":"44","name":"Sector-72 Gurgaon"},{"loc_id":"45","name":"Sector-168 Noida"},{"loc_id":"46","name":"Nirvana Country"},{"loc_id":"47","name":"Sector-16 B Gr Noida"},{"loc_id":"48","name":" Sector-45 Noida"},{"loc_id":"49","name":"Sector-134 Noida"},{"loc_id":"50","name":"Sector-74 Noida,"},{"loc_id":"51","name":"Sector-75 Noida"},{"loc_id":"52","name":"Niti Khand 1"},{"loc_id":"53","name":"Sector-5 Dwarka"},{"loc_id":"54","name":"DLF CITY PHASE 4"},{"loc_id":"55","name":"Sector-49 Gurgaon,"},{"loc_id":"56","name":"Palam Vihar,"},{"loc_id":"57","name":"Sector-23 Dwarka"},{"loc_id":"58","name":"Sector-7 Dwarka"},{"loc_id":"59","name":"Sector-22 Dwarka"},{"loc_id":"60","name":"Sector-60 Gurgaon"},{"loc_id":"61","name":"Vasant Vihar"},{"loc_id":"62","name":"Yamuna Expressway"},{"loc_id":"63","name":"Sector-107 Noida"},{"loc_id":"64","name":"Sector-109 Gurgaon"},{"loc_id":"65","name":"Greater Kailash II"},{"loc_id":"66","name":"Paschim Vihar"},{"loc_id":"67","name":"Sector-86 Gurgaon"},{"loc_id":"68","name":"Defence Colony"},{"loc_id":"69","name":"Sector-143 Noida"},{"loc_id":"70","name":" Sector-77 Noida"},{"loc_id":"71","name":"I P Extension"},{"loc_id":"72","name":"Sector-90 Gurgaon"},{"loc_id":"73","name":"Sector 84 Faridabad"},{"loc_id":"74","name":"Vaibhav Khand"},{"loc_id":"75","name":"Sector-100 Noida"},{"loc_id":"76","name":"Sector-93 A Noida,"},{"loc_id":"77","name":"Sector-83 Gurgaon"},{"loc_id":"78","name":"Sector-69 Gurgaon"},{"loc_id":"79","name":"Sector-92 Gurgaon"},{"loc_id":"80","name":"Sector-25 Rohini"},{"loc_id":"81","name":"Sector-104 Noida"},{"loc_id":"82","name":"Sector-81 Gurgaon"},{"loc_id":"83","name":"Gyan Khand 1"},{"loc_id":"84","name":"Sector-82 Noida"},{"loc_id":"85","name":"Sushant Lok Phase - 1"},{"loc_id":"86","name":"Malibu Town"},{"loc_id":"87","name":"Sector-135 Noida"},{"loc_id":"88","name":"Sector 82 Faridabad"},{"loc_id":"89","name":"Safdarjung Enclave"},{"loc_id":"90","name":"Sector 89 Faridabad"},{"loc_id":"91","name":"Shakti Khand 3"},{"loc_id":"92","name":"Sector-46 Gurgaon"},{"loc_id":"93","name":"Ankur Vihar"},{"loc_id":"94","name":"Sector-84 Gurgaon"},{"loc_id":"95","name":"South City 1"},{"loc_id":"96","name":"Saket"},{"loc_id":"97","name":"Sector 78 Faridabad"},{"loc_id":"98","name":"Sector-13 Dwarka"},{"loc_id":"99","name":"DLF CITY PHASE 5"},{"loc_id":"100","name":"Vaishali"},{"loc_id":"101","name":"Mayur Vihar - I"},{"loc_id":"102","name":"Sector-56 Gurgaon"},{"loc_id":"103","name":"Sector-104 Gurgaon"},{"loc_id":"104","name":"Mahavir Enclave"},{"loc_id":"105","name":"Shalimar garden"},{"loc_id":"106","name":"Sector-70A Gurgaon"},{"loc_id":"107","name":"Sainik Colony"},{"loc_id":"108","name":"Sector-18A Dwarka"},{"loc_id":"109","name":"Sector-4 Vaishali"},{"loc_id":"110","name":"Sector-3 Dwarka"},{"loc_id":"111","name":"Sector-62 Gurgaon"},{"loc_id":"112","name":"L Zone"},{"loc_id":"113","name":"Sector-103 Gurgaon"},{"loc_id":"114","name":"Sector-51 Gurgaon"},{"loc_id":"115","name":"Sector-4 Gr Noida"},{"loc_id":"116","name":"Sector-52 Gurgaon"},{"loc_id":"117","name":"East of Kailash"},{"loc_id":"118","name":"Sector-5 Vaishali"},{"loc_id":"119","name":"Sector-1 Vasundhara"},{"loc_id":"120","name":"Sector ZETA Gr Noida"},{"loc_id":"121","name":"Vikas Puri"},{"loc_id":"122","name":"Sector-8 Dwarka"},{"loc_id":"123","name":"Sarita Vihar"},{"loc_id":"124","name":"Sector 85 Faridabad"},{"loc_id":"125","name":"Sector 77 Faridabad"},{"loc_id":"126","name":"Sector-68 Gurgaon"},{"loc_id":"127","name":"Sector-50 Noida"},{"loc_id":"128","name":"Sector-16 Gr Noida"},{"loc_id":"129","name":"Sector-45 Gurgaon"},{"loc_id":"130","name":"Sector-76 Noida"},{"loc_id":"131","name":"C R Park"},{"loc_id":"132","name":"Sector-102 Gurgaon"},{"loc_id":"133","name":"Sector 81 Faridabad"},{"loc_id":"134","name":"Sector-108 Gurgaon"},{"loc_id":"135","name":"Sector-110 Noida"},{"loc_id":"136","name":"Sector-3 Vasundhara"},{"loc_id":"137","name":"Maruti Kunj"},{"loc_id":"138","name":"Sector 87 Faridabad"},{"loc_id":"139","name":"Ahinsa Khand 1"},{"loc_id":"140","name":"Bhopura"},{"loc_id":"141","name":"Sector-79 Noida"},{"loc_id":"142","name":"Sector ZETA I Gr Noida"},{"loc_id":"143","name":"Sector-91 Gurgaon"},{"loc_id":"144","name":"Sector-82A Gurgaon"},{"loc_id":"145","name":"Sector-28 Rohini"},{"loc_id":"146","name":"Sector-93 Noida"},{"loc_id":"147","name":"Shakti Khand 2"},{"loc_id":"148","name":"Golf Course Road"},{"loc_id":"149","name":"Sector-2 Gr Noida"},{"loc_id":"150","name":"Nehar Par"},{"loc_id":"151","name":"Sector-120 Noida"},{"loc_id":"152","name":"C Block Sushant Lok Phase - I"},{"loc_id":"153","name":"Burari"},{"loc_id":"154","name":"Sector-70 Gurgaon"},{"loc_id":"155","name":"Sector-5 Vasundhara"},{"loc_id":"156","name":"Lal Kuan"},{"loc_id":"157","name":"DLF CITY PHASE 3"},{"loc_id":"158","name":"South City 2"},{"loc_id":"159","name":"Janakpuri"},{"loc_id":"160","name":"Sector-93 Gurgaon"},{"loc_id":"161","name":"Sector-129 Noida"},{"loc_id":"162","name":"Ranjit Vihar I"},{"loc_id":"163","name":"Sector-42 Gurgaon"},{"loc_id":"164","name":"Sector-37C Gurgaon"},{"loc_id":"165","name":"Sector-50 Gurgaon"},{"loc_id":"166","name":"Sector-2 Dwarka"},{"loc_id":"167","name":"Sector-18B Dwarka"},{"loc_id":"168","name":"Pari Chowk"},{"loc_id":"169","name":"Sector-93 B Noida"},{"loc_id":"170","name":"Mohan Garden"},{"loc_id":"171","name":"Sector-3 Gr Noida"},{"loc_id":"172","name":"Sector-58 Gurgaon"},{"loc_id":"173","name":"Sector-23 Rohini"},{"loc_id":"174","name":"Sushant Lok Phase - 3"},{"loc_id":"175","name":"Panchsheel Park"},{"loc_id":"176","name":"Hauj Khas"},{"loc_id":"177","name":"Sector Omicron III Greater Noida"},{"loc_id":"178","name":"Pitampura"},{"loc_id":"179","name":"Sector-14 Dwarka"},{"loc_id":"180","name":"BPTP"},{"loc_id":"181","name":"Panchsheel Enclave"},{"loc_id":"182","name":"Sector Chi 5 Gr Noida"},{"loc_id":"183","name":"Moti Nagar"},{"loc_id":"184","name":"Sector-43 Gurgaon"},{"loc_id":"185","name":"Shalimar Garden Extension I"},{"loc_id":"186","name":"Jaypee Greens"},{"loc_id":"187","name":"Sector-70 Noida"},{"loc_id":"188","name":"Sector-85 Gurgaon"},{"loc_id":"189","name":"Sushant Lok Phase - 2"},{"loc_id":"190","name":"Shalimar Garden Extension II"},{"loc_id":"191","name":"Suraj Kund"},{"loc_id":"192","name":"Green Park"},{"loc_id":"193","name":"Sector Omicron I Greater Noida"},{"loc_id":"194","name":"Mehrauli"},{"loc_id":"195","name":"Laxmi Nagar"}]
         * message : Locality list successfully got.
         * resultCode : 1
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("locality_list")
        private List<LocalityListData> localityList;

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

        public List<LocalityListData> getLocalityList() {
            return localityList;
        }

        public void setLocalityList(List<LocalityListData> localityList) {
            this.localityList = localityList;
        }

        public static class LocalityListData {
            /**
             * loc_id : 1
             * name : Uttam Nagar
             */

            @SerializedName("loc_id")
            private String locId;
            @SerializedName("name")
            private String name;

            public String getLocId() {
                return locId;
            }

            public void setLocId(String locId) {
                this.locId = locId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
