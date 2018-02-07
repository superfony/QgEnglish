package qge.cn.com.qgenglish.app.schoolinfo;

import java.io.Serializable;

/**
 * Created by fony on 2018/2/5.
 */

public class SchoolInfo implements Serializable {


    /**
     * schoolInfo : {"address":"","createTime":1513662487000,"enabled":"00","id":1,"imgSrc":"","managerName":"张三","managerPhone":"18678881728","padId":"12345","password":"","rebackRate":null,"referrer":"","sname":"测试学校","timeSurplus":999,"timeTotal":1000,"userId":1,"userName":""}
     * token : 3UqUZk%2B6YJnXb0Hr2fB%2FMn2MS%2BbFQXCg0F5DHtPhjKNlM%2B5UkjNl3UPOIJ12GybzfDTk937vXOc5mkUHOfUp36b00eT%2F2%2FUxZDrMhfkNWOY%2Fg%2FUVx%2B8gRDbNANG0ZSQyOCKT%2FxOZVpDLwMNlbNuKRJYoR7ObGbD2IbLchG%2BIXrZZz6GaFKWawgGAtr1QI%2FwZ8mv%2B09KtMUSVOABbpHxwpC%2BNj0iKjkf6o3lXeBmSFwmD%2B44BCuAmvr%2FfUPAwj9IcVyW%2BwQh%2FQHnmq77%2F0%2F2hJBVCNCsgM9nxI90SE7BHyXxp5pbczS%2Bj1THd9Pdz7XvM
     */

    private SchoolInfoBean schoolInfo;
    private String token;

    public SchoolInfoBean getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(SchoolInfoBean schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class SchoolInfoBean implements Serializable {
        /**
         * address :
         * createTime : 1513662487000
         * enabled : 00
         * id : 1
         * imgSrc :
         * managerName : 张三
         * managerPhone : 18678881728
         * padId : 12345
         * password :
         * rebackRate : null
         * referrer :
         * sname : 测试学校
         * timeSurplus : 999
         * timeTotal : 1000
         * userId : 1
         * userName :
         */

        private String address;
        private long createTime;
        private String enabled;
        private int id;
        private String imgSrc;
        private String managerName;
        private String managerPhone;
        private String padId;
        private String password;
        private Object rebackRate;
        private String referrer;
        private String sname;
        private int timeSurplus;
        private int timeTotal;
        private int userId;
        private String userName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getManagerPhone() {
            return managerPhone;
        }

        public void setManagerPhone(String managerPhone) {
            this.managerPhone = managerPhone;
        }

        public String getPadId() {
            return padId;
        }

        public void setPadId(String padId) {
            this.padId = padId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getRebackRate() {
            return rebackRate;
        }

        public void setRebackRate(Object rebackRate) {
            this.rebackRate = rebackRate;
        }

        public String getReferrer() {
            return referrer;
        }

        public void setReferrer(String referrer) {
            this.referrer = referrer;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public int getTimeSurplus() {
            return timeSurplus;
        }

        public void setTimeSurplus(int timeSurplus) {
            this.timeSurplus = timeSurplus;
        }

        public int getTimeTotal() {
            return timeTotal;
        }

        public void setTimeTotal(int timeTotal) {
            this.timeTotal = timeTotal;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
