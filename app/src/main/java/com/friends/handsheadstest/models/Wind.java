
package com.friends.handsheadstest.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Wind {

    @SerializedName("deg")
    private Long mDeg;
    @SerializedName("speed")
    private Double mSpeed;

    public Long getDeg() {
        return mDeg;
    }

    public void setDeg(Long deg) {
        mDeg = deg;
    }

    public Double getSpeed() {
        return mSpeed;
    }

    public void setSpeed(Double speed) {
        mSpeed = speed;
    }

}
