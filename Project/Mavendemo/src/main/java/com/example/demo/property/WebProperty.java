package com.example.demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "webInfo")
public class WebProperty {

    public String webName;
    
    public String structureWay;

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getStructureWay() {
        return structureWay;
    }

    public void setStructureWay(String structureWay) {
        this.structureWay = structureWay;
    }
    
    
    
}
