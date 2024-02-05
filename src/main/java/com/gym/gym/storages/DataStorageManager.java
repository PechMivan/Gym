package com.gym.gym.storages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataStorageManager {

    List<DataStorage> dataStorageList;
    private Map<String, DataStorage> mainDataStorage = new HashMap<>();

    @Autowired
    public void setMainDataStorage(List<DataStorage> dataStorageList){
        this.dataStorageList = dataStorageList;
        dataStorageList.forEach(ds -> {
            mainDataStorage.put(ds.getStorageType(), ds);
        });
    }

}
