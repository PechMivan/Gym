package com.gym.gym.storages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public class DataStorageManager {

    private final Map<String, DataStorage<?>> mainDataStorage;

    public DataStorageManager(){
        this.mainDataStorage = new HashMap<>();
    }

    @Autowired
    public void setMainDataStorage(List<DataStorage<?>> dataStorageList){
        dataStorageList.forEach(ds -> mainDataStorage.put(ds.getSTORAGE_TYPE(), ds));
    }

    public <T> void write(String storageType, Map<Long, T> data){
        DataStorage<T> dataStorage = (DataStorage<T>) mainDataStorage.get(storageType);
        if(dataStorage == null){
            // Log the error
            return;
        }
        dataStorage.writeToFile(data);
    }

    public <T> Map<Long, T> read(String storageType){
        Map<Long, T> data = new HashMap<>();
        DataStorage<T> dataStorage = (DataStorage<T>) mainDataStorage.get(storageType);

        if(dataStorage == null){
            // Log the error
            return data;
        }

        data = dataStorage.readFromFile();
        return data;
    }
}
