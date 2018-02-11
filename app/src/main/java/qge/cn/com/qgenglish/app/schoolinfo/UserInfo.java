package qge.cn.com.qgenglish.app.schoolinfo;

import java.io.Serializable;

/**
 * Created by fony on 2018/2/6.
 */

public class UserInfo implements Serializable {


    /**
     * token : 3UqUZk%2B6YJnXb0Hr2fB%2FMoeJv3mIMQVhBDFiHditOullM%2B5UkjNl3UPOIJ12GybzfDTk937vXOc5mkUHOfUp36b00eT%2F2%2FUxZDrMhfkNWOY%2Fg%2FUVx%2B8gRDbNANG0ZSQyOCKT%2FxOZVpDLwMNlbNuKRJYoR7ObGbD2IbLchG%2BIXrZZz6GaFKWawgGAtr1QI%2FwZ8UTgri5J6OjfcBfc4iA7eTdd25URkyxW4UBswidGbzTPZDRZ%2BtuNQ99vF1KRblu1hK0JrOdgWmJn%2BjGNmjx3NHQOy84kCs94W826au4GvJqSXLVZ03P%2BmJ0kRsoErxC4F4DKaRdQ58hvn0w%2BemoQnAt9IJ%2B3pXyNfdxM%2BEqZ4KzLot%2FONFxPgmbvYBL2Wbpq%2BYYQq3tEJl%2BweBiJho%2BfS82I2JHSkfg0vnliLSGyfn8nzu2C9nrSiRuCbU2HSbxBCoyPJTMxwm%2BxuZQAF0TPJC6VdWiVhjDsTt%2Bp%2FE7%2BxZyvYMwCA7gA5Lg62czVLyMpRThQxSEdksWRMNvFkOiQixpxKpTilBy5lGhaZfFMjYW%2Bl4ZwB8RPUE0pGYzjppXtr8v8e2tOwtXbeslOxP%2Br1g%3D%3D
     * userInfo : {"createDate":1517885616000,"enabled":"00","id":26,"lastLoginTime":1517885616000,"password":"123456","phone":"","type":"02","userName":"font"}
     */

    private String token;
    private UserInfoBean userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean implements Serializable {
        /**
         * createDate : 1517885616000
         * enabled : 00
         * id : 26
         * lastLoginTime : 1517885616000
         * password : 123456
         * phone :
         * type : 02
         * userName : font
         */

        private long createDate;
        private String enabled;
        private int id;
        private long lastLoginTime;
        private String password;
        private String phone;
        private String type;
        private String userName;
        private String area;
        private String city;
        private String grade;

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        private String schoolName;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
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

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
