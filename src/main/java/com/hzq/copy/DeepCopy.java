package com.hzq.copy;

import java.util.ArrayList;

/**
 * description
 *
 * @author hzq
 * @date 2022/4/25 11:07
 */
public class DeepCopy implements Cloneable {
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    public DeepCopy clone() {
        DeepCopy dCopy = null;

        try {
            dCopy = (DeepCopy) super.clone();
            //增加这行
            dCopy.arrayList = (ArrayList<String>) this.arrayList.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dCopy;
    }

    //set
    public void setValue(String value) {
        this.arrayList.add(value);
    }

    //get
    public ArrayList<String> getvalue() {
        return this.arrayList;
    }
}
