package com.gym.gym.storages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public class DataStorageManager {

    Logger logger = LoggerFactory.getLogger(DataStorageManager.class);

    private final Map<String, DataStorage<?>> mainDataStorage;

    public DataStorageManager(){
        this.mainDataStorage = new HashMap<>();
    }

    /* Autowired annotation gets all beans of type DataStorage and injects them
     * into a list that is further used to create a map of datastorages. */
    @Autowired
    public void setMainDataStorage(List<DataStorage<?>> dataStorageList){
        dataStorageList.forEach(ds -> mainDataStorage.put(ds.getStorageType(), ds));
    }

    public <T> void write(String storageType, Map<Long, T> data){
        DataStorage<T> dataStorage = (DataStorage<T>) mainDataStorage.get(storageType);
        if(dataStorage == null){
            logger.error("Data storage of type " + storageType + " doesn't exist");
            return;
        }
        dataStorage.writeToFile(data);
    }

    public <T> Map<Long, T> read(String storageType){
        Map<Long, T> data = new HashMap<>();
        DataStorage<T> dataStorage = (DataStorage<T>) mainDataStorage.get(storageType);

        if(dataStorage == null){
            logger.error("Data storage of type " + storageType + " doesn't exist");
            return data;
        }

        data = dataStorage.readFromFile();
        return data;
    }
}
