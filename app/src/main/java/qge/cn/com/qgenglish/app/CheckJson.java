package qge.cn.com.qgenglish.app;

import java.util.List;

/**
 * Created by fony on 2018/1/28.
 */

public class CheckJson {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ProvinceName : 北京
         * ID : 1
         * city : [{"ID":1,"CityName":"东城区"},{"ID":353,"CityName":"西城区"},{"ID":356,"CityName":"朝阳区"},{"ID":357,"CityName":"丰台区"},{"ID":358,"CityName":"石景山区"},{"ID":359,"CityName":"海淀区"},{"ID":360,"CityName":"门头沟区"},{"ID":361,"CityName":"房山区"},{"ID":362,"CityName":"通州区"},{"ID":363,"CityName":"顺义区"},{"ID":364,"CityName":"昌平区"},{"ID":365,"CityName":"大兴区"},{"ID":366,"CityName":"怀柔区"},{"ID":367,"CityName":"平谷区"},{"ID":368,"CityName":"密云县"},{"ID":369,"CityName":"延庆县"},{"ID":370,"CityName":"其它区"}]
         */

        private String ProvinceName;
        private int ID;
        private List<CityBean> city;

        public String getProvinceName() {
            return ProvinceName;
        }

        public void setProvinceName(String ProvinceName) {
            this.ProvinceName = ProvinceName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * ID : 1
             * CityName : 东城区
             */

            private int ID;
            private String CityName;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String CityName) {
                this.CityName = CityName;
            }
        }
    }
}
