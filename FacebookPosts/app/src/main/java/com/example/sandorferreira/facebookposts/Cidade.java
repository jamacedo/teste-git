package com.example.sandorferreira.facebookposts;

/**
 * Created by sandorferreira on 23/09/15.
 */
import java.util.Properties;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Cidade {

    public String info;
    public Properties properties;

    public void setInfo(String text){
        info = text;
    }
    public String getInfo(){
        return info;
    }

    public Properties getProperties(){
        return properties;
    }

}
