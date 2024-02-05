package com.gym.gym.storages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public class DataStorageManager {

    private final Map<String, DataStorage> mainDataStorage;

    public DataStorageManager(){
        this.mainDataStorage = new HashMap<>();
    }

    @Autowired
    public void setMainDataStorage(List<DataStorage> dataStorageList){
        dataStorageList.forEach(ds -> mainDataStorage.put(ds.getSTORAGE_TYPE(), ds));
    }

    public void write(String storageType, Map<Long, Object> data){
        DataStorage dataStorage = mainDataStorage.get(storageType);
        if(dataStorage == null){
            //Aqui va un log
            return;
        }
        dataStorage.writeToFile(data);
    }

    public Map<Long, Object> read(String storageType){
        Map<Long, Object> data = new HashMap<>();
        DataStorage dataStorage = mainDataStorage.get(storageType);

        if(dataStorage == null){
            //Aqui va un log
            return data;
        }

        data = dataStorage.readFromFile();
        return data;
    }

}
