package model;

import register.MapObjRegister;

import java.util.HashMap;

/**
 * Simulation Model is the main model of the simulation
 * @author Zhanghaoji
 * @date 2021.06.2021/6/23 11:59
 */
public class SimulationModel {

    private HashMap<String, Place> placeMap = new HashMap<>(); // all places

    private HashMap<String, Car> carMap = new HashMap<>(); // all cars

    private HashMap<String, Station> stationMap = new HashMap<>(); // all stations

    private String stationText = ""; // station text to be displayed

    private String carText = ""; // car text to be displayed

    private String placeText = ""; // place text to be displayed

    private void updateStationText() {
        String str = "充电站信息\n";
        str += "编号\t充电桩数量\t充电速度\t所在位置\n";
        for(int i = 1; stationMap.containsKey(String.valueOf(i)); ++i) {
            Station sta = stationMap.get(String.valueOf(i));
            Place p = sta.getPlace();
            str += String.format("%s\t%d/%d\t%.2f\t(%.0f,%.0f)\n", sta.getId(), sta.getLineSize(), sta.getNumber(), sta.getChargingSpeed(), p.getX(), p.getY());
        }
        stationText = str;
    }

    public void updateCarText() {
        String str = "电动汽车信息\n";
        str += "编号\t电量\t速度\t当前位置\t居住地\t工作地\n";
        for(int i = 1; carMap.containsKey(String.valueOf(i)); ++i) {
            Car car = carMap.get(String.valueOf(i));
            str += String.format("%s\t%.0f/%.0f\t%.0f\t(%.0f,%.0f)\t%s\t%s\n", car.getId(), car.getPower(), car.getCapacity(),
                    car.getSpeed(), car.getCurPlace().getX(), car.getCurPlace().getY(), car.getHomePlaceID(), car.getWorkPlaceID());
        }
        carText = str;
    }

    public void updatePlaceText() {
        String str = "区域信息\n";
        str += "编号\t区域类型\t区域位置\n";
        for(int i = 1; placeMap.containsKey(String.valueOf(i)); ++i) {
            Place p = placeMap.get(String.valueOf(i));
            String typ = "";
            switch (p.getType()) {
                case "living": typ = "生活区"; break;
                case "industrial": typ = "工业区"; break;
                case "business": typ = "商业区"; break;
            }
            str += String.format("%s\t%s\t(%.0f,%.0f)\n", p.getId(), typ, p.getX(), p.getY());
        }
        placeText = str;
    }

    public SimulationModel(MapObjRegister register) {
        /**
         * place data
         */
        HashMap<String, Object> hMap = register.getRegisteredDataObjs("model.Place");
        for(Object d: hMap.values()) {
            if(d == null)
                continue;
            HashMap<String, Object> dMap = (HashMap<String, Object>)d;
            Place place = new Place((String)dMap.get("id"), (String)dMap.get("type"), Integer.parseInt((String)dMap.get("X-coordinate")), Integer.parseInt((String)dMap.get("Y-coordinate")));
            placeMap.put(place.getId(), place);
        }
        /**
         * car data
         */
        hMap = register.getRegisteredDataObjs("model.Car");
        for(Object d: hMap.values()) {
            if(d == null)
                continue;
            HashMap<String, Object> dMap = (HashMap<String, Object>)d;
            String hID = (String)dMap.get("homePlaceID");
            Car car = new Car((String)dMap.get("id"), Double.parseDouble((String)dMap.get("capacity")), Double.parseDouble((String)dMap.get("speed")),
                    hID, (String)dMap.get("workPlaceID"), placeMap.get(hID));
            carMap.put(car.getId(), car);
        }
        /**
         * station data
         */
        hMap = register.getRegisteredDataObjs("model.Station");
        for(Object d: hMap.values()) {
            if(d == null)
                continue;
            HashMap<String, Object> dMap = (HashMap<String, Object>)d;
            Station sta = new Station((String)dMap.get("id"), Integer.parseInt((String)dMap.get("number")), Double.parseDouble((String)dMap.get("chargingSpeed")),
                    placeMap.get((String)dMap.get("placeID")));
            stationMap.put(sta.getId(), sta);
        }

        updatePlaceText();
        updateStationText();
        updateCarText();
    }

    public HashMap<String, Place> getPlaceMap() {
        return placeMap;
    }

    public String getStationText() {
        return stationText;
    }

    public String getCarText() {
        return carText;
    }

    public String getPlaceText() {
        return placeText;
    }
}
