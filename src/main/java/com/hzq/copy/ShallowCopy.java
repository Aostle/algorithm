package com.hzq.copy;

import java.util.ArrayList;

/**
 * description
 *
 * @author hzq
 * @date 2022/4/25 11:07
 */
public class ShallowCopy implements Cloneable{
    private final ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    public ShallowCopy clone() {
        ShallowCopy sCopy = null;

        try {
            sCopy = (ShallowCopy) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sCopy;
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
